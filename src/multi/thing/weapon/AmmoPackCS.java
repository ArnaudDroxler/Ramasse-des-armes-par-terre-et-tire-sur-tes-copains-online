
package multi.thing.weapon;

import java.awt.image.BufferedImage;


import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class AmmoPackCS extends AmmoPack {

	public AmmoPackCS(Vector2D pos) {
		super(pos);
		ammo = 20;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffAmmoPackCS;
	}

}
