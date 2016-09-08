package rattco.thing;

import java.awt.image.BufferedImage;

import rattco.tools.MagasinImage;
import rattco.tools.raycasting.Vector2D;

public class Armure extends Thing {

	public Armure(Vector2D pos) {
		super(pos);
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffArmure[0];
	}

}