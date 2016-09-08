package rattco.messages;

import rattco.server.Partie;

public class AcceptClientMessage {

	private String msg;
	private Partie partie;
	private int clientId;
	private String mapPath;

	public AcceptClientMessage(String pseudo, Partie partie, int clientId, String mapPath) {
		this.msg = "Bienvenue " + pseudo + ", vous avez l'ID "+clientId+"\nInfos partie : " + partie.toString();
		this.partie = partie;
		this.clientId=clientId;
		this.mapPath=mapPath;
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
		return clientId;
	}

	public String getMapPath() {
		return mapPath;
	}

}
