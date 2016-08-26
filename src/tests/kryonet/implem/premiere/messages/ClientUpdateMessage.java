package tests.kryonet.implem.premiere.messages;

public class ClientUpdateMessage{

	private double pos;
	
	public void setPosition(double d) {
		pos=d;
	}

	public double getPos() {
		return pos;
	}
	
	public ClientUpdateMessage(){}

}
