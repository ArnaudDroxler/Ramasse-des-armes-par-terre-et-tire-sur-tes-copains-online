package tests.kryonet.implem.premiere.server;

import multi.thing.personnage.Joueur;
import multi.tools.raycasting.Vector2D;

public class JoueurOnline extends Joueur{

	public String pseudo;
	private int connectionId;
	
	public JoueurOnline() {
		pseudo="";
	}

	public JoueurOnline(String pseudo, int connectionId) {
		super(new Vector2D(),new Vector2D());
		this.pseudo=pseudo;
		this.connectionId=connectionId;
	}

	public String getPseudo() {
		return pseudo;
	}

	@Override
	public String toString() {
		return pseudo + ", vie=" + vie;
	}
	
}
