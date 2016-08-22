package multi.thing;

import java.awt.image.BufferedImage;

import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Goal extends Thing {

	public Goal(Vector2D pos) {
		super(pos);
		
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffPorte;
	}

}
