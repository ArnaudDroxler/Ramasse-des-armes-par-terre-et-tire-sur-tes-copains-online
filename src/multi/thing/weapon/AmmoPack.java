package multi.thing.weapon;

import multi.thing.Thing;

import multi.tools.raycasting.Vector2D;

public abstract class AmmoPack extends Thing {

	protected static int ammo;

	public static int getAmmo() {
		return ammo;
	}

	public AmmoPack(Vector2D pos) {
		super(pos);
	}

}
