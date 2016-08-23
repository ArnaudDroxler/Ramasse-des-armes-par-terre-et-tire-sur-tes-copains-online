package tests.kryonet;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientSide {
	
	public static void main(String[] args) {
	    Client client = new Client();
	    client.start();
	    try {
			client.connect(5000, "localhost", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

	    String request = "Here is the request";
	    client.sendTCP(request);
	    
	    client.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof String) {
	        	   String response = (String)object;
	        	   System.out.println(response);
	           }
	        }
	     });
	}

}
