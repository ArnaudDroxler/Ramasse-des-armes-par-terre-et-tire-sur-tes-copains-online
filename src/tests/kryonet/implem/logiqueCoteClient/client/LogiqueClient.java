package tests.kryonet.implem.logiqueCoteClient.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import multi.thing.Thing;
import multi.tools.map.ImageParser;
import multi.tools.map.LvlMap;
import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.logiqueCoteClient.server.JoueurOnline;
import tests.kryonet.implem.logiqueCoteClient.server.Partie;

public class LogiqueClient/* extends KeyAdapter */{

	protected static final long delay = 20;
	protected boolean fin;
	protected LvlMap map;
	protected HashMap<Integer, JoueurOnline> joueurs;
	protected ArrayList<Thing> objets;
	protected JoueurOnline joueur;
	protected Vector2D oldPosition;
	protected HashSet<Integer> touchesEnfoncees;
	protected int joueurId;

	public LogiqueClient(String nomMap, Partie partie, int i) {
		touchesEnfoncees = new HashSet<Integer>(6);
		fin = false;
		map = ImageParser.getMap(nomMap);
		oldPosition = map.getStartPosition();
		
		joueurId=i;
		joueurs = partie.getJoueurs();
		joueur=joueurs.get(i);
		joueur.setPosition(oldPosition);
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
/*
	@Override
	public void keyPressed(KeyEvent e) {
		touchesEnfoncees.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		touchesEnfoncees.remove(e.getKeyCode());
	}
	*/
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
		if (map.inWall(joueur.getPosition()) && !joueur.getMort()) {
			moveAlongWalls();
		}

		if (touchesEnfoncees.contains(KeyEvent.VK_LEFT)) {
			joueur.rotateLeft();
		}
		if (touchesEnfoncees.contains(KeyEvent.VK_RIGHT)) {
			joueur.rotateRight();
		}

	}

	public void updatePartie(Partie partie) {
		joueurs = partie.getJoueurs();
		// mauvaise solution car très peu performante :
		//joueur = ennemis.remove(joueurId);
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

	public void mouseLeftPressed() {
		// TODO Auto-generated method stub
		
	}

	public void setAffichageScore(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
