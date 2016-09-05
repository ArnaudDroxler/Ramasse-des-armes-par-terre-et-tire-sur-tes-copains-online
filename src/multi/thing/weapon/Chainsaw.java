package multi.thing.weapon;

import java.awt.image.BufferedImage;

import com.sun.javafx.font.LogicalFont;

import multi.FenetreJeu;
import multi.Logique;
import multi.tools.ImageLoader;
import multi.tools.MagasinImage;
import multi.tools.raycasting.Vector2D;

public class Chainsaw extends Weapon {

	private int cpt;

	public Chainsaw(Vector2D pos) {
		super(pos);
		ammo = 200;
		maxAmmo = 1000;
		RoF = 5;
		DpS = 50;
		RaoF = 2;
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
					while(FenetreJeu.mouseLeftPressed){
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
	public String getThingType() {
		return "multi.thing.weapon.Chainsaw";
	}

}
