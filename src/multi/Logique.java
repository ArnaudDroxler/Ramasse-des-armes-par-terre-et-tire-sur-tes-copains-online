package multi;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import multi.thing.Armure;
import multi.thing.Key;
import multi.thing.Medipack;
import multi.thing.Monstre;
import multi.thing.Thing;
import multi.thing.personnage.Ennemie;
import multi.thing.personnage.Joueur;
import multi.thing.weapon.HandGun;
import multi.tools.GeometricTools;
import multi.tools.map.ImageParser;
import multi.tools.map.LvlMap;
import multi.tools.raycasting.Vector2D;
import multi.tools.raycasting.algoPiergiovanni;

public class Logique extends KeyAdapter {

	protected LvlMap map;
	protected Joueur heros;
	private long delay;
	private long tempsRepop;
	private HashSet<Integer> touchesEnfoncees;
	private Vector2D oldPosition;
	protected boolean fin;
	protected Line2D fireLine;
	public boolean isFiring;
	protected ArrayList<Thing> listeThings;

	// test vie et armure
	protected ArrayList<Ennemie> listEnnemie;

	public Logique(String nomMap) {
		delay = 10;
		tempsRepop = 15000;

		map = ImageParser.getMap(nomMap);

		oldPosition = map.getStartPosition();

		heros = new Joueur(oldPosition, new Vector2D(1, 0));
		heros.setVitesse(0.05);

		touchesEnfoncees = new HashSet<Integer>(6);

		fin = false;
		isFiring = false;

		// test vie et armure
		listeThings = map.getListThing();
		listEnnemie = map.getListEnnemie();

		animer();

	}

	private void animer() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (!fin) {
						if (!touchesEnfoncees.isEmpty()) {
							updateDeplacement();
						}
						Thread.sleep(delay);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	synchronized protected void updateDeplacement() {
		oldPosition = heros.getPosition();
		if (touchesEnfoncees.contains(KeyEvent.VK_W)) {
			heros.forward();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_S)) {
			heros.backward();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_A)) {
			heros.strafeLeft();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_D)) {
			heros.strafeRight();
		}

		// collisison avec les murs
		if (map.inWall(heros.getPosition())) {
			moveAlongWalls();
		}

		if (touchesEnfoncees.contains(KeyEvent.VK_LEFT)) {
			heros.rotateLeft();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_RIGHT)) {
			heros.rotateRight();
		}

		// test packs

		// Utilisation d'un itérateur car on supprime un objet d'une liste qu'on
		// parcourt
		Iterator<Thing> iterator = listeThings.iterator();
		while (iterator.hasNext()) {
			Thing thing = iterator.next();
			if (collapse(thing.getPosition(), 1.2)) {
				switch (thing.getClass().getSimpleName()) {
				case "HandGun":
					if (touchesEnfoncees.contains(KeyEvent.VK_E)) {
						heros.setArme(new HandGun(heros.getPosition()));
						repopObjet(thing.getPosition(), thing.getClass().getSimpleName());
						iterator.remove();
					} else if (heros.getArme() != null) {
						heros.getArme().sumAmmo(10);
						repopObjet(thing.getPosition(), thing.getClass().getSimpleName());
						iterator.remove();
					}
					break;
				}
			}
			if (collapse(thing.getPosition(), .8)) {
				System.out.println(thing.getClass().getSimpleName());
				switch (thing.getClass().getSimpleName()) {
				case "Ennemie":
					heros.perdVie(5);
					break;
				case "Armure":
					heros.ajoutArmure(10);
					repopObjet(thing.getPosition(), thing.getClass().getSimpleName());
					iterator.remove();
					break;
				case "Medipack":
					heros.ajoutVie(10);
					repopObjet(thing.getPosition(), thing.getClass().getSimpleName());
					iterator.remove();
					break;
				}
				System.out.println("vie restante: " + heros.getVie() + " / armure restante: " + heros.getArmure());
			}

		}
	}

	private void repopObjet(Vector2D position, String type) {
		Thread threadrepop = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(tempsRepop);
					switch (type) {
					case "Armure":
						listeThings.add(new Armure(position));
						break;
					case "Medipack":
						listeThings.add(new Medipack(position));
						break;
					case "HandGun":
						listeThings.add(new HandGun(position));
						break;
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		threadrepop.start();

	}

	private void moveAlongWalls() {
		double newx = heros.getPosition().getdX();
		double newy = heros.getPosition().getdY();
		double oldx = oldPosition.getdX();
		double oldy = oldPosition.getdY();

		int caseX = (int) oldx;
		int caseY = (int) oldy;

		if (oldx <= newx) {
			if (oldy <= newy)
				// bas droite
				testAndMove(newx, newy, caseX + .99, caseY + .99);
			else
				// haut droite
				testAndMove(newx, newy, caseX + .99, caseY);
		} else {
			if (oldy <= newy)
				// bas gauche
				testAndMove(newx, newy, caseX, caseY + .99);
			else
				// haut gauche
				testAndMove(newx, newy, caseX, caseY);
		}
	}

	private void testAndMove(double newx, double newy, double lockX, double lockY) {
		// on essaye de glisser horizontalement
		if (!map.inWall(newx, lockY)) {
			// on déplace si on est pas dans un mur
			heros.setPosition(newx, lockY);
		} else if (!map.inWall(lockX, newy)) {
			// sinon, on essaye de glisser verticalement
			heros.setPosition(lockX, newy);
		} else {
			// on est dans un mur dans les deux cas
			// on ne bouge plus, on se met dans le coin
			heros.setPosition(lockX, lockY);
		}
	}

	private boolean collapse(Vector2D point, double r) {
		double x = heros.getPosition().getdX();
		double y = heros.getPosition().getdY();
		return (x >= point.getdX() - r && x <= point.getdX() + r && y >= point.getdY() - r && y <= point.getdY() + r);
	}

	private boolean collision(Thing truc) {
		return map.inWall(truc.getPosition());
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (!touchesEnfoncees.contains(e.getKeyCode())) {
				fire();
			}
		}

		touchesEnfoncees.add(e.getKeyCode());
	}

	protected synchronized void fire() {
		if (heros.getArme() != null) {

			Thread threadRoF = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						isFiring = true;
						Thread.sleep((long) (1000 / heros.getArme().getRoF()));
						isFiring = false;
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

				}
			});

			if (!isFiring && heros.getArme().getAmmo() > 0) {

				threadRoF.start();

				heros.getArme().sousAmmo(1);

				double posx = heros.getPosition().getdX();
				double posy = heros.getPosition().getdY();
				double dirx = heros.getDirection().getdX();
				double diry = heros.getDirection().getdY();
				double r = .5;

				double d = algoPiergiovanni.algoRaycasting(heros.getPosition(), heros.getDirection(), map);

				fireLine = new Line2D.Double(posx, posy, posx + dirx * d, posy + diry * d);

				Iterator<Ennemie> iterator = listEnnemie.iterator();
				while (iterator.hasNext()) {
					Ennemie ennemie = iterator.next();
					Rectangle2D rect = new Rectangle2D.Double(ennemie.getPosition().getdX() - r / 2,
							ennemie.getPosition().getdY() - r / 2, r, r);
					if (fireLine.intersects(rect)) {
						ennemie.perdVie(heros.getArme().computeDamage(d));
						System.out.println("Ennemie  : vie restante: " + ennemie.getVie() + " / armure restante: "
								+ ennemie.getArmure());

					}
				}
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		touchesEnfoncees.remove(e.getKeyCode());
	}

	public LvlMap getMap() {
		return map;
	}

}
