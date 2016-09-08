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

public class PcServer {

	private String mapName;
	private int tempsPartie;
	private Partie partie;
	private Server server;
	private LogiqueServer ls;

	private String[] tabMap;
	int cptMap;
	private int nbJoeursMax;

	public PcServer(String[] args) {
		server = new Server();

		if (args.length == 0) {
			new JFrameConfiguration();
		} else {
			// String mapPath = "sprite/map/maison";

			mapName = args[0];
			nbJoeursMax = Integer.parseInt(args[1]);

			if (args[2] == null) {
				// Si aucun temps entr�, temps de 5mn par d�faut
				tempsPartie = 300000;
			} else {
				tempsPartie = Integer.parseInt(args[2]) * 60000;
			}

			tabMap = JFrameConfiguration.loadFoldersFromFolder("maps");

			for (int i = 0; i < tabMap.length; i++) {
				String testMapChoisie = "maps/" + tabMap[i];
				if (testMapChoisie.equals(mapName)) {
					System.out.println("cptMap = " + i);
					cptMap = i;
				}

			}

			partie = new Partie();

			Registerer.registerFor(server);

			ls = new LogiqueServer(mapName, partie, this);

			server.start();

			try {
				server.bind(54555, 54777);

				String str = "le serveur est ouvert\nPorts : TCP 54555, UDP 54777";
				System.out.println(str);

				Thread tpartieEnCours = new Thread(new Runnable() {

					@Override
					public void run() {

						long t1 = System.currentTimeMillis(), t2;
						while ((tempsPartie / 1000) != partie.tempsSecondes) {
							t2 = System.currentTimeMillis();
							partie.setTempsSecondes((t2 - t1) / 1000);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						creerNouvellePartie();
						sendFinPartie(mapName, partie);

						this.run();

					}
				});
				tpartieEnCours.start();

			} catch (IOException e) {
				System.err.println("Les ports TCP 54555 et UDP 54777 ne sont pas accessibles");
				System.exit(-1);
				// e.printStackTrace();
			}
		}

		server.addListener(new Listener() {
			public void connected(Connection connection) {
				System.out.println(connection + " connected");
			}

			public void received(Connection connection, Object object) {
				if (object instanceof ClientConnexionMessage) {
					ClientConnexionMessage ccm = (ClientConnexionMessage) object;
					if (partie.getJoueurs().size() < nbJoeursMax) {
						connection.setName(ccm.getPseudo() + ":" + connection.getID());
						System.out.println("nouveau joueur : " + ccm.getPseudo());

						JoueurOnline nouveaujoueur = new JoueurOnline(ccm.getPseudo(), connection.getID());
						
						partie.addJoueur(connection.getID(), nouveaujoueur);
						AcceptClientMessage acm = new AcceptClientMessage(ccm.getPseudo(), partie, connection.getID(),
								mapName);
						connection.sendTCP(acm);
					}else{
						connection.sendTCP("Serveur plein");
					}
				} else if (object instanceof PlayerUpdateMessage) {
					PlayerUpdateMessage pum = (PlayerUpdateMessage) object;
					partie.updateJoueur(connection.getID(), pum);
					connection.sendUDP(partie);
				} else if (object instanceof PickUpMessage) {
					server.sendToAllExceptUDP(connection.getID(), (PickUpMessage) object);
				} else if (object instanceof FireMessage) {
					ls.fireFromPlayer(((FireMessage) object).getClientId());
				}
			}

			public void disconnected(Connection connection) {
				System.out.println(connection + " disconnected");
				partie.removeJoueur(connection.getID());
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
		mapName = "maps/" + tabMap[cptMap];
		partie = new Partie();
		ls = new LogiqueServer(mapName, partie, this);

	}
}
