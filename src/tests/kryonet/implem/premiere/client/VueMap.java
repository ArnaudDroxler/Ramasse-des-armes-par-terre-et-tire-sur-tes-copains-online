package tests.kryonet.implem.premiere.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;

import multi.thing.Thing;
import multi.thing.personnage.Ennemi;
import multi.thing.personnage.Personnage;

public class VueMap  extends Renderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double zoom;
	private Graphics2D g2d;
	
	public VueMap(LogiqueClient logiqueClient) {
		super(logiqueClient);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double zoomx = getWidth() / (double) lc.map.getMapBackground().getWidth();
				double zoomy = getHeight() / (double) lc.map.getMapBackground().getHeight();
				zoom = Math.min(zoomx, zoomy);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		
		AffineTransform init = g2d.getTransform();
		g2d.scale(zoom, zoom);
		g2d.drawImage(lc.map.getMapBackground(), 0, 0, null);
		g2d.setTransform(init);

		g2d.setColor(Color.red);
		drawPersonnage(lc.joueur, init);
		drawAutresJoueurs(init);
	}

	private void drawPersonnage(Personnage perso, AffineTransform init) {
		int a = (int) zoom;
		
		g2d.translate(perso.getPosition().getdX() * zoom, perso.getPosition().getdY() * zoom);
		g2d.rotate(perso.getDirection().getTheta());
		g2d.fillOval((int) (- zoom / 2), ((int) (- zoom / 2)), a, a);
		g2d.fillRect(0, -1, (int) zoom, 2);
		g2d.setTransform(init);
		/*
		if (lc.isFiring) {
			drawTir();
		}
		*/
	}

	private void drawTir() {
		/*
		double x = lc.fireLine.getX2() - lc.fireLine.getX1();
		double y = lc.fireLine.getY2() - lc.fireLine.getY1();
		double d = Math.sqrt(x * x + y * y);
		g2d.drawLine(0, 0, (int) (d * zoom), 0);
		*/
	}

	private void drawAutresJoueurs(AffineTransform init) {
		g2d.setColor(Color.green);
		int a = (int) zoom;
		// a prot�ger
		for (Ennemi ennemi : lc.listEnnemis) {
			drawPersonnage(ennemi, init);
		}
	}

}
