package rattco.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;

import rattco.thing.personnage.JoueurOnline;

public class VueMap extends Renderer {

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
		drawAutresJoueurs(init);
		g2d.setColor(Color.green);
		drawPersonnage(lc.joueur, init);
	}

	private void drawPersonnage(JoueurOnline joueur, AffineTransform init) {
		int a = (int) zoom;

		g2d.translate(joueur.getPosition().getdX() * zoom, joueur.getPosition().getdY() * zoom);
		// g2d.drawString(joueur.pseudo, 0, (int) -zoom);
		printSimpleString(joueur.pseudo);
		// g2d.rotate(joueur.getDirection().getTheta());
		g2d.rotate(Math.atan2(joueur.getDirection().getdY(), joueur.getDirection().getdX()));
		g2d.fillOval((int) (-zoom / 2), ((int) (-zoom / 2)), a, a);
		g2d.fillRect(0, -1, (int) zoom, 2);
		g2d.setTransform(init);
		/*
		 * if (lc.isFiring) { drawTir(); }
		 */
	}

	private void drawTir() {
		/*
		 * double x = lc.fireLine.getX2() - lc.fireLine.getX1(); double y =
		 * lc.fireLine.getY2() - lc.fireLine.getY1(); double d = Math.sqrt(x * x
		 * + y * y); g2d.drawLine(0, 0, (int) (d * zoom), 0);
		 */
	}

	private void drawAutresJoueurs(AffineTransform init) {
		int a = (int) zoom;
		// a protéger
		for (JoueurOnline joueur : lc.joueurs.values()) {
			drawPersonnage(joueur, init);
		}
	}

	private void printSimpleString(String s) {
		int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
		int start = -stringLen / 2;
		g2d.drawString(s, start, (int) -zoom);
	}

}
