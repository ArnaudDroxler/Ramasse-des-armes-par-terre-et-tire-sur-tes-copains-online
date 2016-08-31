package tests.kryonet.implem.logiqueCoteClient.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;

import multi.thing.Thing;
import multi.thing.personnage.Joueur;
import multi.tools.ImageLoader;
import multi.tools.raycasting.Vector2D;
import sun.java2d.pipe.DrawImage;

public class VueCamera extends Renderer {

	private Vector2D pos;
	private Vector2D dir;
	private Vector2D plane;
	private int w,h;
	protected double[] tabDistStripes;
	protected BufferedImage buffImgThings;
	protected Double clearRect;
	protected Graphics2D g2dThings;
	private Graphics2D g2dMurs;
	private BufferedImage buffImgMurs;
	private Graphics2D g2d;
	private boolean readyToDraw;

	public VueCamera(LogiqueClient _logique) {
		super(_logique);

		pos = lc.joueur.getPosition();
		plane = new Vector2D(0, 0);
		setDirection(lc.joueur.getDirection());
		
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
		g2d.drawImage(buffImgMurs,0,0,w,h,null);
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
		h = getHeight();
		w = getWidth();
		
		tabDistStripes = new double[w];
		
		clearRect = new Rectangle2D.Double(0, 0, w, h);

		buffImgThings = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g2dThings = buffImgThings.createGraphics();

		buffImgMurs = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g2dMurs = buffImgMurs.createGraphics();
		
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
		
		g2dMurs.setColor(Color.black);
		g2dMurs.fill(clearRect);

		int w=buffImgMurs.getWidth();
		int h=buffImgMurs.getHeight();
		
		for (int x = 0; x < w; x++) {
			double cameraX = 2 * x / (double) w - 1;
			double rayPosX = pos.getdX();
			double rayPosY = pos.getdY();
			double rayDirX = dir.getdX() + plane.getdX() * cameraX;
			double rayDirY = dir.getdY() + plane.getdY() * cameraX;

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
			tabDistStripes[x] = perpWallDist;

			if (side == 1) {
				//g2d.setColor(Color.LIGHT_GRAY);
				g2dMurs.setColor(Color.LIGHT_GRAY);
			} else {
				//g2d.setColor(Color.GRAY);
				g2dMurs.setColor(Color.GRAY);
			}
			int middle = h / 2;

			// le -15 permet d'augmenter la hauteur des murs.
			//g2d.drawLine(x, (middle - lineHeight / 2 -15), x, (middle + lineHeight / 2));
			g2dMurs.drawLine(x, (middle - lineHeight / 2 -15), x, (middle + lineHeight / 2));
			
		}
		//g2d.drawImage(buffImgMurs, null, 0, 0);
		
	}
	
	private void drawThings() {
		// Ici ça permet de clear rapidement une bufferedImage
		AlphaComposite alphacomp = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
		g2dThings.setComposite(alphacomp);
		g2dThings.fill(clearRect);
	}

}
