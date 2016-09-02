
package multi;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import multi.thing.Thing;
import multi.thing.personnage.Ennemi;
import multi.thing.personnage.Joueur;
import multi.thing.weapon.PrecisionRifle;
import multi.thing.weapon.ShootGun;
import multi.thing.weapon.Chainsaw;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Camera extends Renderer {

	private static final long serialVersionUID = -1160718986443478214L;

	private AffineTransform oldContext;

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

		posCamera = new Joueur(logique.getMap().getStartPosition(), new Vector2D(1, 0));

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
		oldContext = g2d.getTransform();
		draw(g2d);
		g2d.setTransform(oldContext);
	}

	public void resetCam() {
		posCamera.setPosition(oldPos);
		posCamera.setDirection(oldDir);
		plane.setdX(oldPlane.getdX());
		plane.setdY(oldPlane.getdY());
	}

	public void zoom() {

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
				if (customSize) {
					h = customHeight;
					w = customWidth;

				} else {
					h = getHeight();
					w = getWidth();
				}
				tabDistStripes = new double[w];

				/*
				 * GraphicsEnvironment ge =
				 * GraphicsEnvironment.getLocalGraphicsEnvironment();
				 * GraphicsConfiguration gc =
				 * ge.getDefaultScreenDevice().getDefaultConfiguration();
				 * bufferThings = gc.createCompatibleImage(w, h,
				 * Transparency.TRANSLUCENT);
				 * bufferThings.setAccelerationPriority(1);
				 * 
				 * System.out.println(bufferThings.getCapabilities(gc).
				 * isAccelerated());
				 */
				bufferThings = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

				clearRect = new Rectangle2D.Double(0, 0, w, h);
				g2dBuff = bufferThings.createGraphics();
			}

		});
	}

	private void appearance() {
		setBackground(Color.black);
	}

	private void draw(Graphics2D g2d) {
		if (g2dBuff != null) {
			if (customSize) {
				double sx = getWidth() / (double) customWidth;
				double sy = getHeight() / (double) customHeight;
				g2d.scale(sx, sy);
			}
			// Ici ça permet de clear rapidement une bufferedImage
			AlphaComposite alphacomp = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
			g2dBuff.setComposite(alphacomp);
			g2dBuff.fill(clearRect);

			setPosition(logique.heros.getPosition());
			setDirection(logique.heros.getDirection());

			// g2d.setBackground(Color.BLACK);

			g2d.translate(w / 2, h / 2);
			try {
				if (FenetreJeu.mouseRightPressed && logique.heros.getArme() instanceof PrecisionRifle) {
					g2d.scale(2, 2);
				}
				algoRaycasting(g2d);
			} catch (Exception e) {
				System.out.println("coucou y a probleme");
				e.printStackTrace();
			}

			renderThings();

			g2d.drawImage(bufferThings, null, -w / 2, -h / 2);

			drawWeapon(g2d);

			drawHUD(g2d);
			if (logique.isFiring && !(logique.heros.getArme() instanceof Chainsaw) && logique.toucheMur) {
				// BufferedImage img =
				// logique.heros.getArme().getSpriteImpactMur();

				for (int i = 0; i < logique.fireLineList.size(); i++) {
					double longueurligne = Math.sqrt(Math
							.pow((logique.fireLineList.get(i).getX2() - logique.fireLineList.get(i).getX1()), 2)
							+ Math.pow((logique.fireLineList.get(i).getY2() - logique.fireLineList.get(i).getY1()), 2));
					if (logique.heros.getArme().computeDamage(longueurligne) > 0) {
						BufferedImage img = scale(logique.heros.getArme().getSpriteImpactMur(),
								scaleWidth / (longueurligne / 4), scaleHeight / (longueurligne / 4));

						if (logique.heros.getArme() instanceof ShootGun) {

							// Alpha = 10°
							double alpha = Math.PI / 18;

							for (int j = -2; j < 3; j++) {
								// g2d.drawImage(img, null, 0, 0);
								g2d.drawImage(img, null, (int) (w / 2 + Math.sin(alpha * j) * h / 2) - w / 2, 0);
							}

						} else {
							g2d.drawImage(img, null, 0, 0);
						}
					}
				}
			}

			if (logique.isFiring && !(logique.heros.getArme() instanceof Chainsaw) && logique.toucheEnnemi) {

				// BufferedImage img =
				// logique.heros.getArme().getSpriteImpactMur();

				for (int i = 0; i < logique.fireLineList.size(); i++) {
					double longueurligne = Math.sqrt(Math
							.pow((logique.fireLineList.get(i).getX2() - logique.fireLineList.get(i).getX1()), 2)
							+ Math.pow((logique.fireLineList.get(i).getY2() - logique.fireLineList.get(i).getY1()), 2));
					if (logique.heros.getArme().computeDamage(longueurligne) > 0) {

						BufferedImage img = scale(logique.heros.getArme().getSpriteImpactEnnemi(),
								scaleWidth / (longueurligne / 4), scaleHeight / (longueurligne / 4));

						if (logique.heros.getArme() instanceof ShootGun) {

							// Alpha = 10°
							double alpha = Math.PI / 18;

							for (int j = -2; j < 3; j++) {
								System.out.println(longueurligne);
								// g2d.drawImage(img, null, 0, 0);
								g2d.drawImage(img, null, (int) (w / 2 + Math.sin(alpha * j) * h / 2) - w / 2, 0);
							}

						} else {
							System.out.println(longueurligne);
							g2d.drawImage(img, null, 0, 0);
						}
					}
				}
			}

			if (logique.afficheScore) {

				String strScore = new String("Joueur");

				Font fontTitre = new Font("Helvetica", Font.BOLD, 20);
				int valTabX = w / 5;
				int valTabY = -h / 3;
				g2d.setFont(fontTitre);
				g2d.setColor(new Color(1f, 0f, 0f, .5f));
				int stringHei = (int) g2d.getFontMetrics().getStringBounds(strScore, g2d).getHeight();
				g2d.drawString(strScore, valTabX - w / 2, valTabY);
				strScore = "Kill";
				g2d.drawString(strScore, 3 * valTabX - w / 2, valTabY);
				strScore = "Death";
				g2d.drawString(strScore, 4 * valTabX - w / 2, valTabY);

				Font fontAff = new Font("Helvetica", Font.BOLD, 15);
				g2d.setFont(fontAff);
				// Trier la liste de joueur en fonction du score
				// int tailleTab = joueurOnline.size();
				int tailleTab = 3;

				// Pour chaque élément de la liste:
				for (int i = 0; i < tailleTab; i++) {
					// On récupère le pseudo
					strScore = logique.heros.getPseudo();
					g2d.drawString(strScore, valTabX - w / 2, stringHei * (i + 1) + valTabY);
					strScore = logique.heros.getNbKill() + "";
					g2d.drawString(strScore, 3 * valTabX - w / 2, stringHei * (i + 1) + valTabY);
					strScore = logique.heros.getNbDeath() + "";
					g2d.drawString(strScore, 4 * valTabX - w / 2, stringHei * (i + 1) + valTabY);
				}

			}
			if (logique.heros.getMort()) {
				String strMort = new String("Vous êtes mort!");
				Font font = new Font("Helvetica", Font.BOLD, 60);
				Rectangle2D rectangle = new Rectangle2D.Double(-w / 2, -h / 2, w, h);
				Color couleur = new Color(0f, 0f, 0f, .5f);
				g2d.setColor(couleur);
				g2d.draw(rectangle);
				g2d.fill(rectangle);
				// g2d.setFont(font);
				// g2d.setColor(new Color(1f, 0f, 0f, .5f));
				// // Pour centrage:
				// int stringLen = (int)
				// g2d.getFontMetrics().getStringBounds(strMort,
				// g2d).getWidth();
				// int stringHei = (int)
				// g2d.getFontMetrics().getStringBounds(strMort,
				// g2d).getHeight();
				// g2d.drawString(strMort, w / 2 - stringLen / 2, h / 2);
			}

			else if (logique.heros.prendDegats()) {
				Point2D center = new Point2D.Float(0, 0);
				float radius = w / 2;
				float[] dist = { 0.0f, 0.9f };

				Color trans = new Color(1f, 1f, 1f, 0f);
				Color rouge = new Color(1f, 0f, 0f, .5f);

				Color[] colors = { trans, rouge };
				RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);

				Rectangle2D rectangle = new Rectangle2D.Double(-w / 2, -h / 2, w, h);
				g2d.setPaint(gradient);
				g2d.fill(rectangle);
			}
		}

	}

	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n")) {
			drawtabString(g, line, x, y += g.getFontMetrics().getHeight());
		}
	}

	private void drawtabString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\t")) {
			g.drawString(line, x += g.getFontMetrics().getHeight(), y);
		}
	}

	private void drawHUD(Graphics2D g2d) {

		g2d.drawImage(scale(MagasinImage.buffHudLeft, scaleWidth, scaleHeight), null, -w / 2, h / 4);
		// g2d.drawImage(MagasinImage.buffHudLeft, null, -w/2,h/4);
		g2d.setColor(new Color(0, 97, 255));
		g2d.setFont(new Font("Arial", Font.PLAIN, (int) (30 * scaleHeight)));
		g2d.drawString("" + logique.heros.getArmure(), -w / 2 + w / 10, h / 4 + h / 9);
		g2d.setColor(Color.RED);
		g2d.setFont(new Font("Arial", Font.PLAIN, (int) (30 * scaleHeight)));
		g2d.drawString("" + logique.heros.getVie(), -w / 2 + w / 10, h / 4 + h / 5 + h / 35);

		String str;
		if (logique.heros.getArme() != null) {
			str = new String(logique.heros.getArme().getAmmo() + "/" + logique.heros.getArme().getMaxAmmo());
		} else {
			str = new String("0/0");
		}
		// g2d.drawImage(scale(MagasinImage.buffHudRight, scaleWidth,
		// scaleHeight), null, w/4,h/4);
		g2d.drawImage(scale(MagasinImage.buffHudRight, scaleWidth, scaleHeight), null, w / 4, h / 4);
		g2d.setColor(new Color(38, 38, 38));
		g2d.setFont(new Font("Arial", Font.PLAIN, (int) (25 * scaleHeight)));
		int strLen = (int) g2d.getFontMetrics().getStringBounds(str, g2d).getWidth();

		g2d.drawString(str, w / 4 + w / 6 - strLen, h / 4 + w / 10 + w / 50);

	}

	private void drawWeapon(Graphics2D g2d) {
		if (logique.heros.getArme() instanceof PrecisionRifle && FenetreJeu.mouseRightPressed) {

			Point2D center = new Point2D.Float(0, 0);
			float radius = w / 8;
			float[] dist = { 0.0f, 0.9f, 1.0f };

			Color trans = new Color(0f, 0f, 0f, 0f);

			Color[] colors = { trans, trans, Color.black };
			RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);

			Rectangle2D rectangle = new Rectangle2D.Double(-w / 2, -h / 2, w, h);
			g2d.setPaint(gradient);
			g2d.fill(rectangle);

			g2d.setColor(Color.black);
			g2d.drawLine(-w / 2, 0, w / 2, 0);
			g2d.drawLine(0, -h / 2, 0, h / 2);
		} else if (logique.heros.getArme() != null) {
			BufferedImage img = scale(logique.heros.getArme().getSpriteHUD(), scaleWidth, scaleHeight);
			g2d.drawImage(img, null, -img.getWidth() / 2, h / 2 - img.getHeight());
		}
	}

	public static BufferedImage scale(BufferedImage bi, double scaleWidth2, double scaleHeight2) {
		int width = (int) (bi.getWidth() * scaleWidth2);
		int height = (int) (bi.getHeight() * scaleHeight2);
		BufferedImage biNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = biNew.createGraphics();
		// graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
		// RenderingHints.VALUE_RENDER_QUALITY);
		// graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		// graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		graphics.drawImage(bi, 0, 0, width, height, null);
		graphics.dispose();
		return biNew;
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

		int texWidth = 256;
		int texHeight = 256;

		BufferedImage buff = new BufferedImage(w, h,  BufferedImage.TYPE_INT_ARGB);
		
		g2d.translate(-w / 2, -h / 2);
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
			int step = 0;
			while (hit == 0 && ++step < 1000) {
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

			int middle = h / 2;

			int drawStart = -lineHeight / 2 + h / 2;
			if (drawStart < 0)
				drawStart = 0;
			int drawEnd = lineHeight / 2 + h / 2;
			if (drawEnd >= h)
				drawEnd = h - 1;

			double wallX; // where exactly the wall was hit
			if (side == 0)
				wallX = rayPosY + perpWallDist * rayDirY;
			else
				wallX = rayPosX + perpWallDist * rayDirX;
			wallX -= Math.floor((wallX));

			int texX = (int) (wallX * texWidth);
			if (side == 0 && rayDirX > 0)
				texX = texWidth - texX - 1;
			if (side == 1 && rayDirY < 0)
				texX = texWidth - texX - 1;
			
			int text  = logique.map.getTextureTab()[mapX][mapY];
			
			for (int y = drawStart; y < drawEnd; y++) {
				int d = y * 256 - h * 128 + lineHeight * 128;
				int texY = ((d * texHeight) / lineHeight) / 256;

				Color c = new Color(MagasinImage.buffTextMur[text].getRGB(texX, texY));
				 if(side == 1) 
					 c = c.darker();
				 buff.setRGB(x, y, c.getRGB());

			}

		      double floorXWall; 
		      double floorYWall; //x, y position of the floor texel at the bottom of the wall

		      //4 different wall directions possible
		      if(side == 0 && rayDirX > 0)
		      {
		        floorXWall = mapX;
		        floorYWall = mapY + wallX;
		      }
		      else if(side == 0 && rayDirX < 0)
		      {
		        floorXWall = mapX + 1.0;
		        floorYWall = mapY + wallX;
		      }
		      else if(side == 1 && rayDirY > 0)
		      {
		        floorXWall = mapX + wallX;
		        floorYWall = mapY;
		      }
		      else
		      {
		        floorXWall = mapX + wallX;
		        floorYWall = mapY + 1.0;
		      }

		      double distWall, distPlayer, currentDist;

		      distWall = perpWallDist;
		      distPlayer = 0.0;

		      if (drawEnd < 0) drawEnd = h; //becomes < 0 when the integer overflows

		      //draw the floor from drawEnd to the bottom of the screen
		      for(int y = drawEnd + 1; y < h; y++)
		      {
		        currentDist = h / (2.0 * y - h); //you could make a small lookup table for this instead

		        double weight = (currentDist - distPlayer) / (distWall - distPlayer);

		        double currentFloorX = weight * floorXWall + (1.0 - weight) * pos.getdX();
		        double currentFloorY = weight * floorYWall + (1.0 - weight) * pos.getdY();

		        int floorTexX = (int) (currentFloorX * texWidth) % texWidth;
		        int floorTexY = (int) (currentFloorY * texHeight) % texHeight;
		        
		        int floorTexture =  logique.map.getTextureTab()[(int) currentFloorX][(int) currentFloorY];
		     
		        Color c = new Color(MagasinImage.buffTextMur[floorTexture].getRGB(floorTexX, floorTexY)); 
		        c = c.darker();
				 buff.setRGB(x, y, c.getRGB());
				 
				 
				c = new Color(MagasinImage.buffTextMur[floorTexture].getRGB(floorTexY, floorTexX));
			    buff.setRGB(x, h-y, c.getRGB());
		       
		      }
		    }

		g2d.drawImage(buff, null, 0, 0);

		g2d.translate(w / 2, h / 2);
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

			if (current instanceof Ennemi) {
				// Calcul de l'angle
				// v2 est le vecteur inverse à la direction de l'ennemi
				Vector2D v2 = current.getDirection().mult(-1);
				// v1 est le vecteur qui relie la camera à l'ennemi
				double dx = current.getPosition().getdX() - posCamera.getPosition().getdX();
				double dy = current.getPosition().getdY() - posCamera.getPosition().getdY();
				Vector2D v1 = new Vector2D(dx, dy);
				// l'angle dirigé de v2 et v1, merci à
				// http://stackoverflow.com/questions/21483999/using-atan2-to-find-angle-between-two-vectors
				double angle = Math.atan2(v2.getdY(), v2.getdX()) - Math.atan2(v1.getdY(), v1.getdX());
				if (angle < 0)
					angle += 2 * Math.PI;
				// le nombre d'angles de vue possibles pour cet ennemi
				int nbSecteurs = current.getNbSecteurs();
				// l'angle de vue choisi selon l'angle
				int secteur = (int) (((nbSecteurs * angle / (2 * Math.PI)) + 0.5) % nbSecteurs);

				currentSprite = current.getSprite(secteur);
			} else {
				currentSprite = current.getSprite();
			}

			int imageWidth = currentSprite.getWidth();
			int imageHeight = currentSprite.getHeight();

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
						// 256 and 128 factors to avoid floats
						int d = (y) * 256 - h * 128 + spriteHeight * 128;
						int texY = ((d * imageHeight) / spriteHeight) / 256;

						// texY passe dans les négatifs (min atteint = -4)
						if (texY < 0) {
							texY = 0;
						}
						if (texX < 0) {
							texX = 0;
						}

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
	private int h, w;

	public static int InitialcustomHeight = 288;
	public static int InitialcustomWidth = 512;

	// public int customHeight = 288;
	// public int customWidth = 512;

	public int customHeight = 360;
	public int customWidth = 640;

	// public int customHeight = 720;
	//public int customWidth = 1280;

	// public int customHeight = 1080;
	// public int customWidth = 1920;

	public final boolean customSize = true;

	private final double scaleWidth = (double) customWidth / InitialcustomWidth;
	private final double scaleHeight = (double) customHeight / InitialcustomHeight;

	private ArrayList<Thing> listThings;
	private TreeMap<Double, Thing> listAfficher;

	private BufferedImage bufferThings;
	private Rectangle2D.Double clearRect;
	private Graphics2D g2dBuff;

	public Thing posCamera;
	private double[] tabDistStripes;

	public Vector2D plane;

	private int dir;
	private BufferedImage currentSprite;

	// Juste pour le test, à enlever plus tard
	private Vector2D oldPos;
	private Vector2D oldDir;
	private Vector2D oldPlane;

	protected boolean zoom;

}
