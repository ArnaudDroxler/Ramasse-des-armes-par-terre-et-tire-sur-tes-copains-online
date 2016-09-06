
package toutdansunpackage.thing.weapon;

import java.awt.image.BufferedImage;

import toutdansunpackage.tools.ImageLoader;
import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.raycasting.Vector2D;

public class AssaultRifle extends Weapon {

	private int cpt;
	
	protected static final double DpS=60;
	protected static final double RoF=4;
	protected static final double RaoF=40;
	protected static final int maxAmmo=150;
	protected static final int ammoRecharge=30;
	
	public AssaultRifle(){}

	public AssaultRifle(Vector2D pos) {
		super(pos);
		ammo = 30;
		cpt = 2;
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

					for (cpt = 2; cpt < 5; cpt++) {
						Thread.sleep(100);
					}
					cpt = 2;

				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}
		});

		if (isFiring) {
			isFiring = false;
			threadAnimation.start();
		}
		return MagasinImage.buffAssaltRifle[cpt];
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffAssaltRifle[0];
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
