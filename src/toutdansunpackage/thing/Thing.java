
package toutdansunpackage.thing;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sun.scenario.effect.impl.sw.sse.SSEPhongLighting_DISTANTPeer;

import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.raycasting.Vector2D;

public abstract class Thing {

	protected static final Random random = new Random();
	protected static final float v = 0.1f;
	
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Thing(Vector2D pos, Vector2D dir) {
		this(pos.getdX(), pos.getdY(), dir.getdX(), dir.getdY());
	}

	public Thing() {
		this(new Vector2D(0, 0), new Vector2D(1, 0));
	}

	public Thing(double posX, double posY, double dirX, double dirY) {
		position = new Vector2D();
		direction = new Vector2D();
		position.setdX(posX);
		position.setdY(posY);
		direction.setdX(dirX);
		direction.setdY(dirY);
		dAlpha = 1;
		exists=true;
	}

	public Thing(Vector2D vector2d) {
		this(vector2d, new Vector2D(1, 0));
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Vectoriellement : OA + (Dir * scale)
	 */
	public void forward() {
		// 1 valeur placeholder, on devra trouver une vitesse � mettre ici.
		// System.out.println(position + " " + v + " " +direction);
		position = position.add(direction.scale(v));
	}

	/**
	 * Vectoriellement : OA - (Dir * scale)
	 */
	public void backward() {
		position = position.add(direction.scale(-v));
	}

	/**
	 * Vectoriellement : OA + (dir*-90�)
	 */
	public void strafeRight() {
		// Vecteur direction perpendiculaire
		Vector2D perpendiculairDroit = direction.rotate(Math.PI / 2.0).scale(v);
		// Encore une question de vitesse � revoir.
		position = position.add(perpendiculairDroit);
	}

	/**
	 * Vectoriellement : OA + (dir*90�)
	 */
	public void strafeLeft() {
		Vector2D perpendiculairGauche = direction.rotate(-Math.PI / 2.0).scale(v);
		position = position.add(perpendiculairGauche);
	}

	public void rotateRight() {
		// Valeur placeholder pour la vitesse de rotation
		direction = direction.rotate(Math.toRadians(dAlpha));
	}

	public void rotateLeft() {
		direction = direction.rotate(Math.toRadians(-dAlpha));
	}

	public void rotate(double d) {
		direction = direction.rotate(Math.toRadians(d / 4));
	}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setPosition(Vector2D pos) {
		position = pos;
	}

	public void setDirection(Vector2D dir) {
		direction = dir;
	}

	public void setPosition(double posX, double posY) {
		position.setdX(posX);
		position.setdY(posY);
	}

	public void setDirection(double dirX, double dirY) {
		direction.setdX(dirX);
		direction.setdY(dirY);
	}


	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getDirection() {
		return direction;
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private Vector2D position;
	private Vector2D direction;
	private float dAlpha;

	public abstract BufferedImage getSprite();

	public BufferedImage getSprite(int dir) {
		return null;
	}
	public int getNbSecteurs(){
		return 1;
	}
	public abstract String getThingType();
	private boolean exists;
	
	public boolean exists() {
		return exists;
	}
	
	public void hideForAWhile(int timeToHideThing){
		exists=false;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(timeToHideThing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				exists=true;
			}
		});
		t.start();
	}

	public void hideForAWhile() {
		hideForAWhile(5000);
	}
}
