package multi.thing;

import java.awt.image.BufferedImage;

import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Armure extends Thing {

	public Armure(Vector2D pos) {
		super(pos);
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffArmure;
	}

}