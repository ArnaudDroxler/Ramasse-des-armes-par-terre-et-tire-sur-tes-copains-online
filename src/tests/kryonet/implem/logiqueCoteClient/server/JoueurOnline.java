package tests.kryonet.implem.logiqueCoteClient.server;

import multi.thing.personnage.Joueur;
import multi.tools.raycasting.Vector2D;

public class JoueurOnline extends Joueur{

	public String pseudo;
	
	public JoueurOnline() {
	}

	public JoueurOnline(String pseudo) {
		super(new Vector2D(),new Vector2D(1,0));
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
