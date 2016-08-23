package tests.kryonet.UdpCustomObject;

import javafx.geometry.Point2D;

public class Joueur {
	
	private Point2D position;
	private Point2D direction;
	private String pseudo;
	
	public Joueur(String pseudo, int x, int y, int a, int b){
		this.pseudo = pseudo;
		position= new Point2D(x, y);
		direction=new Point2D(a, b);
	}

	@Override
	public String toString() {
		return "\nJoueur [position=" + position + ", direction=" + direction + ", pseudo=" + pseudo + "]";
	}
	
}
