package tests.kryonet.implem;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class MainServer {

	public static void main(String[] args) {
		Server server = new Server();
		Registerer.registerFor(server);

		server.start();

		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Partie partie = new Partie();

		server.addListener(new Listener() {
			public void connected(Connection connection){
				System.out.println(connection + " connected");
				connection.sendTCP("vous êtes connectés au serveur avec l'id " + connection.getID() +", addresse UDP: " + connection.getRemoteAddressUDP());
			}
			public void received(Connection connection, Object object) {
				if (object instanceof ClientConnexionMessage) {
					ClientConnexionMessage ccm = (ClientConnexionMessage) object;
					connection.setName(ccm.getPseudo()+":"+connection.getID());
					System.out.println("nouveau joueur : " + ccm.getPseudo());
					Joueur nouveaujoueur = new Joueur(ccm.getPseudo(), connection.getID());
					partie.addJoueur(connection.getID(), nouveaujoueur);
					AcceptClientMessage acm = new AcceptClientMessage(ccm.getPseudo(), partie);
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

		System.out.println("le serveur est ouvert");
	}

}
