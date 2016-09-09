package rattco.client;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import rattco.messages.AcceptClientMessage;
import rattco.messages.ClientConnexionMessage;
import rattco.messages.DamageMessage;
import rattco.messages.FinPartieMessage;
import rattco.messages.FireMessage;
import rattco.messages.KillMessage;
import rattco.messages.PickUpMessage;
import rattco.messages.PlayerUpdateMessage;
import rattco.server.Partie;
import rattco.tools.Registerer;

/**
 * Cette classe s'occupe de la communication avec le serveur.
 * Une fois la connexion effectu�e, elle envoie toutes les networkDelay ms
 * la position du client au serveur.
 * Elle cr�e un objet LogiqueClient qui s'occupe de d�placer le joueur
 * du client et g�rer les collisisons avec les murs et les objets 
 * et un objet VueCamera qui permet le rendu du jeu en ray-casting
 * Les �v�nements d'entr�es souris/clavier sont g�r�es par la JFrameClient
 * jfcCamera.
 */
public class PcClient {

	private LogiqueClient lc;
	private final int networkDelay = 20;
	private Client client;
	private JFrameClient jfcCamera;
	private VueCamera vueCamera;
	private boolean jfcExists;
	private Dimension dim;
	private Point pt;

	public PcClient(String ip, String pseudo, int customH) throws IOException {
		// ceci est utile pour la recr�ation d'une jframe une fois la partie termin�e
		jfcExists = false;
		dim = new Dimension();
		pt = new Point();

		// nouvel objet Client de KryoNet qui nous permet la communication avec le serveur
		client = new Client();
		// il faut, du c�t� client comme du c�t� serveur, enregistrer les classes qui vont transiter entre les 2
		Registerer.registerFor(client);

		client.start();

		/**
		 * �a c'est un peu particulier mais pas trouv� de meilleure solution pour fournir
		 * � la logique client la r�f�rence du PcClient (car la logique client est instanci�e
		 * dans une classe abstraite anonyme, donc this n'est pas le PcClient
		 */
		PcClient moiMeme = this;

		// tente une connexion pendant 5 secondes,
		// lance une IOException si �a se passe mal
		client.connect(5000, ip, 54555, 54777);

		/**
		 * ClientConnexionMessage, ce message sert � �tablir une connexion avec le serveur.
		 * On l'envoie en TCP pour �tre s�r qu'il arrive � bon port
		 */
		ClientConnexionMessage ccm = new ClientConnexionMessage(pseudo);
		client.sendTCP(ccm);

		/**
		 * Le gestionnaire d'�v�nements 
		 */
		client.addListener(new Listener() {

			public void received(Connection connection, Object object) {
				/**
				 * AcceptClientMessage, ce message est un "Acknowledge" du serveur
				 * Il contient l'objet partie cr�� sur le serveur qui contient la
				 * position de tous les joueurs, le nom de la map � charger et un
				 * id pour que le client sache quel joueur il est
				 */
				if (object instanceof AcceptClientMessage) {
					AcceptClientMessage acm = (AcceptClientMessage) object;
					System.out.println(acm.getMsg());

					lc = new LogiqueClient(acm.getMapPath(), acm.getPartie(), acm.getId(), moiMeme);

					vueCamera = new VueCamera(lc, customH);
					
					// si une jfc existait d�j�, on la replace � la m�me position que celle d'avant
					if (jfcExists) {
						jfcCamera = new JFrameClient(vueCamera);
						jfcCamera.setLocation(pt);
						jfcCamera.setSize(dim);
					} else {
						jfcCamera = new JFrameClient(vueCamera);
						jfcExists = true;
					}

					/**
					 * Tous les networkDelay ms, on envoie notre objet JoueurOnline au serveur
					 * Le serveur va alors mettre � jour l'objet Partie et nous le renvoyer.
					 * C'est pas top niveau charge du serveur car certains attributs du joueur
					 * n'ont pas � �tre transmis (pseudo, vie, arme...).
					 * C'est un choix de facilit�e qu'il faudrait changer si on avait plus de temps
					 */
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							PlayerUpdateMessage pum = new PlayerUpdateMessage();
							while (true) {
								pum.setJoueur(lc.joueur);
								// comme on envoie le message tr�s souvent, on utilise UDP
								client.sendUDP(pum);
								try {
									Thread.sleep(networkDelay);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					});
					t.start();
				/**
				 * Le serveur nous � envoy� un objet Partie, on met simplement � jour
				 * la partie locale
				 */
				}else if (object instanceof Partie) {
					lc.updatePartie((Partie) object);
				// PickUpMessage: qqn a ramass� un objet, il faut le cacher
				} else if (object instanceof PickUpMessage) {
					lc.hideThing(((PickUpMessage) object).getIndexOfThing());
				// KillMessage: qqn est mort
				} else if (object instanceof KillMessage) {
					lc.murderHappened((KillMessage) object);
				// DamageMessage: le client s'est fait tirer dessus mais il n'est pas mort
				} else if (object instanceof DamageMessage) {
					lc.sufferDamages((DamageMessage) object);
				// Si on re�oit une String, on l'affiche simplement dans la console
				} else if (object instanceof String) {
					System.out.println((String) object);
				/**
				 * FinPartieMessage: le client doit recr�er une JFrameClient et se reconnecter
				 * au serveur
				 */
				} else if (object instanceof FinPartieMessage) {
					dim = jfcCamera.getSize();
					pt = jfcCamera.getLocation();
					jfcCamera.dispose();
					ClientConnexionMessage ccm = new ClientConnexionMessage(pseudo);
					client.sendTCP(ccm);
				}
			}
		});

	}

	/**
	 * Ces deux m�thodes sont appell�es par la logiqueClient lorsqu'un objet est
	 * ramass� et lorsque le client fait feu. Elles envoie le message appropri�
	 * au serveur, on utilise UDP car plus performant mais on pourrait utiliser
	 * TCP pour s'assurer que ces messages arrivent
	 */
	public void sendPickUpMessage(int indexOfThing) {
		PickUpMessage pum = new PickUpMessage(indexOfThing);
		client.sendUDP(pum);
	}

	public void sendFireMessage(int idOfPlayer) {
		FireMessage fm = new FireMessage(idOfPlayer);
		client.sendUDP(fm);
	}

}
