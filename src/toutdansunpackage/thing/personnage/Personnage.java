package toutdansunpackage.thing.personnage;

import toutdansunpackage.thing.Thing;
import toutdansunpackage.thing.weapon.Weapon;
import toutdansunpackage.tools.raycasting.Vector2D;

public abstract class Personnage extends Thing {

	protected int vie;
	protected int armure;
	protected Weapon arme;
	protected boolean estMort;
	protected boolean prendDegats;
	protected int nbKill;
	protected int nbDeath;

	public Personnage(Vector2D pos, Vector2D dir) {
		super(pos, dir);
		estMort = false;
		prendDegats = false;
		nbKill = 0;
		nbDeath = 0;
	}

	public Personnage() {
	}

	public void perdVie(int pv) {
		prendDegats = true;
		// Si on a de l'armure, baisse d'abord l'armure
		if (armure > 0) {
			armure -= pv;
			if (armure < 0) {

				// Lorsqu'il n'y a plus d'armure, baisse la vie
				vie += armure;
				armure = 0;
			}
		} else {
			vie -= pv;
		}
		// Si la vie passe en dessous de 0, on meurt
		if (vie <= 0) {
			tuer();
		}
	}

	public void tuer() {
		estMort = true;
		setNbDeath();
	}

	public int getVie() {
		return vie;
	}

	public int getArmure() {
		return armure;
	}

	public boolean getMort() {
		return estMort;
	}

	public boolean prendDegats() {
		return prendDegats;
	}

	public void resetPrendDegats() {
		prendDegats = false;
	}

	public int getNbKill() {
		return nbKill;
	}

	public int getNbDeath() {
		return nbDeath;
	}

	public void setNbKill() {
		nbKill += 1;

	}

	public void setNbDeath() {
		nbDeath += 1;
	}

}
