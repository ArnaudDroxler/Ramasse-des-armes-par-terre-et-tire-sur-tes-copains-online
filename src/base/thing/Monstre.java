package base.thing;

import java.awt.image.BufferedImage;

import base.tools.ImageLoader;
import base.tools.raycasting.Vector2D;

public class Monstre extends Thing {

	private int i;
	private int cpt;

	public final BufferedImage[] sprites = {
			ImageLoader.loadBufferedImage("yoan00.png"),
			ImageLoader.loadBufferedImage("yoan01.png"),
			ImageLoader.loadBufferedImage("yoan02.png"),
			ImageLoader.loadBufferedImage("yoan03.png")
			};

	public Monstre(Vector2D pos) {
		super(pos);
		v = random.nextDouble() * .06;
		cpt=2;
	}
	
	

	public BufferedImage getSprite() {
		if(--cpt==0){
			cpt=2;
			i++;
			i %= sprites.length;
		}
		return sprites[i];

	}
}
