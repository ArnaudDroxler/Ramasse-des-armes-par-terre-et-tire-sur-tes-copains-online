package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.tools.ImageLoader;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class SubmachineGun extends Weapon {

	public final BufferedImage[] sprites = { ImageLoader.loadBufferedImage("submachingunhud0.png"),
			ImageLoader.loadBufferedImage("submachingunhud1.png"), ImageLoader.loadBufferedImage("submachingunhud2.png")};

	private int cpt;

	public SubmachineGun(Vector2D pos) {
		super(pos);
		ammo = 30;
		RoF = 8;
		DpS = 40;
		RaoF = 20;
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

					for (cpt = 1; cpt < 2; cpt++) {
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
		return sprites[cpt];
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffSubMachinGun;
	}

}
