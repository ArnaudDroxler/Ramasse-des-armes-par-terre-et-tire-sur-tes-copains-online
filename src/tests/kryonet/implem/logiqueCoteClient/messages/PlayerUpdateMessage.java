package tests.kryonet.implem.logiqueCoteClient.messages;

import multi.tools.raycasting.Vector2D;

public class PlayerUpdateMessage{

	private double posx;
	private double posy;
	
	public void setPosition(double x, double y) {
		posx=x;
		posy=y;
	}
	
	public PlayerUpdateMessage(){}

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
