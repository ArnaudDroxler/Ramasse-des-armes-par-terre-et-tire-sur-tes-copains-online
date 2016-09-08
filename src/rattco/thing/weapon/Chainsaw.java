package rattco.thing.weapon;

import java.awt.image.BufferedImage;

import rattco.client.JFrameClient;
import rattco.tools.MagasinImage;
import rattco.tools.raycasting.Vector2D;

public class Chainsaw extends Weapon {

	private int cpt;
	
	protected static final double DpS=100;
	protected static final double RoF=5;
	protected static final double RaoF=2;
	protected static final int maxAmmo=1000;
	protected static final int ammoRecharge=200;
	
	public Chainsaw(){}

	public Chainsaw(Vector2D pos) {
		super(pos);
		ammo = 200;
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
					int i = 0;
					while(JFrameClient.mouseLeftPressed){
						cpt = i++%2+2;
						Thread.sleep(100);
					}
					cpt=0;
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		if (isFiring) {
			isFiring = false;
			threadAnimation.start();
		}
		return MagasinImage.buffChainsawAnimation[cpt];
	}

	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffChainsaw[0];
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
