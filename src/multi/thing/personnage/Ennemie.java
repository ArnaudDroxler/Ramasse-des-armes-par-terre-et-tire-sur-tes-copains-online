package multi.thing.personnage;

import java.awt.image.BufferedImage;

import multi.tools.ImageLoader;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Ennemie extends Personnage {

	public Ennemie(Vector2D startPosition, Vector2D startDirection) {
		super(startPosition, startDirection);
		vie = 5;
		armure = 0;
		v = random.nextDouble() * .06;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffYoanBlanc;
	}

	@Override
	public BufferedImage getSprite(int dir) {

		if (dir == 1) {
			// gauche 1
			return MagasinImage.buffFantome3;
		}
		if (dir == 2) {
			// gauche 2
			return MagasinImage.buffFantome2;
		}
		if (dir == 3) {
			// gauche 3
			return MagasinImage.buffFantome1;
		}
		if (dir == 4) {
			// dos 4
			return MagasinImage.buffFantome0;
		}
		if (dir == 5) {
			// droite 5
			return MagasinImage.buffFantome7;
		}
		if (dir == 6) {
			// droite 6
			return MagasinImage.buffFantome6;
		}
		if (dir == 7) {
			// droite 7
			return MagasinImage.buffFantome5;
		}

		// face 0
		return MagasinImage.buffFantome4;

	}

}
