package tests.kryonet.implem;

import com.esotericsoftware.kryonet.Connection;

public class Joueur {

	private String pseudo;
	private int vie;
	private int posx;
	private int posy;
	private int dirx;
	private int diry;
	private Connection connection;
	
	public Joueur() {
		
	}

	public Joueur(String pseudo, Connection connection) {
		this.pseudo=pseudo;
		this.connection=connection;
	}

	public String getPseudo() {
		return pseudo;
	}

	@Override
	public String toString() {
		return pseudo + ", vie=" + vie + ", pos=(" + posx + "," + posy + "), dir=(" + dirx
				+ "," + diry + ")";
	}
	
}
