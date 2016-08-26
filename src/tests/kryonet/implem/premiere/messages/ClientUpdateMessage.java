package tests.kryonet.implem.premiere.messages;

public class ClientUpdateMessage{

	private int pos;
	
	public void setPosition(int p) {
		pos=p;
	}

	public int getPos() {
		return pos;
	}
	
	public ClientUpdateMessage(){}

}
