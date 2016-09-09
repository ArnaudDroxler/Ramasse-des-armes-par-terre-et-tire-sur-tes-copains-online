package rattco.tools.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import rattco.thing.Thing;
import rattco.tools.raycasting.Vector2D;

/**
 * Décrit un objet Map, est héritée par ImageLvlMap, au début on pensais qu'il
 * pourrait y avoir plusieurs types de map (comme XmlLvlMap) mais finalement y a que ImageLvlMap
 */
public abstract class LvlMap {

	protected int width;
	protected int height;
	private BufferedImage mapBackground;
	private ArrayList<Thing> listThing;
	private int[][] textureTab;

	private ArrayList<Vector2D> listStartPosition;

	public abstract boolean inWall(Vector2D pos);

	public abstract boolean inWall(double newx, double newy);

	public LvlMap(int i, int j) {
		width = i;
		height = j;
		listThing = new ArrayList<Thing>();
		listStartPosition = new ArrayList<Vector2D>();
		textureTab = new int[width][height];
	}

	public BufferedImage getMapBackground() {
		return mapBackground;
	}

	public void setMapBackground(BufferedImage mapBackground) {
		this.mapBackground = mapBackground;
	}

	public Vector2D getStartPosition() {

		int posTab = (int) (Math.random() * listStartPosition.size());
		return listStartPosition.get(posTab);

		// return startPosition;
	}

	public ArrayList<Thing> getListThing() {
		return listThing;
	}

	public void setListThing(ArrayList<Thing> listThing) {
		this.listThing = listThing;
	}

	public void setListStartPosition(ArrayList<Vector2D> listPos) {
		listStartPosition = listPos;
	}

	public ArrayList<Vector2D> getListStartPosition() {
		return listStartPosition;
	}

	public int getTextureTab(int mapX, int mapY) {
		if(mapX<0 || mapX>=textureTab.length){
			mapX=0;
		}
		if(mapY<0 || mapY>=textureTab[mapX].length){
			mapY=0;
		}
		return textureTab[mapX][mapY];
	}

	public void setTextureTab(int x, int y, int numeroTexture) {
		textureTab[x][y] = numeroTexture;
	}


}
