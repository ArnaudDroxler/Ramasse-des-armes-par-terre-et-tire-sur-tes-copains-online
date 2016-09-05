package tests.kryonet.implem.logiqueCoteClient.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JComponent;

import multi.thing.Thing;
import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.logiqueCoteClient.server.JoueurOnline;

public class Drawer {

//	private Object clearRect;
//	private Object pos;
//	private Object dir;
//	private Object plane;
//	private double[] tabDistStripes;
//	private Object frameW;
//	private Object frameH;
	
	private BufferedImage buffImgMurs;
	private Graphics2D g2dMurs;
	private VueCamera cam;
	private Graphics2D g2dThings;
	private BufferedImage buffImgThings;
	private java.awt.geom.Rectangle2D.Double clearRect;
	private LogiqueClient lc;
	private TreeMap<Double, Thing> chosesAAfficher;
	protected BufferedImage currentSprite;
	private Graphics2D g2d;

	public Drawer(VueCamera cam, LogiqueClient lc) {
		g2dMurs=cam.g2dMurs;
		buffImgMurs=cam.buffImgMurs;
		g2dThings=cam.g2dThings;
		buffImgThings=cam.buffImgThings;
		clearRect=cam.clearRect;
		chosesAAfficher=cam.chosesAAfficher;
		g2d=cam.g2d;
		
		this.cam = cam;
		this.lc=lc;
	}

	public void drawWalls() {
		g2dMurs.setColor(Color.black);
		g2dMurs.fill(clearRect);

		int w=buffImgMurs.getWidth();
		int h=buffImgMurs.getHeight();
		
		for (int x = 0; x < w; x++) {
			double cameraX = 2 * x / (double) w - 1;
			double rayPosX = cam.pos.getdX();
			double rayPosY = cam.pos.getdY();
			double rayDirX = cam.dir.getdX() + cam.plane.getdX() * cameraX;
			double rayDirY = cam.dir.getdY() + cam.plane.getdY() * cameraX;

			int mapX = (int) rayPosX;
			int mapY = (int) rayPosY;

			double sideDistX, sideDistY, perpWallDist;

			double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
			double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));

			int stepX, stepY, hit = 0, side = 0;

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
				if (lc.map.inWall(mapX, mapY)) {
					hit = 1;
				}
			}
			if (side == 0) {
				perpWallDist = (mapX - rayPosX + (1 - stepX) / 2) / rayDirX;
			} else {
				perpWallDist = (mapY - rayPosY + (1 - stepY) / 2) / rayDirY;
			}
			int lineHeight = (int) (h / perpWallDist);
			cam.tabDistStripes[x] = perpWallDist;

			if (side == 1) {
				g2dMurs.setColor(Color.LIGHT_GRAY);
			} else {
				g2dMurs.setColor(Color.GRAY);
			}
			int middle = h / 2;
			
			g2dMurs.drawLine(x, middle - lineHeight, x, (middle + lineHeight / 2));
			
		}
		
		cam.g2d.drawImage(buffImgMurs,0,0,cam.frameW,cam.frameH,null);
	}
	
	
	
	
	
	
	

	public void drawThings() {
		
		// Ici ça permet de clear rapidement une bufferedImage
		AlphaComposite alphacomp = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
		g2dThings.setComposite(alphacomp);
		g2dThings.fill(clearRect);

		// TODO : ajouter les objets aussi dans la liste de choses a afficher
		for (JoueurOnline j : lc.joueurs.values()) {
			
			Vector2D deltaPos = j.getPosition().sub(cam.pos);

			// On veut les trier dans l'ordre décroissant, on les ajoute donc
			// dans un TreeMap selon
			// leur distance à la caméra. On veut que les plus loin soit les
			// derniers, d'où le -length()
			
			if(j.id != lc.joueur.id && deltaPos.length()>0.75)
				chosesAAfficher.put(-deltaPos.length(), j);
		}
		
		Set<Double> keys = chosesAAfficher.keySet();
		Iterator<Double> i = keys.iterator();
		Thing current;

		int w=buffImgThings.getWidth();
		int h=buffImgThings.getHeight();
		
		while (i.hasNext()) {
			current = chosesAAfficher.get(i.next());

			Vector2D deltaPos = current.getPosition().sub(cam.pos);
			// Ici on fait un tricks mathématique magique
			double l = cam.plane.getdX() * cam.dir.getdY() -  cam.dir.getdX() * cam.plane.getdY();
			double[][] matrix = { { cam.dir.getdY() / l, -cam.dir.getdX() / l }, { -cam.plane.getdY() / l, cam.plane.getdX() / l } };
			Vector2D projected = deltaPos.multiplyByMatrice(matrix);
			double transformX = projected.getdX();
			double transformY = projected.getdY();

			if (current instanceof JoueurOnline) {
				// Calcul de l'angle
				// v2 est le vecteur inverse à la direction de l'ennemi
				Vector2D v2 = current.getDirection().mult(-1);
				// v1 est le vecteur qui relie la camera à l'ennemi
				double dx = current.getPosition().getdX() - cam.pos.getdX();
				double dy = current.getPosition().getdY() - cam.pos.getdY();
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
				if (transformY > 0 && j > 0 && j < w && transformY < cam.tabDistStripes[j]) {
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
							buffImgThings.setRGB(j, y, rgb);
						}
					}
				}
			}
		}
		// A la fin de l'affichage, on clear la liste
		chosesAAfficher.clear();
		
		cam.g2d.drawImage(buffImgThings,0,0,cam.frameW,cam.frameH,null);	
	}

}
