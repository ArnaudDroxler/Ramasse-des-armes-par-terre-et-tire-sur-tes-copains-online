package tests.kryonet.implem.logiqueCoteClient.messages;

import tests.kryonet.implem.logiqueCoteClient.server.Partie;

public class AcceptClientMessage {

	private String msg;
	private Partie partie;
	private int id;

	public AcceptClientMessage(String pseudo, Partie partie, int id) {
		msg = "Bienvenue " + pseudo + ", vous avez l'ID "+id+"\nInfos partie : " + partie.toString();
		this.partie = partie;
		this.id=id;
	}

	public AcceptClientMessage() {
	}

	public String getMsg() {
		return msg;
	}
	
	public Partie getPartie(){
		return partie;
	}

	public int getId() {
		return id;
	}

}
