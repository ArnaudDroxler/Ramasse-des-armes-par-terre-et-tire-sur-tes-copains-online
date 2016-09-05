package multi.tools.map;

import java.awt.Color;
import java.awt.image.BufferedImage;

import multi.tools.raycasting.Vector2D;

public class ImageLvlMap extends LvlMap {

	public ImageLvlMap(int i, int j){
		
		super(i,j);
	}

	@Override
	public boolean inWall(Vector2D pos) {
		return inWall((int)pos.getdX(), (int)pos.getdY()); 
	}

	@Override
	public boolean inWall(double x, double y) {
		// vérifie si le point donné est dans l'image
		// pour éviter de chercher dans une mémoire inexistante
		if(x>=0 && x < getMapBackground().getWidth() && y>=0 && y < getMapBackground().getHeight()){
			// si le pixel qui correspond à ce point est noir, on est dans un mur, on envoie true
			return (getMapBackground().getRGB((int)x, (int)y) == Color.black.getRGB()); 
		}
		// si on est en dehors de la map
		return false;
	}

}
