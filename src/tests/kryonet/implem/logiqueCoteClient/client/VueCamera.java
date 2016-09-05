package tests.kryonet.implem.logiqueCoteClient.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
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
import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.logiqueCoteClient.server.JoueurOnline;

public class VueCamera extends Renderer {

	protected Vector2D pos,dir,plane;
	protected int frameW, frameH;

	public static int customW = 1280/4;
	public static int customH = 720/4;
	
	protected double[] tabDistStripes;
	protected BufferedImage buffImgThings;
	protected java.awt.geom.Rectangle2D.Double clearRect;
	protected Graphics2D g2dThings;
	protected Graphics2D g2dMurs;
	protected BufferedImage buffImgMurs;
	protected Graphics2D g2d;
	private boolean readyToDraw;
	protected BufferedImage currentSprite;
	
	private Drawer drawer2;
	
	protected TreeMap<Double, Thing> chosesAAfficher;

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
		frameW = getWidth();
		frameH = getHeight();
		
		int w = customW;
		int h = customH;

		clearRect = new Rectangle2D.Double(0, 0, w, h);

		tabDistStripes = new double[w];
		buffImgMurs = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g2dMurs = buffImgMurs.createGraphics();

		buffImgThings = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g2dThings = buffImgThings.createGraphics();

		
		drawer2 = new Drawer(this, lc);
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
		drawer2.drawWalls();
	}
	
	private void drawThings() {

		drawer2.drawThings();
		
//		// Ici ça permet de clear rapidement une bufferedImage
//		AlphaComposite alphacomp = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
//		g2dThings.setComposite(alphacomp);
//		g2dThings.fill(clearRect);
//
//		// TODO : ajouter les objets aussi dans la liste de choses a afficher
//		for (JoueurOnline j : lc.joueurs.values()) {
//			
//			Vector2D deltaPos = j.getPosition().sub(pos);
//
//			// On veut les trier dans l'ordre décroissant, on les ajoute donc
//			// dans un TreeMap selon
//			// leur distance à la caméra. On veut que les plus loin soit les
//			// derniers, d'où le -length()
//			
//			if(j.id != lc.joueur.id && deltaPos.length()>0.75)
//				chosesAAfficher.put(-deltaPos.length(), j);
//		}
//		
//		Set<Double> keys = chosesAAfficher.keySet();
//		Iterator<Double> i = keys.iterator();
//		Thing current;
//
//		int w=buffImgThings.getWidth();
//		int h=buffImgThings.getHeight();
//		
//		while (i.hasNext()) {
//			current = chosesAAfficher.get(i.next());
//
//			Vector2D deltaPos = current.getPosition().sub(pos);
//			// Ici on fait un tricks mathématique magique
//			double l = plane.getdX() * dir.getdY() -  dir.getdX() * plane.getdY();
//			double[][] matrix = { { dir.getdY() / l, -dir.getdX() / l }, { -plane.getdY() / l, plane.getdX() / l } };
//			Vector2D projected = deltaPos.multiplyByMatrice(matrix);
//			double transformX = projected.getdX();
//			double transformY = projected.getdY();
//
//			if (current instanceof JoueurOnline) {
//				// Calcul de l'angle
//				// v2 est le vecteur inverse à la direction de l'ennemi
//				Vector2D v2 = current.getDirection().mult(-1);
//				// v1 est le vecteur qui relie la camera à l'ennemi
//				double dx = current.getPosition().getdX() - pos.getdX();
//				double dy = current.getPosition().getdY() - pos.getdY();
//				Vector2D v1 = new Vector2D(dx, dy);
//				// l'angle dirigé de v2 et v1, merci à
//				// http://stackoverflow.com/questions/21483999/using-atan2-to-find-angle-between-two-vectors
//				double angle = Math.atan2(v2.getdY(), v2.getdX()) - Math.atan2(v1.getdY(), v1.getdX());
//				if (angle < 0)
//					angle += 2 * Math.PI;
//				// le nombre d'angles de vue possibles pour cet ennemi
//				int nbSecteurs = current.getNbSecteurs();
//				// l'angle de vue choisi selon l'angle
//				int secteur = (int) (((nbSecteurs * angle / (2 * Math.PI)) + 0.5) % nbSecteurs);
//
//				currentSprite = current.getSprite(secteur);
//			} else {
//				currentSprite = current.getSprite();
//			}
//
//			int imageWidth = currentSprite.getWidth();
//			int imageHeight = currentSprite.getHeight();
//
//			int spriteScreenX = (int) (w / 2 * (1 + transformX / transformY));
//
//			// Calculer la hauteur du sprite en cours de dessin
//			int spriteHeight = Math.abs((int) (h / transformY));
//			int drawStartY = -spriteHeight / 2 + h / 2;
//			if (drawStartY < 0) {
//				drawStartY = 0;
//			}
//			int drawEndY = spriteHeight / 2 + h / 2;
//			if (drawEndY >= h) {
//				drawEndY = h - 1;
//			}
//
//			// calculer la largeur du sprite.
//			int spriteWidth = Math.abs((int) (w / transformY));
//			int drawStartX = -spriteWidth / 2 + spriteScreenX;
//			if (drawStartX < 0) {
//				drawStartX = 0;
//			}
//			int drawEndX = spriteWidth / 2 + spriteScreenX;
//			if (drawEndX >= w) {
//				drawEndX = w - 1;
//			}
//
//			// Pour chaque tranche de notre image, on vérifie si il n'y a pas un
//			// mur plus proche
//			for (int j = drawStartX; j < drawEndX; j++) {
//				int texX = (256 * (j - (-spriteWidth / 2 + spriteScreenX)) * imageWidth / spriteWidth) / 256;
//				if (transformY > 0 && j > 0 && j < w && transformY < tabDistStripes[j]) {
//					for (int y = drawStartY; y < drawEndY; y++) {
//						// 256 and 128 factors to avoid floats
//						int d = (y) * 256 - h * 128 + spriteHeight * 128;
//						int texY = ((d * imageHeight) / spriteHeight) / 256;
//
//						// texY passe dans les négatifs (min atteint = -4)
//						if (texY < 0) {
//							texY = 0;
//						}
//						if (texX < 0) {
//							texX = 0;
//						}
//
//						// pixel invisible: 16777215
//						int rgb = currentSprite.getRGB(texX, texY);
//						if (rgb != 16777215) {
//							buffImgThings.setRGB(j, y, rgb);
//						}
//					}
//				}
//			}
//		}
//		// A la fin de l'affichage, on clear la liste
//		chosesAAfficher.clear();
//		
//		g2d.drawImage(buffImgThings,0,0,frameW,frameH,null);		
	}

}
