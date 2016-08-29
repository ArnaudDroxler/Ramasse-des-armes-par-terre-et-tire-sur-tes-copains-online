package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.thing.Thing;
import multi.tools.ImageLoader;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public abstract class Weapon extends Thing {

	protected double DpS;
	protected double RoF;
	protected double RaoF;
	protected int ammo;
	protected boolean isFiring;
	protected boolean impactMur;
	protected boolean impactEnnemi;

	public final BufferedImage[] spritesImpactEnnemi = { MagasinImage.buffImpactEnnemi0, MagasinImage.buffImpactEnnemi1,
			MagasinImage.buffImpactEnnemi2, MagasinImage.buffImpactEnnemi3 };

	public final BufferedImage[] spritesImpactMur = { MagasinImage.buffImpactMur0, MagasinImage.buffImpactMur1,
			MagasinImage.buffImpactMur2, MagasinImage.buffImpactMur3, MagasinImage.buffImpactMur4 };

	private int cptennemi;
	private int cptmur;

	public abstract int computeDamage(double d);

	public abstract BufferedImage getSpriteHUD();

	public double getDpS() {
		return DpS;
	}

	public double getRoF() {
		return RoF;
	}

	public int getAmmo() {
		return ammo;
	}

	public void sumAmmo(int a) {
		ammo += a;
		System.out.println("Ammo : " + ammo);
	}

	public void subAmmo(int a) {
		ammo -= a;
		System.out.println("Ammo : " + ammo);
	}

	public Weapon(Vector2D pos) {
		super(pos);
		cptennemi = 0;
		cptmur = 0;
		impactMur = false;
		impactEnnemi = false;
	}

	public boolean isFiring() {
		return isFiring;
	}

	public boolean isImpactMur() {
		return impactMur;
	}

	public boolean isImpactEnnemi() {
		return impactEnnemi;
	}

	public void setImpactMur(boolean isImpact) {
		this.impactMur = isImpact;
	}

	public void setImpactEnnemi(boolean isImpact) {
		this.impactEnnemi = isImpact;
	}

	public void setFiring(boolean isFiring) {
		this.isFiring = isFiring;
	}

	public BufferedImage getSpriteImpactEnnemi() {

		Thread threadImpactEnnemi = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					for (cptennemi = 0; cptennemi < 4; cptennemi++) {
						Thread.sleep(70);

					}
					cptennemi = 0;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}
		});
		if (isImpactEnnemi()) {
			impactEnnemi = false;
			threadImpactEnnemi.start();
		}
		return spritesImpactEnnemi[cptennemi];
	}

	public BufferedImage getSpriteImpactMur() {

		Thread threadImpactMur = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					for (cptmur = 0; cptmur < 5; cptmur++) {
						Thread.sleep(90);

					}
					cptmur = 0;

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}
		});
		if (isImpactMur()) {
			impactMur = false;
			threadImpactMur.start();
		}

		return spritesImpactMur[cptmur];
	}

}
