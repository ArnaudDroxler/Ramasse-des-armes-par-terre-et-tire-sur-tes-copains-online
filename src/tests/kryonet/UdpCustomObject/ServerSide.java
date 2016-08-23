package tests.kryonet.UdpCustomObject;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerSide {

	public static void main(String[] args) {
		Server server = new Server();
		
	    Kryo kryo = server.getKryo();
	    kryo.register(Joueur.class);
	    kryo.register(Infos.class);
	    kryo.register(ArrayList.class);
	    
		server.start();
		
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	        	System.out.println("message recu sur le serveur");
        		System.out.println("le client dit : " + object);
        		/*
	        	if (object instanceof Yolo) {
	        		Yolo infos = (Yolo)object;
	        		System.out.println("le client dit : " + infos);
	           }
	           */
	        }
	     });
	    
	    System.out.println("le serveur écoute");
	}

}
