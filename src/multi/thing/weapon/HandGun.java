package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class HandGun extends Weapon {

	public HandGun(Vector2D pos) {
		super(pos);
		ammo = 10;
		RoF = 2;
		DpS = 50;
		RaoF = 15;
	}

	@Override
	public int computeDamage(double d) {
		if (d < RaoF) {
			return (int) DpS;
		} else {
			return (int) (45 * (1 - Math.exp(0.06 * (d - RaoF))) + DpS);
		}
	}

	@Override
	public BufferedImage getSpriteHUD() {
		return MagasinImage.buffHandGunHUD;
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffHandGun;
	}

}
