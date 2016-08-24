package multi.thing.personnage;

import java.awt.image.BufferedImage;

import multi.tools.ImageLoader;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Ennemie extends Personnage {

	public Ennemie(Vector2D startPosition, Vector2D startDirection) {
		super(startPosition, startDirection);
		vie = 100;
		armure = 0;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffYoanBlanc;
	}

	@Override
	public BufferedImage getSprite(int dir) {

		if (dir == 2) {
			// gauche 2
			return MagasinImage.;
		}
		if (dir == 3) {
			// gauche 3
			return MagasinImage.;
		}
		if (dir == 4) {
			// gauche 4
			return MagasinImage.;
		}
		if (dir == 5) {
			// dos
			return MagasinImage.;
		}
		if (dir == 6) {
			// dos
			return MagasinImage.;
		}
		if (dir == 7) {
			// gauche
			return MagasinImage.;
		}
		if (dir == 8) {
			// gauche
			return MagasinImage.;
		}

		// face 1
		return MagasinImage.;

	}

}
