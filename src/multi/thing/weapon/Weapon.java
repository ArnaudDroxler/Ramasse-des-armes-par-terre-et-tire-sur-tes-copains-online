package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.thing.Thing;
import multi.tools.raycasting.Vector2D;

public abstract class Weapon extends Thing {

	protected double DpS;
	protected double RoF;
	protected int ammo;

	public abstract int computeDamage(double d);

	public abstract BufferedImage getSpriteHUD();

	public void sumAmmo(int a) {
		ammo = +a;
	}

	public void sousAmmo(int a) {
		ammo -= a;
	}

	public Weapon(Vector2D pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}

}
