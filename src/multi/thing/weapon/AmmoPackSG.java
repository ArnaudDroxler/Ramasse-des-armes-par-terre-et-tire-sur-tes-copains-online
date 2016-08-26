
package multi.thing.weapon;

import java.awt.image.BufferedImage;


import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class AmmoPackSG extends AmmoPack {

	public AmmoPackSG(Vector2D pos) {
		super(pos);
		ammo = 30;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffAmmoPackHG;
	}

}
