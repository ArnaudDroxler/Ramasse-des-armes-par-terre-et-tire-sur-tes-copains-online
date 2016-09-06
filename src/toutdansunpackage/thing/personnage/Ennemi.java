package toutdansunpackage.thing.personnage;

import java.awt.image.BufferedImage;

import toutdansunpackage.tools.ImageLoader;
import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.raycasting.Vector2D;

public class Ennemi extends Personnage {

	public Ennemi(Vector2D startPosition, Vector2D startDirection) {
		super(startPosition, startDirection);
		vie = 100;
		armure = 0;
		t=0;
		v = random.nextDouble() * .03 + 0.01 ;
	}
	
	@Override
	public BufferedImage getSprite() {
		return MagasinImage.buffPika[0];
	}

	@Override
	public BufferedImage getSprite(int dir) {
		return MagasinImage.buffPika[dir];
	}
	
	public int getNbSecteurs(){
		return MagasinImage.buffPika.length;
	}

	public void bouge() {
		
		forward();
		if(t++==100){
			double randAlpha = (Math.random()) * alphaMax - alphaMax / 2;
			Vector2D direction = getDirection().rotate(randAlpha);
			setDirection(direction);
			t=0;
		}
		
		setDirection(getDirection().rotate(0.005));
	}
	
	private int t;
	private static double alphaMax = Math.PI/3;
	@Override
	public String getThingType() {
		return "toutdansunpackage.thing.personnage.Ennemi";
	}

}
