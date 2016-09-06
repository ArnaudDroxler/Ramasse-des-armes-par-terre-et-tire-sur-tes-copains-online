package toutdansunpackage.thing.weapon;

import java.awt.image.BufferedImage;

import toutdansunpackage.tools.ImageLoader;
import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.raycasting.Vector2D;

public class SubmachineGun extends Weapon {
	
	protected static final double DpS=30;
	protected static final double RoF=8;
	protected static final double RaoF=20;
	protected static final int maxAmmo=200;
	
	private int cpt = 2;
	
	public SubmachineGun(){}

	public SubmachineGun(Vector2D pos) {
		super(pos);
		ammo = 40;
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

					for (cpt = 2; cpt < 4; cpt++) {
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
		return MagasinImage.buffSubMachinGun[cpt];
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffSubMachinGun[0];
	}

	@Override
	public String getThingType() {
		return "toutdansunpackage.thing.weapon.SubmachineGun";
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

}
