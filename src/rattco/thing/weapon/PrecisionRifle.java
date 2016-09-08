
package rattco.thing.weapon;

import java.awt.image.BufferedImage;

import rattco.tools.ImageLoader;
import rattco.tools.MagasinImage;
import rattco.tools.raycasting.Vector2D;

public class PrecisionRifle extends Weapon {

	private int cpt;
	
	protected static final double DpS=100;
	protected static final double RoF=0.5;
	protected static final double RaoF=60;
	protected static final int maxAmmo=40;
	protected static final int ammoRecharge=8;
	
	public PrecisionRifle(){}

	public PrecisionRifle(Vector2D pos) {
		super(pos);
		ammo = 8;
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

					for (cpt = 0; cpt < 5; cpt++) {
						Thread.sleep(120);
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
		return MagasinImage.buffPrecisionRifleAnimation[cpt];
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffPrecisionRifle[0];
	}

	@Override
	public double getDpS() {
		return DpS;
	}

	@Override
	public double getRoF() {
		return RoF;
	}

	@Override
	public int getMaxAmmo() {
		return maxAmmo;
	}

	@Override
	public int getAmmoRecharge() {
		return ammoRecharge;
	}

}
