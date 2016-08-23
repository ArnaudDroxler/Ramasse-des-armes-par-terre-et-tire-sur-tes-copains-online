package tests.kryonet.UdpCustomObject;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

public class ClientSide {
	
	public static void main(String[] args) {
	    Client client = new Client();
	    
	    Kryo kryo = client.getKryo();
	    kryo.register(Joueur.class);
	    kryo.register(Infos.class);
	    kryo.register(ArrayList.class);
	    
	    client.start();
	    try {
			client.connect(5000, "localhost", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

	    Infos infos = new Infos("coucou");

	    Joueur j= new Joueur("aicha", 12, 2);
	    infos.addJoueur(j);
	    infos.addJoueur(new Joueur("yoan", 4, 4));
	    
	    int nbBSent = client.sendUDP(infos);
	    System.out.println(nbBSent + " bytes envoyés");
	}

}
