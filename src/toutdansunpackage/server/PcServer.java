package toutdansunpackage.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import toutdansunpackage.messages.AcceptClientMessage;
import toutdansunpackage.messages.ClientConnexionMessage;
import toutdansunpackage.messages.DamageMessage;
import toutdansunpackage.messages.FireMessage;
import toutdansunpackage.messages.KillMessage;
import toutdansunpackage.messages.PickUpMessage;
import toutdansunpackage.messages.PlayerUpdateMessage;
import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.Registerer;

public class PcServer {

	private String mapPath;
	private String tempsPartie;
	private String nombreJoueursMax;
	private Partie partie;
	private Server server;
	private LogiqueServer ls;

	public PcServer(String[] args) {
		server = new Server();

		if (!(args.length > 0)) {
			new JFrameConfiguration();
		} else {
			// String mapPath = "sprite/map/maison";
			mapPath = args[0];
			nombreJoueursMax = args[1];
			tempsPartie = args[2];
			partie = new Partie(mapPath);

			Registerer.registerFor(server);

			ls = new LogiqueServer(mapPath, partie, this);

			server.start();

			try {
				server.bind(54555, 54777);

				String str = "le serveur est ouvert\nPorts : TCP 54555, UDP 54777";
				System.out.println(str);
				System.out.println("map : " + mapPath);
				System.out.println("nombre de joueurs: " + nombreJoueursMax);
				System.out.println("temps en millisecondes: " + tempsPartie);
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
					connection.setName(ccm.getPseudo() + ":" + connection.getID());
					System.out.println("nouveau joueur : " + ccm.getPseudo());

					JoueurOnline nouveaujoueur = new JoueurOnline(ccm.getPseudo(), connection.getID());
					partie.addJoueur(connection.getID(), nouveaujoueur);

					AcceptClientMessage acm = new AcceptClientMessage(ccm.getPseudo(), partie, connection.getID(),
							mapPath);
					connection.sendTCP(acm);
				} else if (object instanceof PlayerUpdateMessage) {
					PlayerUpdateMessage pum = (PlayerUpdateMessage) object;
					partie.updateJoueur(connection.getID(), pum);
					partie.nbBytesSent = connection.sendUDP(partie);
				} else if (object instanceof PickUpMessage) {
					server.sendToAllExceptUDP(connection.getID(), (PickUpMessage) object);
				} else if (object instanceof FireMessage) {
					ls.fireFromPlayer(((FireMessage)object).getClientId());
				}
			}

			public void disconnected(Connection connection) {
				System.out.println(connection + " disconnected");
				partie.removeJoueur(connection.getID());
			}
		});

	}

	public void sendKillMessage(JoueurOnline shooter, JoueurOnline ennemiTouche) {
		KillMessage km = new KillMessage(shooter.id,ennemiTouche.id);
		server.sendToAllUDP(km);
	}

	public void sendDamageMessage(JoueurOnline ennemiTouche, int degats) {
		DamageMessage dm = new DamageMessage(degats);
		server.sendToUDP(ennemiTouche.id, dm);
		
	}
}
