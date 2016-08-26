
package multi.thing.weapon;

import java.awt.image.BufferedImage;


import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class AmmoPackSmG extends AmmoPack {

	public AmmoPackSmG(Vector2D pos) {
		super(pos);
		ammo = 60;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffAmmoPackSmG;
	}

}
