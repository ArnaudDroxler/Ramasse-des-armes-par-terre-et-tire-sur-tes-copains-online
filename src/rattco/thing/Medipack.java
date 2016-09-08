package rattco.thing;

import java.awt.image.BufferedImage;

import rattco.tools.MagasinImage;
import rattco.tools.raycasting.Vector2D;

public class Medipack extends Thing {

	public Medipack(Vector2D pos) {
		super(pos);

	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffMedipack[0];
	}

}