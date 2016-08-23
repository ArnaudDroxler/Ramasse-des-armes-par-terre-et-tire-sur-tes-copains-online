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
	}

	@Override
	public int computeDamage(double d) {
		if (ammo > 0) {
			return 50;
		} else {
			return 0;
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
