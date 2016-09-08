package rattco.messages;

public class FireMessage {

	private int clientId;

	public FireMessage(){}
	
	public FireMessage(int clientId){
		this.clientId = clientId;
	}

	public int getClientId() {
		return clientId;
	}
	
}
