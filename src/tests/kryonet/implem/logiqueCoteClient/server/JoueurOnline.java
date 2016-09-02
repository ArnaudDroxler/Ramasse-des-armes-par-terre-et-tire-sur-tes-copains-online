package tests.kryonet.implem.logiqueCoteClient.server;

import java.awt.image.BufferedImage;

import multi.thing.personnage.Joueur;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class JoueurOnline extends Joueur{

	public String pseudo;
	public int id;
	
	public JoueurOnline() {
	}

	public JoueurOnline(String pseudo, int id) {
		super(new Vector2D(),new Vector2D(1,0));
		this.pseudo=pseudo;
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	@Override
	public String toString() {
		return pseudo + ", vie=" + vie + ", pos=" + getPosition();
	}
	
	@Override
	public BufferedImage getSprite(int dir) {
		return MagasinImage.buffPika[dir];
	}
	
	public int getNbSecteurs(){
		return MagasinImage.buffPika.length;
	}
	
}
