package clientserver.thing;

import java.awt.image.BufferedImage;

import clientserver.tools.MagasinImage;
import clientserver.tools.raycasting.Vector2D;

public class Medipack extends Thing {

	public Medipack(Vector2D pos) {
		super(pos);
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffMedipack;
	}

}