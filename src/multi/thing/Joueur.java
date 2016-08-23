package multi.thing;

import java.awt.image.BufferedImage;

import multi.tools.raycasting.Vector2D;

public class Joueur extends Thing {

	public Joueur(Vector2D startPosition, Vector2D startDirection) {
		super(startPosition, startDirection);
		vie = 100;
		armure = 50;
	}

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

	public void perdVie(int pv) {

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
		if (vie < 0) {
			// TODO Mort
			System.out.println("Mort!!!");
		}
	}

	public int getVie() {
		return vie;
	}

	public int getArmure() {
		return armure;
	}

	private int vie;
	private int armure;
	// private Arme arme;

}
