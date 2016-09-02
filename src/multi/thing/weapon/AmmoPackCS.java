
package multi.thing.weapon;

import java.awt.image.BufferedImage;


import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class AmmoPackCS extends AmmoPack {
	
	protected static int ammo;

	public static int getAmmo() {
		return ammo;
	}
	
	public AmmoPackCS(Vector2D pos) {
		super(pos);
		ammo = 200;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffAmmoPackCS;
	}

}
