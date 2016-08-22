package integration.beta.tools.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import integration.beta.thing.Key;
import integration.beta.thing.Monstre;
import integration.beta.thing.Thing;
import integration.beta.tools.raycasting.Vector2D;

public abstract class LvlMap {

	protected int width;
	protected int height;
	private BufferedImage mapBackground;
	private ArrayList<Thing> listThing;
	private ArrayList<Monstre> listMonstre;
	private Vector2D startPosition;
	protected Vector2D startDirection;
	private Vector2D goalPosition;
	private Vector2D keyPosition;
	private Key key;

	public abstract boolean inWall(Vector2D pos);
	public abstract boolean inWall(double newx, double newy);
	
	public LvlMap() {
		listThing = new ArrayList<Thing>();
		listMonstre = new ArrayList<Monstre>();
	}
	public BufferedImage getMapBackground() {
		return mapBackground;
	}
	public void setMapBackground(BufferedImage mapBackground) {
		this.mapBackground = mapBackground;
	}
	
	public Vector2D getStartPosition() {
		return startPosition;
	}

	public ArrayList<Thing> getListThing() {
		return listThing;
	}
	public ArrayList<Monstre> getListMonstre() {
		return listMonstre;
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
		key=cle;
	}
	public Key getKey(){
		return key;
	}
	
}
