package tests.kryonet.implem.serveurChef.messages;

import multi.tools.raycasting.Vector2D;

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

	public Vector2D getPos() {
		return new Vector2D(posx,posy);
	}

}
