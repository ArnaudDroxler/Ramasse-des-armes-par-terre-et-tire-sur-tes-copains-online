package tests.kryonet.implem.premiere.server;

import com.esotericsoftware.kryonet.Connection;

public class Joueur {

	private String pseudo;
	private int vie;
	private int posx;
	private int posy;
	private int dirx;
	private int diry;
	private int connectionId;
	
	public Joueur() {
		
	}

	public Joueur(String pseudo, int connectionId) {
		this.pseudo=pseudo;
		this.connectionId=connectionId;
	}

	public String getPseudo() {
		return pseudo;
	}

	@Override
	public String toString() {
		return pseudo + ", vie=" + vie + ", pos=(" + posx + "," + posy + "), dir=(" + dirx
				+ "," + diry + ")";
	}

	public void setPos(int pos) {
		posx=pos;		
	}
	
}
