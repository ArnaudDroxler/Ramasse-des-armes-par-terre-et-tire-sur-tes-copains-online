package integration.beta.thing;

import java.awt.image.BufferedImage;

import integration.beta.tools.MagasinImage;
import integration.beta.tools.raycasting.Vector2D;

public class Key extends Thing {

	public Key(Vector2D pos) {
		super(pos);
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffCle;
	}

}
