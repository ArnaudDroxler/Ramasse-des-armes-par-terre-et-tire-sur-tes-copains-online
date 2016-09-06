package multi.thing.weapon;

import java.awt.image.BufferedImage;

import multi.thing.Thing;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public abstract class Weapon extends Thing {

	protected double DpS;
	protected double RoF;
	protected double RaoF;
	protected int ammo;
	protected int maxAmmo;
	

	protected boolean isFiring;
	protected boolean impactMur;
	protected boolean impactEnnemi;

	private int cptennemi;
	private int cptmur;
	
	public Weapon(){}

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
	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void sumAmmo(int a) {
		if (ammo < maxAmmo) {
			ammo += a;
		}
	}

	public void subAmmo(int a) {
		ammo -= a;
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

	public void setIsFiring(boolean isFiring) {
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
		return MagasinImage.buffImpactEnnemi[cptennemi];
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

		return  MagasinImage.buffImpactMur[cptmur];
	}

}
