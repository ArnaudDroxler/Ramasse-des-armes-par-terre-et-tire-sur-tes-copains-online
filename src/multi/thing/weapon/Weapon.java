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

	public double getDpS() {
		return DpS;
	}

	public double getRoF() {
		return RoF;
	}

	public int getAmmo() {
		return ammo;
	}

	public void sumAmmo(int a) {
		ammo += a;
		System.out.println("Ammo : " + ammo);
	}

	public void subAmmo(int a) {
		ammo -= a;
		System.out.println("Ammo : " + ammo);
	}

	public Weapon(Vector2D pos) {
		super(pos);
	}

}
