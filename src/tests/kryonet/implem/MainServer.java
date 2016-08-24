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

		// Kryo kryo = server.getKryo();
		// kryo.register(Joueur.class);
		// kryo.register(Infos.class);
		// kryo.register(ArrayList.class);

		server.start();

		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Partie partie = new Partie();

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof ClientConnexionMessage) {
					ClientConnexionMessage ccm = (ClientConnexionMessage) object;
					System.out.println("nouveau joueur : " + ccm.getPseudo());
					partie.ajouterJoueur(ccm.getPseudo());
					connection.sendTCP("bienvenue sur le serveur");
				} else if (object instanceof Joueur) {
					Joueur j = (Joueur) object;
					partie.updateJoueur(j);
					connection.sendUDP(partie);
				}

			}
		});

		System.out.println("le serveur est ouvert");
	}

}
