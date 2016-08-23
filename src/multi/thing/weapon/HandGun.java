package multi.thing.weapon;

import java.awt.image.BufferedImage;

public class HandGun extends Weapon {
	
	public HandGun() {
		super();
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
	
		return null;
	}

	@Override
	public BufferedImage getSprite() {
	
		return null;
	}

}
