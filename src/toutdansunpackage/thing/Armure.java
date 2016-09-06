package toutdansunpackage.thing;

import java.awt.image.BufferedImage;

import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.raycasting.Vector2D;

public class Armure extends Thing {

	public Armure(Vector2D pos) {
		super(pos);
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffArmure[0];
	}

	@Override
	public String getThingType() {
		return "toutdansunpackage.thing.Armure";
	}

}