package multi.thing.personnage;

import java.awt.image.BufferedImage;

import multi.thing.weapon.HandGun;
import multi.thing.weapon.Weapon;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Joueur extends Personnage {

	public Joueur(Vector2D startPosition, Vector2D startDirection) {
		super(startPosition, startDirection);
		vie = 100;
		armure = 50;
	}		
	
	public Joueur() {}	

	@Override
	public BufferedImage getSprite() {
		return null;
	}

	public void ajoutVie(int pv) {
		vie += pv;
		if (vie > 100)
			vie = 100;
	}

	public void ajoutArmure(int pa) {
		armure += pa;
		if (armure > 100)
			armure = 100;
	}

	public Weapon getArme() {
		return arme;
	}

	public void setArme(Weapon arme) {
		this.arme = arme;
	}

	public void respawn() {
		estMort = false;
		vie = 100;
		armure = 50;
	}

}
