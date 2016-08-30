package tests.kryonet.implem.logiqueCoteClient.server;

import multi.thing.personnage.Joueur;
import multi.tools.raycasting.Vector2D;

public class JoueurOnline extends Joueur{

	public String pseudo;
	
	public JoueurOnline() {
		pseudo="";
		v=0.1;
	}

	public JoueurOnline(String pseudo) {
		super(new Vector2D(),new Vector2D());
		this.pseudo=pseudo;
	}

	public String getPseudo() {
		return pseudo;
	}

	@Override
	public String toString() {
		return pseudo + ", vie=" + vie;
	}
	
}
