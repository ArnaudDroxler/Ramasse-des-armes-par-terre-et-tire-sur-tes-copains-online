package multi.thing;

import java.awt.image.BufferedImage;

import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Medipack extends Thing {

	public Medipack(Vector2D pos) {
		super(pos);

	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffMedipack[0];
	}

	@Override
	public String getThingType() {
		return "multi.thing.Medipack";
	}

}