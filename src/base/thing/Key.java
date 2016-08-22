package base.thing;

import java.awt.image.BufferedImage;

import base.tools.MagasinImage;
import base.tools.raycasting.Vector2D;

public class Key extends Thing {

	public Key(Vector2D pos) {
		super(pos);
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffCle;
	}

}
