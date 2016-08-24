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
				connection.sendTCP("vous êtes connectés au serveur");
			}
			public void received(Connection connection, Object object) {
				if (object instanceof ClientConnexionMessage) {
					ClientConnexionMessage ccm = (ClientConnexionMessage) object;
					System.out.println("nouveau joueur : " + ccm.getPseudo());
					partie.ajouterJoueur(ccm.getPseudo());
					AcceptClientMessage acm = new AcceptClientMessage(ccm.getPseudo());
					connection.sendTCP("bienvenue sur le serveur " + ccm.getPseudo());
				} else if (object instanceof Joueur) {
					Joueur j = (Joueur) object;
					partie.updateJoueur(j);
					connection.sendUDP(partie);
				} else if (object instanceof String) {
					String s = (String) object;
					System.out.println(s);
					connection.sendUDP(s);
				}

			}
		});

		System.out.println("le serveur est ouvert");
	}

}
