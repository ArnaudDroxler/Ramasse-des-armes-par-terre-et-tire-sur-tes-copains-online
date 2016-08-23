package multi.thing.weapon;

import java.awt.image.BufferedImage;

import base.thing.Thing;
import base.tools.raycasting.Vector2D;

public abstract class Weapon extends Thing{
	
	protected double DpS;
	protected double RoF;
	protected int ammo;
	
	public abstract int computeDamage();
	public abstract BufferedImage getSpriteHUD();
	
	public Weapon() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
