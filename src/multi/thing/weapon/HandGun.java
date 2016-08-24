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
		RaoF = 10;
	}

	@Override
	public int computeDamage(double d) {
		if (d < RaoF) {
			return (int) DpS;
		} else {
			int dammage = (int) (45 * (1 - Math.exp(0.06 * (Math.abs(RaoF - d)))) + DpS);
			if (dammage < 0) {
				return 0;
			} else
				return dammage;
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
