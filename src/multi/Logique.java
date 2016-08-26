package multi;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import multi.thing.Armure;
import multi.thing.Key;
import multi.thing.Medipack;
import multi.thing.Monstre;
import multi.thing.Thing;
import multi.thing.personnage.Ennemi;
import multi.thing.personnage.Joueur;
import multi.thing.weapon.AmmoPackHG;
import multi.thing.weapon.AmmoPackPR;
import multi.thing.weapon.AmmoPackSG;
import multi.thing.weapon.AmmoPackSmG;
import multi.thing.weapon.Cut;
import multi.thing.weapon.AmmoPack;
import multi.thing.weapon.HandGun;
import multi.thing.weapon.PrecisionRifle;
import multi.thing.weapon.ShootGun;
import multi.thing.weapon.SubmachineGun;
import multi.thing.weapon.Weapon;
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
	private boolean mort;
	public boolean waitRespawn;
	private long tempsRespawn;

	// test vie et armure
	protected ArrayList<Ennemi> listEnnemie;

	public Logique(String nomMap) {
		delay = 10;
		tempsRepop = 15000;
		tempsRespawn = 5000;

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

		mort = false;
		waitRespawn = false;

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
						// updateEnnemis();
						Thread.sleep(delay);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	protected void updateEnnemis() {

		Iterator<Ennemi> iter = listEnnemie.iterator();

		while (iter.hasNext()) {
			Ennemi monstre = iter.next();

			Vector2D oldPos = monstre.getPosition();

			monstre.bouge();

			// collisison avec les murs
			if (collision(monstre)) {
				monstre.setPosition(oldPos);
				monstre.setDirection(monstre.getDirection().rotate(Math.PI));
			}
		}
	}

	synchronized protected void updateDeplacement() {
		mort = false;

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
		if (map.inWall(heros.getPosition()) && !heros.getMort()) {
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
		while (iterator.hasNext() && !mort) {
			Thing thing = iterator.next();
			if (collapse(thing.getPosition(), 1.2) && !heros.getMort()) {
				if (thing instanceof Weapon) {
					if (thing instanceof HandGun) {
						if (touchesEnfoncees.contains(KeyEvent.VK_E)) {
							heros.setArme(new HandGun(heros.getPosition()));
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						} else if (heros.getArme() != null && heros.getArme() instanceof HandGun) {
							heros.getArme().sumAmmo(10);
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
					if (thing instanceof SubmachineGun) {
						if (touchesEnfoncees.contains(KeyEvent.VK_E)) {
							heros.setArme(new SubmachineGun(heros.getPosition()));
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						} else if (heros.getArme() != null && heros.getArme() instanceof SubmachineGun) {
							heros.getArme().sumAmmo(30);
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
					if (thing instanceof ShootGun) {
						if (touchesEnfoncees.contains(KeyEvent.VK_E)) {
							heros.setArme(new ShootGun(heros.getPosition()));
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						} else if (heros.getArme() != null && heros.getArme() instanceof ShootGun) {
							heros.getArme().sumAmmo(30);
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
					if (thing instanceof PrecisionRifle) {
						if (touchesEnfoncees.contains(KeyEvent.VK_E)) {
							heros.setArme(new PrecisionRifle(heros.getPosition()));
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						} else if (heros.getArme() != null && heros.getArme() instanceof PrecisionRifle) {
							heros.getArme().sumAmmo(30);
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
					if (thing instanceof Cut) {
						if (touchesEnfoncees.contains(KeyEvent.VK_E)) {
							heros.setArme(new Cut(heros.getPosition()));
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						} else if (heros.getArme() != null && heros.getArme() instanceof Cut) {
							heros.getArme().sumAmmo(30);
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
				}
			}
			if (collapse(thing.getPosition(), .8) && !heros.getMort()) {
				if (thing instanceof Ennemi) {
					heros.perdVie(5);
					animationDegatsPris();
					if (heros.getMort()) {
						mort = true;
						respawn();
					}
				}
				if (thing instanceof Armure) {
					heros.ajoutArmure(10);
					repopObjet(thing.getPosition(), thing);
					iterator.remove();
				}
				if (thing instanceof Medipack) {
					heros.ajoutVie(10);
					repopObjet(thing.getPosition(), thing);
					iterator.remove();
				}
				if (thing instanceof AmmoPack) {
					if (thing instanceof AmmoPackHG) {
						if (heros.getArme() != null && heros.getArme() instanceof HandGun) {

							heros.getArme().sumAmmo(AmmoPackSmG.getAmmo());
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
					if (thing instanceof AmmoPackSmG) {
						if (heros.getArme() != null && heros.getArme() instanceof SubmachineGun) {
							heros.getArme().sumAmmo(AmmoPackSmG.getAmmo());
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
					if (thing instanceof AmmoPackSG) {
						if (heros.getArme() != null && heros.getArme() instanceof ShootGun) {
							heros.getArme().sumAmmo(AmmoPackSG.getAmmo());
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
					if (thing instanceof AmmoPackPR) {
						if (heros.getArme() != null && heros.getArme() instanceof PrecisionRifle) {
							heros.getArme().sumAmmo(AmmoPackPR.getAmmo());
							repopObjet(thing.getPosition(), thing);
							iterator.remove();
						}
					}
				}
			}

		}
	}

	private void animationDegatsPris() {
		Thread threadDegats = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					heros.resetPrendDegats();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		threadDegats.start();

	}

	private void respawn() {

		heros.setArme(null);
		Thread threadTimerRespawn = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(tempsRespawn);
					// Récupérer position de tous les ennemis et
					// calculer
					// point central
					Vector2D vecPointCentral = new Vector2D();
					for (int i = 0; i < listEnnemie.size(); i++) {
						vecPointCentral = vecPointCentral.add(listEnnemie.get(i).getPosition());

					}

					vecPointCentral = vecPointCentral.div(listEnnemie.size());

					Vector2D pointRespawn = new Vector2D();

					// Calculer point le plus loin de la liste des
					// positions de départ
					double normCentre = vecPointCentral.norm();
					double normMax = 0;

					for (int i = 0; i < map.getListStartPosition().size(); i++) {

						if (Math.abs(normCentre - map.getListStartPosition().get(i).norm()) > normMax) {
							normMax = Math.abs(normCentre - map.getListStartPosition().get(i).norm());

							pointRespawn.setdX(map.getListStartPosition().get(i).getdX());
							pointRespawn.setdY(map.getListStartPosition().get(i).getdY());

						}

					}
					heros.respawn();
					heros.setPosition(pointRespawn);
					mort = false;

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		threadTimerRespawn.start();
	}

	private void repopObjet(Vector2D position, Thing type) {
		Thread threadrepop = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(tempsRepop);
					if (type instanceof Armure) {
						listeThings.add(new Armure(position));
					}
					if (type instanceof Medipack) {
						listeThings.add(new Medipack(position));
					}
					if (type instanceof AmmoPackHG) {
						listeThings.add(new AmmoPackHG(position));
					}
					if (type instanceof AmmoPackSmG) {
						listeThings.add(new AmmoPackSmG(position));
					}
					if (type instanceof AmmoPackSG) {
						listeThings.add(new AmmoPackSG(position));
					}
					if (type instanceof AmmoPackPR) {
						listeThings.add(new AmmoPackPR(position));
					}
					if (type instanceof HandGun) {
						listeThings.add(new HandGun(position));
					}
					if (type instanceof SubmachineGun) {
						listeThings.add(new SubmachineGun(position));
					}
					if (type instanceof ShootGun) {
						listeThings.add(new ShootGun(position));
					}
					if (type instanceof PrecisionRifle) {
						listeThings.add(new PrecisionRifle(position));
					}
					if (type instanceof Cut) {
						listeThings.add(new Cut(position));
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
		touchesEnfoncees.add(e.getKeyCode());
	}

	public void mousePressed() {
		Thread threadFire = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					isFiring = true;
					while (FenetreJeu.mousePressed) {
						fire();
						Thread.sleep((long) (1000 / heros.getArme().getRoF()));
					}
					isFiring = false;
				} catch (InterruptedException e) {
					e.printStackTrace();

				}
			}
		});

		if (heros.getArme() != null && !isFiring) {
			threadFire.start();
		}

	}

	protected synchronized void fire() {

		if (heros.getArme().getAmmo() > 0) {

			heros.getArme().subAmmo(1);
			heros.getArme().setFiring(true);

			double posx = heros.getPosition().getdX();
			double posy = heros.getPosition().getdY();
			double dirx = heros.getDirection().getdX();
			double diry = heros.getDirection().getdY();
			double r = 0.8;

			double d = algoPiergiovanni.algoRaycasting(heros.getPosition(), heros.getDirection(), map);

			fireLine = new Line2D.Double(posx, posy, posx + dirx * d, posy + diry * d);

			Ennemi ennemiTouche = null;

			Iterator<Ennemi> iterator = listEnnemie.iterator();
			while (iterator.hasNext()) {
				Ennemi ennemie = iterator.next();
				Rectangle2D rect = new Rectangle2D.Double(ennemie.getPosition().getdX() - r / 2,
						ennemie.getPosition().getdY() - r / 2, r, r);

				if (fireLine.intersects(rect)) {
					fireLine.setLine(posx, posy, ennemie.getPosition().getdX(), ennemie.getPosition().getdY());
					ennemiTouche = ennemie;
				}
			}

			if (ennemiTouche != null) {
				ennemiTouche.perdVie(heros.getArme().computeDamage(fireLine.getP1().distance(fireLine.getP2())));
				System.out.println("Ennemie " + ennemiTouche.hashCode() + " : vie restante: " + ennemiTouche.getVie()
						+ " / armure restante: " + ennemiTouche.getArmure());
				if (ennemiTouche.getMort()) {
					listEnnemie.remove(ennemiTouche);
					listeThings.remove(ennemiTouche);
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
