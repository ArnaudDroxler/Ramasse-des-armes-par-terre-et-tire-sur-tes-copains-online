
package multi.thing.weapon;

import java.awt.image.BufferedImage;


import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class AmmoPackSmG extends AmmoPack {
	
	protected static int ammo;

	public static int getAmmo() {
		return ammo;
	}
	
	public AmmoPackSmG(Vector2D pos) {
		super(pos);
		ammo = 40;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffSubMachinGun[1];
	}

}
