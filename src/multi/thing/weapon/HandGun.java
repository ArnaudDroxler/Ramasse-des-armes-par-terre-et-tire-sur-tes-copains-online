package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.tools.ImageLoader;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class HandGun extends Weapon {

	private int cpt;

	public HandGun(Vector2D pos) {
		super(pos);
		ammo = 12;
		maxAmmo = 60;
		RoF = 2;
		DpS = 50;
		RaoF = 10;
		cpt = 0;
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

		Thread threadAnimation = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					for (cpt = 1; cpt < 4; cpt++) {
						Thread.sleep(100);
					}
					cpt = 0;

				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}
		});

		if (isFiring) {
			isFiring = false;
			threadAnimation.start();
		}
		return MagasinImage.buffShootgunHUD[cpt];
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffHandGun;
	}

}
