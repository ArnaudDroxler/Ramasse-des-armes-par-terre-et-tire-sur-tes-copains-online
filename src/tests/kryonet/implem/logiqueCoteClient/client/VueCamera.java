package tests.kryonet.implem.logiqueCoteClient.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import multi.thing.Thing;
import multi.thing.personnage.Ennemi;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.logiqueCoteClient.server.JoueurOnline;

public class VueCamera extends Renderer {

	private Vector2D pos, dir, plane;
	private int h, w;
	public static int customW = 1280;
	public static int customH = 720;
	public static int InitialcustomHeight = 288;
	public static int InitialcustomWidth = 512;
	private final double scaleWidth = (double) customW / InitialcustomWidth;
	private final double scaleHeight = (double) customH / InitialcustomHeight;
	
	private int texWidth = 256;
	private int texHeight = 256;
	
	protected double[] tabDistStripes;
	protected BufferedImage buffImgThings;
	protected java.awt.geom.Rectangle2D.Double clearRect;
	protected Graphics2D g2dThings;
	private Graphics2D g2dMurs;
	private BufferedImage buffImgMurs;
	private Graphics2D g2d;
	private boolean readyToDraw;
	private BufferedImage currentSprite;
	
	private TreeMap<Double, Thing> chosesAAfficher;

	public VueCamera(LogiqueClient _logique) {
		super(_logique);

		pos = lc.joueur.getPosition();
		plane = new Vector2D(0, 0);
		setDirection(lc.joueur.getDirection());
		chosesAAfficher = new TreeMap<Double, Thing>();
		
		control();
		setBackground(Color.decode("#111111"));
		
		readyToDraw=false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		if(readyToDraw){
			draw();
		}
		
	}

	private void draw() {		
		setPosition(lc.joueur.getPosition());
		setDirection(lc.joueur.getDirection());

		drawMurs();
		drawThings();
	}

	private void control() {
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				init();
			}

		});
	}

	private void init() {
		
		h = customH;
		w = customW;
		
		clearRect = new Rectangle2D.Double(0, 0, w, h);

		tabDistStripes = new double[w];
		buffImgMurs = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g2dMurs = buffImgMurs.createGraphics();

		buffImgThings = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g2dThings = buffImgThings.createGraphics();
		
		readyToDraw=true;
	}
	
	public void setPosition(Vector2D position) {
		pos = position;
	}

	public void setDirection(Vector2D direction) {
		dir = direction;
		Vector2D vec = direction.rotate(Math.PI / 2.0);
		plane.setdX(vec.getdX());
		plane.setdY(vec.getdY());
	}
	
	private void drawMurs() {
			
		for (int x = 0;x < w ; x++) {
			double cameraX = 2 * x / (double) w - 1;
			double rayPosX = pos.getdX();
			double rayPosY = pos.getdY();
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
			
			int numeroTexture= lc.map.getTextureTab(mapX,mapY);

			for (int y = drawStart; y < drawEnd; y++) {
				int d = y * 256 - h * 128 + lineHeight * 128;
				int texY = ((d * texHeight) / lineHeight) / 256;
				if (numeroTexture > MagasinImage.buffTextMur.length)
					numeroTexture = 0;
				Color c = new Color(MagasinImage.buffTextMur[numeroTexture].getRGB(texX, texY));
				if (side == 1)
					c = c.darker();
				buffImgMurs.setRGB(x, y, c.getRGB());

			}

			double floorXWall;
			double floorYWall; // x, y position of the floor texel at the bottom
								// of the wall

			// 4 different wall directions possible
			if (side == 0 && rayDirX > 0) {
				floorXWall = mapX;
				floorYWall = mapY + wallX;
			} else if (side == 0 && rayDirX < 0) {
				floorXWall = mapX + 1.0;
				floorYWall = mapY + wallX;
			} else if (side == 1 && rayDirY > 0) {
				floorXWall = mapX + wallX;
				floorYWall = mapY;
			} else {
				floorXWall = mapX + wallX;
				floorYWall = mapY + 1.0;
			}

			double distWall, distPlayer, currentDist;

			distWall = perpWallDist;
			distPlayer = 0.0;

			if (drawEnd < 0)
				drawEnd = h; // becomes < 0 when the integer overflows

			// draw the floor from drawEnd to the bottom of the screen
			for (int y = drawEnd + 1; y < h; y++) {
				currentDist = h / (2.0 * y - h); // you could make a small
													// lookup table for this
													// instead

				double weight = (currentDist - distPlayer) / (distWall - distPlayer);

				double currentFloorX = weight * floorXWall + (1.0 - weight) * pos.getdX();
				double currentFloorY = weight * floorYWall + (1.0 - weight) * pos.getdY();

				int floorTexX = (int) (currentFloorX * texWidth) % texWidth;
				int floorTexY = (int) (currentFloorY * texHeight) % texHeight;

				int floorTexture = lc.map.getTextureTab((int) currentFloorX,(int) currentFloorY);
				
				Color c = new Color(MagasinImage.buffTextMur[floorTexture].getRGB(floorTexX, floorTexY));
				c = c.darker();
				buffImgMurs.setRGB(x, y, c.getRGB());

				c = new Color(MagasinImage.buffTextMur[floorTexture].getRGB(floorTexY, floorTexX));
				buffImgMurs.setRGB(x, h - y, c.getRGB());

			}
		}
		
		g2d.drawImage(buffImgMurs,0,0,w,h,null);
		
	}
	
	private void drawThings() {
		// Ici ça permet de clear rapidement une bufferedImage
		AlphaComposite alphacomp = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
		g2dThings.setComposite(alphacomp);
		g2dThings.fill(clearRect);

		// TODO : ajouter les objets aussi dans la liste de choses a afficher
		for (JoueurOnline j : lc.joueurs.values()) {
			
			Vector2D deltaPos = j.getPosition().sub(pos);

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
		
		while (i.hasNext()) {
			current = chosesAAfficher.get(i.next());

			Vector2D deltaPos = current.getPosition().sub(pos);
		
			double l = plane.getdX() * dir.getdY() -  dir.getdX() * plane.getdY();
			double[][] matrix = { { dir.getdY() / l, -dir.getdX() / l }, { -plane.getdY() / l, plane.getdX() / l } };
			Vector2D projected = deltaPos.multiplyByMatrice(matrix);
			double transformX = projected.getdX();
			double transformY = projected.getdY();

			if (current instanceof JoueurOnline) {
				Vector2D v2 = current.getDirection().mult(-1);
				double dx = current.getPosition().getdX() - pos.getdX();
				double dy = current.getPosition().getdY() - pos.getdY();
				Vector2D v1 = new Vector2D(dx, dy);
				double angle = Math.atan2(v2.getdY(), v2.getdX()) - Math.atan2(v1.getdY(), v1.getdX());
				if (angle < 0)
					angle += 2 * Math.PI;
				int nbSecteurs = current.getNbSecteurs();
				int secteur = (int) (((nbSecteurs * angle / (2 * Math.PI)) + 0.5) % nbSecteurs);

				currentSprite = current.getSprite(secteur);
			} else {
				currentSprite = current.getSprite();
			}

			int imageWidth = currentSprite.getWidth();
			int imageHeight = currentSprite.getHeight();

			int spriteScreenX = (int) (w / 2 * (1 + transformX / transformY));
			
			int spriteHeight = Math.abs((int) (h / transformY));
			int drawStartY = -spriteHeight / 2 + h / 2;
			if (drawStartY < 0) {
				drawStartY = 0;
			}
			int drawEndY = spriteHeight / 2 + h / 2;
			if (drawEndY >= h) {
				drawEndY = h - 1;
			}

			int spriteWidth = Math.abs((int) (w / transformY));
			int drawStartX = -spriteWidth / 2 + spriteScreenX;
			if (drawStartX < 0) {
				drawStartX = 0;
			}
			int drawEndX = spriteWidth / 2 + spriteScreenX;
			if (drawEndX >= w) {
				drawEndX = w - 1;
			}
			
			for (int j = drawStartX; j < drawEndX; j++) {
				int texX = (256 * (j - (-spriteWidth / 2 + spriteScreenX)) * imageWidth / spriteWidth) / 256;
				if (transformY > 0 && j > 0 && j < w && transformY < tabDistStripes[j]) {
					for (int y = drawStartY; y < drawEndY; y++) {
						int d = (y) * 256 - h * 128 + spriteHeight * 128;
						int texY = ((d * imageHeight) / spriteHeight) / 256;

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
		chosesAAfficher.clear();
		g2d.drawImage(buffImgThings,0,0,w,h,null);		
	}

}
