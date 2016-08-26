package tests.kryonet.implem.premiere.messages;

public class ClientUpdateMessage{

	private double posx;
	private double posy;
	
	public void setPosition(double x, double y) {
		posx=x;
		posy=y;
	}
	
	public ClientUpdateMessage(){}

	public double getPosx() {
		return posx;
	}

	public double getPosy() {
		return posy;
	}

}
