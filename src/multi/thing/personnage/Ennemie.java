package multi.thing.personnage;

import java.awt.image.BufferedImage;

import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Ennemie  extends Personnage{

	public Ennemie(Vector2D startPosition, Vector2D startDirection) {
		super(startPosition, startDirection);
	
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffYoanBlanc;
	}

}
