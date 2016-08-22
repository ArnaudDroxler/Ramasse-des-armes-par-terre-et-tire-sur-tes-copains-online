package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;

import base.thing.Thing;

public class VueJeu extends Renderer {
	
	private static final long serialVersionUID = -6740877510021842175L;
	private double zoom;
	private Graphics2D g2d;
	
	public VueJeu(Logique _logique) {
		super(_logique);
		
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				double zoomx = getWidth() / (double) logique.map.getMapBackground().getWidth();
				double zoomy = getHeight() / (double) logique.map.getMapBackground().getHeight();
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
		g2d.drawImage(logique.map.getMapBackground(), 0, 0, null);
		g2d.setTransform(init);

		try{
			drawBut();
			drawMonstres();
		}catch(Exception e){
			//System.err.println("yo");
		}

		drawHeros();
	}

	private void drawBut() {
		g2d.setColor(Color.yellow);
		int x = (int) (logique.but.getdX() * zoom);
		int y = (int) (logique.but.getdY() * zoom);
		g2d.fillRect(x, y, (int) zoom, (int) zoom);
	}

	private void drawTir() {
		double x = logique.fireLine.getX2() - logique.fireLine.getX1();
		double y = logique.fireLine.getY2() - logique.fireLine.getY1();
		double d = Math.sqrt(x * x + y * y);
		g2d.drawLine(0, 0, (int) (d * zoom), 0);
	}

	private void drawHeros() {
		g2d.translate(logique.heros.getPosition().getdX() * zoom, logique.heros.getPosition().getdY() * zoom);
		g2d.rotate(logique.heros.getDirection().getTheta());
		
		g2d.setColor(Color.red);
		g2d.fillRect((int) -zoom / 2, (int) -zoom / 2, (int) zoom, (int) zoom);
		g2d.setColor(Color.blue);
		g2d.fillOval((int) -zoom / 4, (int) -zoom / 4, (int) zoom / 2, (int) zoom / 2);
		g2d.fillRect(0, -1, (int) zoom, 2);

		if (logique.isFiring) {
			drawTir();
		}

	}

	private void drawMonstres() {
		g2d.setColor(Color.green);
		int a = (int) zoom;
		// a protéger
		for (Thing monstre : logique.listeMonstres) {
			double x = (monstre.getPosition().getdX() * zoom);
			double y = (monstre.getPosition().getdY() * zoom);
			g2d.fillOval((int) (x - zoom / 2), ((int) (y - zoom / 2)), a, a);
		}
	}

}
