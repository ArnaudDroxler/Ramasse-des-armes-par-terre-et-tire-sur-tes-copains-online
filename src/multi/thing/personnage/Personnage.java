package multi.thing.personnage;

import multi.thing.Thing;
import multi.thing.weapon.Weapon;
import multi.tools.raycasting.Vector2D;

public abstract class Personnage extends Thing {

	protected int vie;
	protected int armure;
	protected Weapon arme;
	protected boolean estMort;
	protected boolean prendDegats;

	public Personnage(Vector2D pos, Vector2D dir) {
		super(pos, dir);
		estMort = false;
		prendDegats = false;
	}
	
	public Personnage(){}

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
			// TODO Mort
			estMort = true;
			System.out.println("Mort!!!");
		}
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
	
	public void resetPrendDegats(){
		prendDegats = false;
	}

}
