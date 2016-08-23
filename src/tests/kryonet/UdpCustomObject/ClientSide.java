package tests.kryonet.UdpCustomObject;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import javafx.geometry.Point2D;

public class ClientSide {
	
	public static void main(String[] args) {
	    Client client = new Client();
	    
	    Kryo kryo = client.getKryo();
	    kryo.register(Infos.class);
	    kryo.register(Joueur.class);
	    kryo.register(ArrayList.class);
	    kryo.register(javafx.geometry.Point2D.class);
	    
	    client.start();
	    try {
			client.connect(5000, "localhost", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

	    Infos infos = new Infos();
	    infos.setTexte("Lancement de la partie");
	    infos.addJoueurs(new Joueur("Aicha Rizzotti",112,12,0,1));
	    infos.addJoueurs(new Joueur("Yoan Blanc",32,54,1,0));
	    
	    int nbBSent = client.sendUDP(infos);
	    System.out.println(nbBSent + " bytes envoyés");
	}

}
