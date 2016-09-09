package rattco.server;

import java.io.IOException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import rattco.messages.AcceptClientMessage;
import rattco.messages.ClientConnexionMessage;
import rattco.messages.DamageMessage;
import rattco.messages.FinPartieMessage;
import rattco.messages.FireMessage;
import rattco.messages.KillMessage;
import rattco.messages.PickUpMessage;
import rattco.messages.PlayerUpdateMessage;
import rattco.thing.personnage.JoueurOnline;
import rattco.tools.Registerer;

/**
 * "Moteur" du serveur, il contient l'objet Server fournit par KryoNet et
 * le gestionnaire d'�v�nement pour dispacher les messages entre les clients
 *
 */
public class PcServer {

	private String mapName;
	private int tempsPartieSecondes;
	private Partie partie;
	private Server server;
	private LogiqueServer ls;

	private String[] tabMap;
	int cptMap;
	private int nbJoeursMax;
	private int nbJoueurs;

	public PcServer(String[] args) {
		server = new Server();
		
		/**
		 * Il est imp�ratif d'enregistrer toutes les classes qui transitent entre le client
		 * et le serveur
		 */
		Registerer.registerFor(server);

		mapName = args[0];
		nbJoeursMax = Integer.parseInt(args[1]);
		nbJoueurs = 0;

		if (args[2] == null) {
			// Si aucun temps entr�, temps de 5 min par d�faut
			tempsPartieSecondes = 5 * 60;
		} else {
			tempsPartieSecondes = Integer.parseInt(args[2]);
		}

		tabMap = JFrameConfiguration.loadFoldersFromFolder("maps");

		/**
		 * Ce compteur de map permet de charger une map l'une apr�s l'autre
		 * � la fin d'une partie, ici on r�cup�re l'index de la map donn�e
		 * en param�tre
		 */
		for (int i = 0; i < tabMap.length; i++) {
			String testMapChoisie = "maps/" + tabMap[i];
			if (testMapChoisie.equals(mapName)) {
				cptMap = i;
			}
		}

		partie = new Partie(tempsPartieSecondes);
		ls = new LogiqueServer(mapName, partie, this);
		
		server.start();

		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			System.err.println("Les ports TCP 54555 et UDP 54777 ne sont pas accessibles");
			System.exit(-1);
		}

		String str = "le serveur est ouvert\nPorts : TCP 54555, UDP 54777";
		System.out.println(str);

		/**
		 * Ce thread permet de red�marre une partie apr�s un certain temps
		 * (temps fourni en param�tre), � chaque seconde on v�rifie que le temps
		 * n'est pas �coul�e et on modifie le temps restant dans l'objet partie,
		 * afin que les clients connaissent le temps restant
		 */
		Thread tpartieEnCours = new Thread(new Runnable() {

			@Override
			public void run() {

				long t1 = System.currentTimeMillis(), t2;
				while (partie.tempsSecondes > 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					t2 = System.currentTimeMillis();
					partie.setTemps((int) (tempsPartieSecondes - (t2 - t1) / 1000));
				}
				creerNouvellePartie();
				sendFinPartie(mapName, partie);

				this.run();

			}
		});
		tpartieEnCours.start();

		/**
		 * Voil� le gestionnaire d'�v�nement
		 */
		server.addListener(new Listener() {

			public void connected(Connection connection) {
				System.out.println(connection + " connected");
			}

			/**
			 * Cette m�thode est appel�e � chaque fois que le serveur re�oit un paquet
			 * L'objet connexion identifie le client
			 * L'objet object est le message qui est re�u, il faut tester de quel type
			 * est cet objet pour effectuer la bonne op�ration en cons�quence
			 */
			public void received(Connection connection, Object object) {
				/**
				 * ClientConnexionMessage, ce message est envoy� par un client qui veut
				 * se connecter, il contient le pseudo du joueur
				 */
				if (object instanceof ClientConnexionMessage) {
					ClientConnexionMessage ccm = (ClientConnexionMessage) object;
					if (nbJoueurs < nbJoeursMax) {
						connection.setName(ccm.getPseudo() + ":" + connection.getID());
						System.out.println("nouveau joueur : " + ccm.getPseudo());

						JoueurOnline nouveaujoueur = new JoueurOnline(ccm.getPseudo(), connection.getID());
						nbJoueurs++;
						
						/**
						 * L'objet partie contient un HashMap avec pour cl� l'id du client et pour valeur 
						 * l'objet JoueurOnline correspondant � ce client
						 */
						partie.addJoueur(connection.getID(), nouveaujoueur);
						AcceptClientMessage acm = new AcceptClientMessage(ccm.getPseudo(), partie, connection.getID(),
								mapName);
						connection.sendTCP(acm);
					} else {
						connection.sendTCP("Serveur plein");
					}
				/**
				 * PlayerUpdateMessage, contient les informations du joueur
				 * Le serveur met � jour la partie et la renvoie au client
				 */
				} else if (object instanceof PlayerUpdateMessage) {
					PlayerUpdateMessage pum = (PlayerUpdateMessage) object;
					partie.updateJoueur(connection.getID(), pum);
					connection.sendUDP(partie);
				/**
				 * PickUpMessage, re�u lorsqu'un client ramasse un objet, le message
				 * contient simplement l'index de cet objet dans la liste d'objets,
				 * dans la logique client. Cette liste est r�cup�r�e depuis l'image de la map
				 * dans la classe ImageParser
				 */
				} else if (object instanceof PickUpMessage) {
					server.sendToAllExceptUDP(connection.getID(), (PickUpMessage) object);
				/**
				 * FireMessage, re�u � chaque fois q'un client fait feu.
				 * La logique serveur calcule les cons�quences du tire et appelle 
				 * sendKillMessage() ou sendDamageMessage()
				 */
				} else if (object instanceof FireMessage) {
					ls.fireFromPlayer(((FireMessage) object).getClientId());
				}
			}

			/**
			 * Lorsqu'un client se d�connecte, il est retir� de la partie
			 */
			public void disconnected(Connection connection) {
				System.out.println(connection + " disconnected");
				partie.removeJoueur(connection.getID());
				nbJoueurs--;
			}
		});

	}

	public void sendKillMessage(JoueurOnline shooter, JoueurOnline ennemiTouche) {
		KillMessage km = new KillMessage(shooter.id, ennemiTouche.id);
		server.sendToAllUDP(km);
	}

	public void sendDamageMessage(JoueurOnline ennemiTouche, int degats) {
		DamageMessage dm = new DamageMessage(degats);
		server.sendToUDP(ennemiTouche.id, dm);
	}

	public void sendFinPartie(String map, Partie part) {
		FinPartieMessage fpm = new FinPartieMessage(map, part);
		server.sendToAllTCP(fpm);
	}

	public void creerNouvellePartie() {
		cptMap++;
		if (cptMap >= tabMap.length) {
			cptMap = 0;
		}
		nbJoueurs = 0;
		mapName = "maps/" + tabMap[cptMap];
		partie = new Partie(tempsPartieSecondes);
		ls = new LogiqueServer(mapName, partie, this);
	}
}
