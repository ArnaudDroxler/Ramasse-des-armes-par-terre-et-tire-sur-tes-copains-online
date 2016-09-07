package toutdansunpackage.thing.weapon;

import java.awt.image.BufferedImage;

import toutdansunpackage.thing.Thing;
import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.raycasting.Vector2D;

public abstract class Weapon extends Thing {
	
	protected int ammo;

	protected boolean isFiring;
	protected boolean impactMur;
	protected boolean impactEnnemi;

	private int cptennemi;
	private int cptmur;
	
	public Weapon(){}

	public abstract int computeDamage(double d);

	public abstract BufferedImage getSpriteHUD();

	public int getAmmo(){return ammo;}
	
	public abstract double getDpS();
	public abstract double getRoF();
	public abstract int getMaxAmmo();
	public abstract int getAmmoRecharge();

	public void sumAmmo(int a) {
		ammo += a;
		if (ammo > getMaxAmmo()) {
			ammo=getMaxAmmo();
		}
	}
	
	public void sumAmmo() {
		sumAmmo(getAmmoRecharge());
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
	
	public void init(){
		ammo = getAmmoRecharge();
	}

	public boolean isAmmoPack(Thing thing) {
		if(thing instanceof AmmoPack){
			return ((AmmoPack)thing).getAmmoType().equals(this.getClass().getSimpleName());
		}
		return false;
	}

}
