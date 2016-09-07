package multi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.Iterator;

import multi.thing.Thing;
import multi.thing.personnage.Ennemi;

public class VueJeu extends Renderer {

	private static final long serialVersionUID = -6740877510021842175L;
	private double zoom;
	private Graphics2D g2d;

	public VueJeu(Logique _logique) {
		super(_logique);

		addComponentListener(new ComponentAdapter(){
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

		drawMonstres(init);
		drawHeros();
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

	private void drawTir() {

		// double x = logique.fireLine.getX2() - logique.fireLine.getX1();
		// double y = logique.fireLine.getY2() - logique.fireLine.getY1();
		// double d = Math.sqrt(x * x + y * y); g2d.drawLine(0, 0, (int) (d
		// *zoom), 0);

		int i = 0;
		Iterator<Line2D> iterator = logique.fireLineList.iterator();
		while (iterator.hasNext()) {
			Line2D fireLine1 = iterator.next();

			// Line2D fireLine = logique.fireLineList.get(3);

			double x = fireLine1.getX2() - fireLine1.getX1();
			double y = fireLine1.getY2() - fireLine1.getY1();
			double d = Math.sqrt(x * x + y * y);

			g2d.drawLine(0, 0, (int) (y * zoom), (int) (x * zoom));
			// g2d.drawLine((int) fireLine.getX1(),(int)
			// fireLine.getX2(),(int)fireLine.getY1(), (int)fireLine.getY2());

		}
	}
	private void drawMonstres(AffineTransform init) {
		g2d.setColor(Color.green);
		int a = (int) zoom;
		// a prot�ger
		for (Thing monstre : logique.listEnnemie) {
			g2d.translate(monstre.getPosition().getdX() * zoom, monstre.getPosition().getdY() * zoom);
			g2d.rotate(monstre.getDirection().getTheta());
			g2d.fillOval((int) (-zoom / 2), ((int) (-zoom / 2)), a, a);
			g2d.fillRect(0, -1, (int) zoom, 2);
			g2d.setTransform(init);
		}
	}

}
