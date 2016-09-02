
package multi.thing.weapon;

import java.awt.image.BufferedImage;


import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class AmmoPackSG extends AmmoPack {
	
	protected static int ammo;

	public static int getAmmo() {
		return ammo;
	}
	
	public AmmoPackSG(Vector2D pos) {
		super(pos);
		ammo = 16;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffAmmoPackHG;
	}

}
