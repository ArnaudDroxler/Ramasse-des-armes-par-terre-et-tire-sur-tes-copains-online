package tests.kryonet;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

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
	}

}
