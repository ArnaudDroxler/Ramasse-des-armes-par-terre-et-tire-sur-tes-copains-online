package base.tools.map;

import java.awt.Color;
import java.awt.image.BufferedImage;

import base.thing.Key;
import base.tools.raycasting.Vector2D;

public class ImageLvlMap extends LvlMap {

	public ImageLvlMap(){
		
		super();
	}

	@Override
	public boolean inWall(Vector2D pos) {
		return inWall((int)pos.getdX(), (int)pos.getdY()); 
	}

	@Override
	public boolean inWall(double x, double y) {
		return (getMapBackground().getRGB((int)x, (int)y) == Color.black.getRGB()); 
	}

}
