package tests.kryonet;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerSide {
	
	public static void main(String[] args) {
		Server server = new Server();
	    server.start();
	    try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof String) {
	        	  String  request = (String)object;
	              System.out.println(request);

	              String response = new String();
	              response = "Thanks";
	              connection.sendTCP(response);
	           }
	        }
	     });
	    
	}
	
}
