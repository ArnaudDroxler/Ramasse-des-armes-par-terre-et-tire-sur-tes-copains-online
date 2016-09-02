package multi.tools.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import multi.thing.Key;
import multi.thing.Monstre;
import multi.thing.Thing;
import multi.thing.personnage.Ennemi;
import multi.tools.raycasting.Vector2D;

public abstract class LvlMap {

	protected int width;
	protected int height;
	private BufferedImage mapBackground;
	private ArrayList<Thing> listThing;
	private ArrayList<Ennemi> listEnnemie;
	private Vector2D startPosition;
	protected Vector2D startDirection;
	private Vector2D goalPosition;
	private Vector2D keyPosition;
	private Key key;
	private int[][] textureTab;

	private ArrayList<Vector2D> listStartPosition;

	public abstract boolean inWall(Vector2D pos);

	public abstract boolean inWall(double newx, double newy);

	public LvlMap(int i, int j) {
		width = i;
		height = j;
		listThing = new ArrayList<Thing>();
		listEnnemie = new ArrayList<Ennemi>();
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

	public ArrayList<Ennemi> getListEnnemie() {
		return listEnnemie;
	}

	public void setListThing(ArrayList<Thing> listThing) {
		this.listThing = listThing;
	}

	public Vector2D getGoalPosition() {
		return goalPosition;
	}

	public void setGoalPosition(Vector2D goalPosition) {
		this.goalPosition = goalPosition;
	}

	public void setStartPosition(Vector2D startPosition) {
		this.startPosition = startPosition;
	}

	public void setKeyPosition(Vector2D pos) {
		this.keyPosition = pos;
	}

	public Vector2D getKeyPosition() {
		return keyPosition;
	}

	public void setKey(Key cle) {
		key = cle;
	}

	public Key getKey() {
		return key;
	}

	public void setListStartPosition(ArrayList<Vector2D> listPos) {
		listStartPosition = listPos;
	}

	public ArrayList<Vector2D> getListStartPosition() {
		return listStartPosition;
	}

	public int[][] getTextureTab() {
		return textureTab;
	}


}
