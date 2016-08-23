package tests.kryonet.UdpCustomObject;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import javafx.geometry.Point2D;

public class ServerSide {

	public static void main(String[] args) {
		Server server = new Server();
		
	    Kryo kryo = server.getKryo();
	    kryo.register(Infos.class);
	    kryo.register(Joueur.class);
	    kryo.register(ArrayList.class);
	    kryo.register(javafx.geometry.Point2D.class);
	    
		server.start();
		
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof Infos) {
	        	  Infos infos = (Infos)object;
	              System.out.println("le client dit : " + infos);
	           }
	        }
	     });
	    
	    System.out.println("le serveur écoute");
	}

}
