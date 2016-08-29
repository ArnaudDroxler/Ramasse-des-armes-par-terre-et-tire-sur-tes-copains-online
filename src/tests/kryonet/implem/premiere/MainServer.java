package tests.kryonet.implem.premiere;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import tests.kryonet.implem.premiere.messages.AcceptClientMessage;
import tests.kryonet.implem.premiere.messages.ClientConnexionMessage;
import tests.kryonet.implem.premiere.messages.ClientUpdateMessage;
import tests.kryonet.implem.premiere.server.JFramePartie;
import tests.kryonet.implem.premiere.server.JoueurOnline;
import tests.kryonet.implem.premiere.server.Partie;
import tests.kryonet.implem.premiere.tools.Registerer;

public class MainServer {

	public static void main(String[] args) {
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
		JFramePartie jfp = new JFramePartie(partie);

		server.addListener(new Listener() {
			public void connected(Connection connection){
				System.out.println(connection + " connected");
				connection.sendTCP("vous êtes connectés au serveur avec l'id " + connection.getID() +", adresse UDP: " + connection.getRemoteAddressUDP());
			}
			public void received(Connection connection, Object object) {
				if (object instanceof ClientConnexionMessage) {
					ClientConnexionMessage ccm = (ClientConnexionMessage) object;
					connection.setName(ccm.getPseudo()+":"+connection.getID());
					System.out.println("nouveau joueur : " + ccm.getPseudo());
					JoueurOnline nouveaujoueur = new JoueurOnline(ccm.getPseudo(), connection.getID());
					partie.addJoueur(connection.getID(), nouveaujoueur);
					AcceptClientMessage acm = new AcceptClientMessage(ccm.getPseudo(), partie, connection.getID());
					connection.sendTCP(acm);
				} else if (object instanceof ClientUpdateMessage) {
					
					ClientUpdateMessage cum = (ClientUpdateMessage) object;
					partie.updateJoueur(connection.getID(), cum);
					//System.out.println(cum.getPos());
					connection.sendUDP(partie);
					
				}
			}
			public void disconnected(Connection connection){
				System.out.println(connection + " disconnected");
				partie.removeJoueur(connection.getID());
			}
		});

		try {
			String str = "le serveur est ouvert à l'adresse " + 
					Inet4Address.getLocalHost().getHostAddress() + "\nPorts : TCP 54555, UDP 54777";
			System.out.println(str);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
