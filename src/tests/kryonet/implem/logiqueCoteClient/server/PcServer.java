package tests.kryonet.implem.logiqueCoteClient.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import tests.kryonet.implem.logiqueCoteClient.messages.AcceptClientMessage;
import tests.kryonet.implem.logiqueCoteClient.messages.ClientConnexionMessage;
import tests.kryonet.implem.logiqueCoteClient.messages.PlayerUpdateMessage;
import tests.kryonet.implem.logiqueCoteClient.tools.Registerer;

public class PcServer {

	public PcServer(boolean modeGraphique) {

		Server server = new Server();
		Registerer.registerFor(server);

		server.start();

		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			System.err.println("Les ports TCP 54555 et UDP 54777 ne sont pas accessibles");
			System.exit(-1);
			// e.printStackTrace();
		}

		Partie partie = new Partie();
		if(modeGraphique){
			new JFramePartie(partie);
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
					JoueurOnline nouveaujoueur = new JoueurOnline(ccm.getPseudo());
					partie.addJoueur(connection.getID(), nouveaujoueur);
					AcceptClientMessage acm = new AcceptClientMessage(ccm.getPseudo(), partie, connection.getID());
					connection.sendTCP(acm);
				} else if (object instanceof PlayerUpdateMessage) {
					PlayerUpdateMessage pum = (PlayerUpdateMessage) object;
					partie.updateJoueur(connection.getID(), pum);
					connection.sendUDP(partie);
				}
			}
			public void disconnected(Connection connection) {
				System.out.println(connection + " disconnected");
				partie.removeJoueur(connection.getID());
			}
		});

		
		String str = "le serveur est ouvert\nPorts : TCP 54555, UDP 54777";
		System.out.println(str);
	}
}