package base;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import base.thing.Héros;
import base.thing.Key;
import base.thing.Monstre;
import base.thing.Thing;
import base.tools.GeometricTools;
import base.tools.map.ImageParser;
import base.tools.map.LvlMap;
import base.tools.raycasting.Vector2D;
import base.tools.raycasting.algoPiergiovanni;

public class Logique extends KeyAdapter {

	protected LvlMap map;
	protected Thing heros;
	protected ArrayList<Monstre> listeMonstres;
	private long delay;
	private HashSet<Integer> touchesEnfoncees;
	private Vector2D oldPosition;
	protected boolean isFiring;
	protected Vector2D but;
	protected Line2D fireLine;
	private boolean modeRafale;
	protected boolean fin;
	private Key cle;
	private boolean cleRecuperee;

	public Logique(String nomMap) {
		delay = 10;

		map = ImageParser.getMap(nomMap);

		oldPosition = map.getStartPosition();

		heros = new Héros(oldPosition, new Vector2D(1, 0));
		heros.setVitesse(0.05);

		listeMonstres = map.getListMonstre();

		touchesEnfoncees = new HashSet<Integer>(6);
		but = map.getGoalPosition();

		cle = map.getKey();
		cleRecuperee = false;

		fireLine = new Line2D.Double(0, 0, 0, 0);

		modeRafale = false;

		fin = false;

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
						// updateMonstres();
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

		// collisison avec les monstres
		for (Thing thing : listeMonstres) {
			if (collapse(thing.getPosition(), .8)) {
				System.out.println("perdu !");
				fin = true;
				new FenetreFin("Perdu !");
			}
		}

		// collisison avec le but
		if (cleRecuperee && collapse(but, 1)) {
			System.out.println("gagné !");
			fin = true;
			new FenetreFin("Gagné !");
		}

		// collisison avec la clé
		if (collapse(cle.getPosition(), 1)) {
			map.getListThing().remove(cle);
			cleRecuperee = true;
		}

		if (touchesEnfoncees.contains(KeyEvent.VK_LEFT)) {
			heros.rotateLeft();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_RIGHT)) {
			heros.rotateRight();
		}

		if (touchesEnfoncees.contains(KeyEvent.VK_SPACE)) {
			if (modeRafale) {
				fire();
			}
		}

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
				isFiring = true;
			}
		}

		touchesEnfoncees.add(e.getKeyCode());
	}

	protected synchronized void fire() {
		double posx = heros.getPosition().getdX();
		double posy = heros.getPosition().getdY();
		double dirx = heros.getDirection().getdX();
		double diry = heros.getDirection().getdY();
		double r = .5;

		double d = algoPiergiovanni.algoRaycasting(heros.getPosition(), heros.getDirection(), map);

		fireLine = new Line2D.Double(posx, posy, posx + dirx * d, posy + diry * d);

		Thing monstreAEnlever = null;

		Iterator<Monstre> iter = listeMonstres.iterator();

		while (iter.hasNext()) {
			Monstre monstre = iter.next();
			Rectangle2D rect = new Rectangle2D.Double(monstre.getPosition().getdX() - r / 2,
					monstre.getPosition().getdY() - r / 2, r, r);

			if (fireLine.intersects(rect)) {
				monstreAEnlever = monstre;
				fireLine.setLine(posx, posy, monstre.getPosition().getdX(), monstre.getPosition().getdY());
			}
		}

		if (monstreAEnlever != null) {
			// bizarre
			// a corriger
			listeMonstres.remove(monstreAEnlever);
			getMap().getListThing().remove(monstreAEnlever);
		}

	}

	synchronized protected void updateMonstres() {
		Iterator<Monstre> iter = listeMonstres.iterator();
		double alphaMax = Math.PI / 8;

		while (iter.hasNext()) {
			Thing monstre = iter.next();
			double randAlpha = Math.random() * alphaMax - alphaMax / 2;

			Vector2D oldPos = monstre.getPosition();

			monstre.forward();
			Vector2D direction = monstre.getDirection().rotate(randAlpha);
			monstre.setDirection(direction);

			// collisison avec les murs
			if (collision(monstre)) {
				monstre.setPosition(oldPos);
				monstre.setDirection(monstre.getDirection().rotate(Math.PI));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			isFiring = false;
		}
		touchesEnfoncees.remove(e.getKeyCode());
	}

	public LvlMap getMap() {
		return map;
	}

}
