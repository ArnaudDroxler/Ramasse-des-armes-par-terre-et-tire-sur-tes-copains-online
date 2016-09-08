package toutdansunpackage.client;

import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import toutdansunpackage.thing.personnage.Joueur;
import toutdansunpackage.thing.Armure;
import toutdansunpackage.thing.Medipack;
import toutdansunpackage.thing.Thing;
import toutdansunpackage.thing.weapon.Axe;
import toutdansunpackage.thing.weapon.ShootGun;
import toutdansunpackage.thing.weapon.Weapon;
import toutdansunpackage.tools.map.ImageParser;
import toutdansunpackage.tools.map.LvlMap;
import toutdansunpackage.tools.raycasting.Vector2D;
import toutdansunpackage.tools.raycasting.algoPiergiovanni;
import toutdansunpackage.messages.DamageMessage;
import toutdansunpackage.messages.KillMessage;
import toutdansunpackage.server.JoueurOnline;
import toutdansunpackage.server.Partie;

public class LogiqueClient{

	protected static final long delay = 20;
	protected static final long tempsRespawn = 5000;
	protected boolean fin;
	protected LvlMap map;
	protected HashMap<Integer, JoueurOnline> joueurs;
	protected ArrayList<Thing> objets;
	protected JoueurOnline joueur;
	protected Vector2D oldPosition;
	protected HashSet<Integer> touchesEnfoncees;
	private PcClient pcClient;
	protected boolean isFiring;
	private static final double r=0.8;
	protected ArrayList<Line2D> impactMurLine;
	protected ArrayList<Line2D> impactEnnemiLine;
	protected ArrayList<Line2D> fireLineList;

	public LogiqueClient(String nomMap, Partie partie, int playerId, PcClient pcClient) {
		touchesEnfoncees = new HashSet<Integer>(6);
		fin = false;
//		map = ImageParser.getMap(nomMap);
		map = ImageParser.getMapFromFolder("maps/dust");
//		map = ImageParser.getMapFromFolder(nomMap);
		objets = map.getListThing();
		
		this.pcClient=pcClient;

		joueurs = partie.getJoueurs();

		joueur = joueurs.get(playerId);
		joueur.setPosition(getPointRespawn());
		
		joueur.setArme(new Axe(joueur.getPosition()));
		animer();
		
		fireLineList = new ArrayList<Line2D>(5);
		impactEnnemiLine = new ArrayList<Line2D>(0);
		impactMurLine = new ArrayList<Line2D>(0);
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

	protected void updateDeplacement() {

		oldPosition = joueur.getPosition();
		if (touchesEnfoncees.contains(KeyEvent.VK_W)) {
			joueur.forward();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_S)) {
			joueur.backward();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_A)) {
			joueur.strafeLeft();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_D)) {
			joueur.strafeRight();
		}

		// collisison avec les murs
		if (map.inWall(joueur.getPosition())) {
			moveAlongWalls();
		}

		if (touchesEnfoncees.contains(KeyEvent.VK_LEFT)) {
			joueur.rotateLeft();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_RIGHT)) {
			joueur.rotateRight();
		}

		collectItems();


	}

	private void moveAlongWalls() {
		double newx = joueur.getPosition().getdX();
		double newy = joueur.getPosition().getdY();
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
				testAndMove(newx, newy, caseX + .99, caseY + .01);
		} else {
			if (oldy <= newy)
				// bas gauche
				testAndMove(newx, newy, caseX + .01, caseY + .99);
			else
				// haut gauche
				testAndMove(newx, newy, caseX + .01, caseY + .01);
		}
	}

	private void testAndMove(double newx, double newy, double lockX, double lockY) {
		// on essaye de glisser horizontalement
		if (!map.inWall(newx, lockY)) {
			// on déplace si on est pas dans un mur
			joueur.setPosition(newx, lockY);
		} else if (!map.inWall(lockX, newy)) {
			// sinon, on essaye de glisser verticalement
			joueur.setPosition(lockX, newy);
		} else {
			// on est dans un mur dans les deux cas
			// on ne bouge plus, on se met dans le coin
			joueur.setPosition(lockX, lockY);
		}
	}

	private void collectItems() {
		Thing thing;
		for (int i = 0; i < objets.size(); i++) {
			thing = objets.get(i);
			if (thing.exists() && collapse(thing.getPosition(), 1.2)) {
				if (thing instanceof Weapon) {
					if (touchesEnfoncees.contains(KeyEvent.VK_E)) {
						joueur.setArme((Weapon) thing);
						hide(thing);
					} else if (joueur.weaponIs(thing)) {
						joueur.getArme().sumAmmo();
						hide(thing);
					}
				} else {
					if (thing instanceof Armure){
						joueur.ajoutArmure(10);
						hide(thing);
					}else if (thing instanceof Medipack){
						joueur.ajoutVie(10);
						hide(thing);
					}else if(joueur.getArme()!= null && joueur.getArme().isAmmoPack(thing)){
						joueur.getArme().sumAmmo();
						hide(thing);
					}
				}
			}
		}
	}

	// envoie au serveur l'info que j'ai ramassé qqch
	private void hide(Thing thing) {
		thing.hideForAWhile();
		pcClient.sendPickUpMessage(objets.indexOf(thing));
	}

	// methode appelée par le serveur parce que qqn a ramassé qqch et il faut le cacher
	public void hideThing(int indexOfThing) {
		objets.get(indexOfThing).hideForAWhile();
	}


	public void updatePartie(Partie partie) {
		joueurs = partie.getJoueurs();
		// mauvaise solution car très peu performante :
		// joueur = ennemis.remove(joueurId);
	}

	public void mouseLeftPressed() {
		Thread threadFire = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					isFiring = true;
					while (JFrameClient.mouseLeftPressed && !joueur.getMort() && joueur.getArme().getAmmo() > 0) {
						fire();
						Thread.sleep((long) (1000 / joueur.getArme().getRoF()));
					}
					isFiring = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		if (joueur.getArme() != null && !isFiring) {
			threadFire.start();
		}
	}

	protected void fire() {
		
		fireLineList.clear();
		impactEnnemiLine.clear();
		impactMurLine.clear();
		
		// dépenser une munition
		joueur.getArme().subAmmo(1);
		
		// lancer l'animation
		joueur.getArme().setIsFiring(true);

		// informer le serveur
		pcClient.sendFireMessage(joueur.id);
		
		Vector2D pos = joueur.getPosition();
		Vector2D dir = joueur.getDirection();
		
		Weapon arme = joueur.getArme();
		
		double d = algoPiergiovanni.algoRaycasting(pos, dir, map);
		
		double x1 = pos.getdX();
		double y1 = pos.getdY();
		double x2 = pos.getdX() + dir.getdX() * d;
		double y2 = pos.getdY() + dir.getdY() * d;


		if (arme instanceof ShootGun) {
			Joueur perso = new Joueur(joueur.getPosition(), joueur.getDirection());
			perso.rotate(-30);
			for (int i = 0; i < 5; i++) {
				perso.rotate(10);
				fireLineList.add(new Line2D.Double(x1, y1, x2 + perso.getDirection().getdX() * d,
						y2 + perso.getDirection().getdY() * d));
			}
		} else {
			fireLineList.add(new Line2D.Double(x1,y1,x2,y2));
		}

		JoueurOnline ennemiTouche = null;

		for (Line2D line2d : fireLineList) {
			Iterator<JoueurOnline> iterator = joueurs.values().iterator();
			while (iterator.hasNext()) {
				JoueurOnline ennemi = iterator.next();
				if (ennemi.id != joueur.id) {
					Rectangle2D rect = new Rectangle2D.Double(ennemi.getPosition().getdX() - r / 2,ennemi.getPosition().getdY() - r / 2, r, r);
					if (line2d.intersects(rect)) {
						line2d.setLine(x1, y1, ennemi.getPosition().getdX(), ennemi.getPosition().getdY());
						ennemiTouche = ennemi;
					}
				}
				
			}
			if (ennemiTouche != null) {
				arme.setImpactEnnemi(true);
				impactEnnemiLine.add(line2d);
			} else {
				
				arme.setImpactMur(true);
				impactMurLine.add(line2d);
			}

		}
	}
	
	public void setAffichageScore(boolean b) {
		// TODO Auto-generated method stub

	}

	private boolean collapse(Vector2D point, double r) {
		// pas top
		// double x = joueur.getPosition().getdX();
		// double y = joueur.getPosition().getdY();
		// return (x >= point.getdX() - r && x <= point.getdX() + r && y >=

		// mieux : distance entre le point et le joueur < r ?
		return (point.sub(joueur.getPosition()).length() < r);
	}

	public void murderHappened(KillMessage km) {
		JoueurOnline killer = joueurs.get(km.getKillerId());
		JoueurOnline victim = joueurs.get(km.getVictimId());
		
		if(joueur.id == killer.id){
			System.out.println(joueur.pseudo + ", vous êtes un tueur");
			joueur.setNbKill();
		}else if(joueur.id == victim.id){
			System.out.println(joueur.pseudo + ", vous êtes une victime");
			joueur.tuer();
			respawn();
		}
		System.out.println(killer.pseudo + " a tué " + victim.pseudo);
		
	}

	public void sufferDamages(DamageMessage dm) {
		joueur.perdVie(dm.getDamages());
		System.out.println(joueur.pseudo + " perd " + dm.getDamages() + ", vie : " + joueur.getVie());
		
		Thread threadDegats = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					joueur.resetPrendDegats();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		threadDegats.start();
	}
	

	private void respawn() {
		joueur.setArme(null);
		Thread threadTimerRespawn = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(tempsRespawn);
					joueur.setPosition(getPointRespawn());
					joueur.respawn();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		threadTimerRespawn.start();
	}

	protected Vector2D getPointRespawn() {

		// Récupérer position de tous les ennemis et calculer point central
		Vector2D vecPointCentral = new Vector2D();
		for (JoueurOnline j : joueurs.values()) {
			vecPointCentral = vecPointCentral.add(j.getPosition());
		}
		vecPointCentral = vecPointCentral.div(joueurs.size());

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
		return pointRespawn;
	}

}
