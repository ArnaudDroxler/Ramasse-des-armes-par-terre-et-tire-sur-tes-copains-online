
package base;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import base.thing.Héros;
import base.thing.Thing;
import base.tools.MagasinImage;
import base.tools.raycasting.Vector2D;

public class Camera extends Renderer {

	private static final long serialVersionUID = -1160718986443478214L;

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Camera(Logique _logique) {
		super(_logique);

		// Sauvegarde des positions et directions de départ
		/*
		 * oldPos = new Vector2D(pos.getdX(), pos.getdY()); oldDir = new
		 * Vector2D(dir.getdX(), dir.getdY()); oldPlane = new
		 * Vector2D(plane.getdX(), plane.getdY());
		 */

		listThings = logique.map.getListThing();
		listAfficher = new TreeMap<Double, Thing>();

		posCamera = new Héros(logique.getMap().getStartPosition(), new Vector2D(1, 0));

		// Le plan représentant la vision
		plane = new Vector2D(0, 0);

		setDirection(this.posCamera.getDirection());

		control();
		appearance();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform oldContext = g2d.getTransform();
		draw(g2d);
		g2d.setTransform(oldContext);
	}

	public void resetCam() {
		posCamera.setPosition(oldPos);
		posCamera.setDirection(oldDir);
		plane.setdX(oldPlane.getdX());
		plane.setdY(oldPlane.getdY());
	}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setPosition(Vector2D position) {
		posCamera.setPosition(position);
	}

	public void setPosition(double x, double y) {
		posCamera.setPosition(x, y);
	}

	public void setDirection(Vector2D direction) {
		posCamera.setDirection(direction);
		Vector2D vec = direction.rotate(Math.PI / 2.0);
		vec.scale(0.66);
		plane.setdX(vec.getdX());
		plane.setdY(vec.getdY());
	}

	public void setDirection(double x, double y) {
		setDirection(new Vector2D(x, y));
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void control() {
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				int w = getWidth();
				int h = getHeight();
				tabDistStripes = new double[w];

				bufferThings = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				clearRect = new Rectangle2D.Double(0, 0, w, h);
				g2dBuff = bufferThings.createGraphics();
				// on relance le thread d'animation
				// animate();
			}

		});
	}

	private void appearance() {
		setBackground(Color.black);
	}

	private void draw(Graphics2D g2d) {
		if (g2dBuff != null) {
			// Ici ça permet de clear rapidement une bufferedImage
			AlphaComposite alphacomp = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
			g2dBuff.setComposite(alphacomp);
			g2dBuff.fill(clearRect);

			setPosition(logique.heros.getPosition());
			setDirection(logique.heros.getDirection());

			g2d.setBackground(Color.BLACK);
			try{
				algoRaycasting(g2d);
			}catch(Exception e){
				System.out.println("coucou y a probleme");
			}
			renderThings();
			g2d.drawImage(bufferThings, 0, 0, null);
			
			drawCursor(g2d);
			drawfire(g2d);
		}
	}

	private void drawfire(Graphics2D g2d) {
		if(logique.isFiring){
			g2d.drawLine(getWidth()/2, getHeight()/2, getWidth()*3/4, getHeight());
		}
			
	}

	private void drawCursor(Graphics2D g2d) {
		g2d.setColor(Color.red);
		g2d.drawLine(getWidth()/2,getHeight()/2-20,getWidth()/2,getHeight()/2+20);
		g2d.drawLine(getWidth()/2-20,getHeight()/2,getWidth()/2+20,getHeight()/2);
		g2d.drawLine(getWidth()/2-5,getHeight()/2-20,getWidth()/2+5,getHeight()/2-20);
		g2d.drawLine(getWidth()/2-5,getHeight()/2+20,getWidth()/2+5,getHeight()/2+20);
		g2d.drawLine(getWidth()/2-20,getHeight()/2-5,getWidth()/2-20,getHeight()/2+5);
		g2d.drawLine(getWidth()/2+20,getHeight()/2-5,getWidth()/2+20,getHeight()/2+5);
		
	}

	/**
	 * ======================================================== Algorithme
	 * adapté à partir du code C++ fournit par Lode Vandevenne
	 * http://lodev.org/cgtutor/raycasting.html
	 *
	 * Written by: Lode Vandevenne Copyright (c) 2004-2007
	 * ========================================================
	 */

	private void algoRaycasting(Graphics2D g2d) {
		int w = this.getWidth();
		int h = this.getHeight();
		for (int x = 0; x < w; x++) {
			double cameraX = 2 * x / (double) w - 1;
			Vector2D pos = posCamera.getPosition();
			double rayPosX = pos.getdX();
			double rayPosY = pos.getdY();
			Vector2D dir = posCamera.getDirection();
			double rayDirX = dir.getdX() + plane.getdX() * cameraX;
			double rayDirY = dir.getdY() + plane.getdY() * cameraX;

			int mapX = (int) rayPosX;
			int mapY = (int) rayPosY;

			double sideDistX;
			double sideDistY;

			double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
			double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
			double perpWallDist;

			int stepX;
			int stepY;

			int hit = 0;
			int side = 0;

			if (rayDirX < 0) {
				stepX = -1;
				sideDistX = (rayPosX - mapX) * deltaDistX;
			} else {
				stepX = 1;
				sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
			}
			if (rayDirY < 0) {
				stepY = -1;
				sideDistY = (rayPosY - mapY) * deltaDistY;
			} else {
				stepY = 1;
				sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
			}
			while (hit == 0) {
				if (sideDistX < sideDistY) {
					sideDistX += deltaDistX;
					mapX += stepX;
					side = 0;
				} else {
					sideDistY += deltaDistY;
					mapY += stepY;
					side = 1;
				}

				if (logique.getMap().inWall(mapX, mapY)) {
					hit = 1;
				}
			}
			if (side == 0) {
				perpWallDist = (mapX - rayPosX + (1 - stepX) / 2) / rayDirX;
			} else {
				perpWallDist = (mapY - rayPosY + (1 - stepY) / 2) / rayDirY;
			}
			int lineHeight = (int) (h / perpWallDist);
			tabDistStripes[x] = perpWallDist;

			if (side == 1) {
				g2d.setColor(Color.LIGHT_GRAY);
			} else {
				g2d.setColor(Color.GRAY);
			}
			int middle = h / 2;
			g2d.drawLine(x, middle - lineHeight / 2, x, middle + lineHeight / 2);
		}
	}

	// On part ici du principe qu'elles sont ordonnées
	private void renderThings() {
		// Ici on les trie
		for (Thing element : listThings) {
			Vector2D deltaPos = element.getPosition().sub(posCamera.getPosition());

			// On veut les trier dans l'ordre décroissant, on les ajoute donc
			// dans un TreeMap selon
			// leur distance à la caméra. On veut que les plus loin soit les
			// derniers, d'où le -length()
			listAfficher.put(-deltaPos.length(), element);
		}

		Set<Double> keys = listAfficher.keySet();
		Iterator<Double> i = keys.iterator();
		while (i.hasNext()) {
			Thing current = listAfficher.get(i.next());

			Vector2D posThing = current.getPosition();
			Vector2D deltaPos = posThing.sub(posCamera.getPosition());
			// Ici on fait un tricks mathématique magique
			double planeX = plane.getdX();
			double planeY = plane.getdY();
			double dirX = posCamera.getDirection().getdX();
			double dirY = posCamera.getDirection().getdY();
			double l = planeX * dirY - dirX * planeY;
			double[][] matrix = { { dirY / l, -dirX / l }, { -planeY / l, planeX / l } };
			Vector2D projected = deltaPos.multiplyByMatrice(matrix);
			double transformX = projected.getdX();
			double transformY = projected.getdY();

			// BufferedImage currentSprite = MagasinImage.buffYoanBlanc;
			// BufferedImage currentSprite = MagasinImage.getNextSprite();
			BufferedImage currentSprite = current.getSprite();

			int imageWidth = currentSprite.getWidth();
			int imageHeight = currentSprite.getHeight();

			int h = this.getHeight();
			int w = this.getWidth();
			int spriteScreenX = (int) (w / 2 * (1 + transformX / transformY));

			// Calculer la hauteur du sprite en cours de dessin
			int spriteHeight = Math.abs((int) (h / transformY));
			int drawStartY = -spriteHeight / 2 + h / 2;
			if (drawStartY < 0) {
				drawStartY = 0;
			}
			int drawEndY = spriteHeight / 2 + h / 2;
			if (drawEndY >= h) {
				drawEndY = h - 1;
			}

			// calculer la largeur du sprite.
			int spriteWidth = Math.abs((int) (w / transformY));
			int drawStartX = -spriteWidth / 2 + spriteScreenX;
			if (drawStartX < 0) {
				drawStartX = 0;
			}
			int drawEndX = spriteWidth / 2 + spriteScreenX;
			if (drawEndX >= w) {
				drawEndX = w - 1;
			}

			// Pour chaque tranche de notre image, on vérifie si il n'y a pas un
			// mur plus proche
			for (int j = drawStartX; j < drawEndX; j++) {
				int texX = (256 * (j - (-spriteWidth / 2 + spriteScreenX)) * imageWidth / spriteWidth) / 256;
				if (transformY > 0 && j > 0 && j < w && transformY < tabDistStripes[j]) {
					for (int y = drawStartY; y < drawEndY; y++) {
						int d = (y) * 256 - h * 128 + spriteHeight * 128; // 256
																			// and
																			// 128
																			// factors
																			// to
																			// avoid
																			// floats
						int texY = ((d * imageHeight) / spriteHeight) / 256;

						// texY passe dans les négatifs (min atteint = -4)
						if (texY < 0) {
							texY = 0;
						}

						// System.out.println(currentSprite.getRGB(0, 0));
						// pixel invisible: 16777215
						int rgb = currentSprite.getRGB(texX, texY);
						if (rgb != 16777215) {
							bufferThings.setRGB(j, y, rgb);
						}
					}
				}
			}
		}
		// A la fin de l'affichage, on clear la liste
		listAfficher.clear();
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools

	private ArrayList<Thing> listThings;
	private TreeMap<Double, Thing> listAfficher;

	private BufferedImage bufferThings;
	private Rectangle2D.Double clearRect;
	private Graphics2D g2dBuff;

	public Thing posCamera;
	private double[] tabDistStripes;

	public Vector2D plane;

	// Juste pour le test, à enlever plus tard
	private Vector2D oldPos;
	private Vector2D oldDir;
	private Vector2D oldPlane;
}
