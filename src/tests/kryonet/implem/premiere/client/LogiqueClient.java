package tests.kryonet.implem.premiere.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import multi.thing.personnage.Ennemi;
import multi.thing.personnage.Joueur;
import multi.thing.personnage.Personnage;
import multi.tools.map.ImageParser;
import multi.tools.map.LvlMap;
import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.premiere.server.JoueurOnline;
import tests.kryonet.implem.premiere.server.Partie;

public class LogiqueClient extends KeyAdapter {

	protected static final long delay = 150;
	protected boolean fin;
	protected LvlMap map;
	protected ArrayList<Ennemi> listEnnemis;
	protected Joueur joueur;
	private Vector2D oldPosition;
	private HashSet<Integer> touchesEnfoncees;
	private int joueurId;

	public LogiqueClient(String nomMap) {
		touchesEnfoncees = new HashSet<Integer>(6);
		fin = false;
		map = ImageParser.getMap(nomMap);
		oldPosition = map.getStartPosition();
		joueur = new Joueur(oldPosition, new Vector2D(1, 0));
		listEnnemis = new ArrayList<Ennemi>(8);
		animer();
	}	

	private void animer() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (!fin) {
						if (!touchesEnfoncees.isEmpty()) {
							//updateDeplacement();
							joueur.forward();
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

	public void updatePartie(Partie partie) {
		HashMap<Integer, JoueurOnline> joueurs = partie.getJoueurs();
		JoueurOnline j = joueurs.remove(joueurId);
		joueur.setPosition(j.posx, j.posy);
		listEnnemis = new ArrayList<Ennemi>(8);
		for (JoueurOnline autreJoueur : joueurs.values()) {
			listEnnemis.add(new Ennemi(new Vector2D(autreJoueur.posx, autreJoueur.posy),new Vector2D(1,0)));
		}
	}

	public void setId(int id) {
		joueurId = id;
	}
	
}
