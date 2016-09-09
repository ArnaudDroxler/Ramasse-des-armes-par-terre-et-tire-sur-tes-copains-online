
package rattco.thing;

import java.awt.image.BufferedImage;
import rattco.tools.raycasting.Vector2D;

/**
 * Classe créée par Maxime et Vincent au P2-Java
 * Chaque objet du jeu est une Thing
 *
 */
public abstract class Thing {

	private static final float dAlpha=2;
	private static final float v = 0.1f;
	
	private Vector2D position;
	private Vector2D direction;
	private boolean exists;

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
		exists=true;
	}

	public Thing(Vector2D vector2d) {
		this(vector2d, new Vector2D(1, 0));
	}

	/**
	 * Vectoriellement : OA + (Dir * scale)
	 */
	public void forward() {
		position = position.add(direction.scale(v));
	}

	/**
	 * Vectoriellement : OA - (Dir * scale)
	 */
	public void backward() {
		position = position.add(direction.scale(-v));
	}

	/**
	 * Vectoriellement : OA + (dir*-90°)
	 */
	public void strafeRight() {
		Vector2D perpendiculairDroit = direction.rotate(Math.PI / 2.0).scale(v);
		position = position.add(perpendiculairDroit);
	}

	/**
	 * Vectoriellement : OA + (dir*90°)
	 */
	public void strafeLeft() {
		Vector2D perpendiculairGauche = direction.rotate(-Math.PI / 2.0).scale(v);
		position = position.add(perpendiculairGauche);
	}

	public void rotateRight() {
		direction = direction.rotate(Math.toRadians(dAlpha));
	}

	public void rotateLeft() {
		direction = direction.rotate(Math.toRadians(-dAlpha));
	}

	public void rotate(double d) {
		direction = direction.rotate(Math.toRadians(d / 4));
	}

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

	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public abstract BufferedImage getSprite();

	public BufferedImage getSprite(int dir) {
		return null;
	}
	public int getNbSecteurs(){
		return 1;
	}
	
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
					e.printStackTrace();
				}
				exists=true;
			}
		});
		t.start();
	}

	public void hideForAWhile() {
		hideForAWhile(15000);
	}
}
