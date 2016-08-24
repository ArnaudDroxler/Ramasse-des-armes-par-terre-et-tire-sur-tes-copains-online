package tests.kryonet.implem;

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
