package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.thing.Thing;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class AmmoPackHG extends Thing {

	public AmmoPackHG(Vector2D pos) {
		super(pos);

	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffAmmoPackHG;
	}

}
