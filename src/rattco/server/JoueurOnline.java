package rattco.server;

import java.awt.image.BufferedImage;

import rattco.thing.Thing;
import rattco.thing.personnage.Joueur;
import rattco.thing.weapon.Weapon;
import rattco.tools.MagasinImage;
import rattco.tools.raycasting.Vector2D;

public class JoueurOnline extends Joueur{

	public String pseudo;
	public int id;
	
	public JoueurOnline() {}


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

	public boolean weaponIs(Thing thing) {
		if(arme == null){
			return false;
		}else{
			boolean wesh = thing.getClass().getSimpleName().equals(arme.getClass().getSimpleName());
			return wesh;
		}
	}
	
}