package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.tools.ImageLoader;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Cut extends Weapon {

	public final BufferedImage[] sprites = { ImageLoader.loadBufferedImage("handgunhud0.png"),
			ImageLoader.loadBufferedImage("handgunhud1.png"), ImageLoader.loadBufferedImage("handgunhud2.png"),
			ImageLoader.loadBufferedImage("handgunhud3.png") };

	private int cpt;

	public Cut(Vector2D pos) {
		super(pos);
		ammo = 1000;
		RoF = 2;
		DpS = 100;
		RaoF = 1.5;
		cpt = 0;
	}

	@Override
	public int computeDamage(double d) {
		if (d < RaoF) 
			return (int) DpS;
		return 0;
		
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
		return sprites[cpt];
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffCle;
	}

}
