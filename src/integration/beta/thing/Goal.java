package integration.beta.thing;

import java.awt.image.BufferedImage;

import integration.beta.tools.MagasinImage;
import integration.beta.tools.raycasting.Vector2D;

public class Goal extends Thing {

	public Goal(Vector2D pos) {
		super(pos);
		
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffPorte;
	}

}
