package tests.kryonet.implem.premiere.messages;

import com.esotericsoftware.kryonet.Connection;

import tests.kryonet.implem.premiere.server.Partie;

public class AcceptClientMessage {

	private String msg;
	

	public AcceptClientMessage(String pseudo, Partie partie) {
		msg = "Bienvenue " + pseudo + "\nInfos partie : " + partie.toString();
	}

	public AcceptClientMessage() {
	}

	public String getMsg() {
		return msg;
	}

}
