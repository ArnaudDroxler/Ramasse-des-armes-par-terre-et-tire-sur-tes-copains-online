package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class HandGun extends Weapon {
	
	public HandGun(Vector2D pos) {
		super(pos);
		ammo = 0;
		RoF = 2;
		DpS = 50;
	}

	@Override
	public int computeDamage() {
		
		return 0;
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
