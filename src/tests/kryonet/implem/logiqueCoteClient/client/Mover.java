package tests.kryonet.implem.logiqueCoteClient.client;

import java.awt.event.KeyEvent;
import java.util.HashSet;

import multi.thing.Thing;
import multi.tools.map.LvlMap;
import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.logiqueCoteClient.server.JoueurOnline;

public class Mover {

	private static LogiqueClient lc;

	public static void setLogique(LogiqueClient logiqueClient) {
		lc = logiqueClient;
	}

	public static void updateDeplacement() {
		lc.oldPosition = lc.joueur.getPosition();
		if (lc.touchesEnfoncees.contains(KeyEvent.VK_W)) {
			lc.joueur.forward();
		}
		if (lc.touchesEnfoncees.contains(KeyEvent.VK_S)) {
			lc.joueur.backward();
		}
		if (lc.touchesEnfoncees.contains(KeyEvent.VK_A)) {
			lc.joueur.strafeLeft();
		}
		if (lc.touchesEnfoncees.contains(KeyEvent.VK_D)) {
			lc.joueur.strafeRight();
		}

		// collisison avec les murs
		if (lc.map.inWall(lc.joueur.getPosition()) && !lc.joueur.getMort()) {
			moveAlongWalls();
		}

		if (lc.touchesEnfoncees.contains(KeyEvent.VK_LEFT)) {
			lc.joueur.rotateLeft();
		}
		if (lc.touchesEnfoncees.contains(KeyEvent.VK_RIGHT)) {
			lc.joueur.rotateRight();
		}
	}
	
	private static void moveAlongWalls() {
		double newx = lc.joueur.getPosition().getdX();
		double newy = lc.joueur.getPosition().getdY();
		double oldx = lc.oldPosition.getdX();
		double oldy = lc.oldPosition.getdY();

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

	private static void testAndMove(double newx, double newy, double lockX, double lockY) {
		// on essaye de glisser horizontalement
		if (!lc.map.inWall(newx, lockY)) {
			// on déplace si on est pas dans un mur
			lc.joueur.setPosition(newx, lockY);
		} else if (!lc.map.inWall(lockX, newy)) {
			// sinon, on essaye de glisser verticalement
			lc.joueur.setPosition(lockX, newy);
		} else {
			// on est dans un mur dans les deux cas
			// on ne bouge plus, on se met dans le coin
			lc.joueur.setPosition(lockX, lockY);
		}
	}

}
