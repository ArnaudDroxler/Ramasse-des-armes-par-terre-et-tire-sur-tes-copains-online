package toutdansunpackage.server;

import toutdansunpackage.tools.raycasting.Vector2D;
import toutdansunpackage.tools.raycasting.algoPiergiovanni;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;

import multi.thing.personnage.Ennemi;
import toutdansunpackage.thing.weapon.ShootGun;
import toutdansunpackage.thing.weapon.Weapon;
import toutdansunpackage.tools.map.ImageParser;
import toutdansunpackage.tools.map.LvlMap;

public class LogiqueServer {

	private LvlMap map;
	private Partie partie;
	
	// pour la méthode fire
	private Line2D fireLine;
	private Rectangle2D rect;
	private static final double r=0.8;
	private JoueurOnline shooter;
	
	private PcServer pcServer;
	
	public LogiqueServer(String nomMap, Partie partie, PcServer pcServer) {
		this.partie = partie;
		map = ImageParser.getMap(nomMap);
		
		this.pcServer = pcServer;
		
		rect = new Rectangle2D.Double();
	}

	public void fireFromPlayer(int clientId) {
		shooter = partie.getJoueurs().get(clientId);

		Vector2D pos = shooter.getPosition();
		Vector2D dir = shooter.getDirection();
		
		Weapon arme = shooter.getArme();
		
		if(arme instanceof ShootGun){
			for(int i=0;i<5;i++){
				fire(pos, dir.rotate(10*i - 20), arme);
			}
		}else{
			fire(pos,dir,arme);
		}
		
	}

	private void fire(Vector2D pos, Vector2D dir, Weapon arme) {
		double d = algoPiergiovanni.algoRaycasting(pos, dir, map);
		
		double x1 = pos.getdX();
		double y1 = pos.getdY();
		double x2 = pos.getdX() + dir.getdX() * d;
		double y2 = pos.getdY() + dir.getdY() * d;
		fireLine = new Line2D.Double(x1, y1, x2, y2);
		
		JoueurOnline ennemiTouche = null;
		
		Iterator<JoueurOnline> iterator = partie.getJoueurs().values().iterator();
		while (iterator.hasNext()) {
			JoueurOnline ennemi = iterator.next();
			if(ennemi != shooter){
				rect.setRect(ennemi.getPosition().getdX() - r / 2, ennemi.getPosition().getdY() - r / 2, r, r);
				
				if (fireLine.intersects(rect)) {
					fireLine.setLine(x1, y1, ennemi.getPosition().getdX(), ennemi.getPosition().getdY());
					ennemiTouche = ennemi;
				}
			}
		}
		if(ennemiTouche!=null){
			int degats = arme.computeDamage(fireLine.getP1().distance(fireLine.getP2()));
			ennemiTouche.perdVie(degats);
			if(ennemiTouche.getMort()){
				pcServer.sendKillMessage(shooter,ennemiTouche);
			}else{
				pcServer.sendDamageMessage(ennemiTouche, degats);
			}
		}
		
		
	}

}
