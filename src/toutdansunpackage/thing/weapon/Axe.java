
package toutdansunpackage.thing.weapon;

import java.awt.image.BufferedImage;

import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.raycasting.Vector2D;

public class Axe extends Weapon {

	private int cpt;
	
	protected static final double DpS=100;
	protected static final double RoF=1;
	protected static final double RaoF=2;
	
	public Axe(){}

	public Axe(Vector2D pos) {
		super(pos);
		ammo = 1000; 
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
					for (cpt = 0; cpt < 11; cpt++) {
						Thread.sleep(75);
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
		return MagasinImage.buffAxeAnimation[cpt];
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffAxe[0];
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
		return 0;
	}

	@Override
	public int getAmmoRecharge() {
		return 1000;
	}
	
}
