package base.tools.map;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import base.tools.raycasting.Vector2D;

public class UseImageLvlMap {

	public static void main(String args[]){
		LvlMap map = ImageParser.getMap("amazingWorld.png");

		new JFrameMap(map);

		System.out.println(map.inWall(new Vector2D(0.5,0.5)));
		System.out.println(map.inWall(new Vector2D(1.0,1.0)));
		System.out.println(map.inWall(new Vector2D(1.5,1.5)));
		System.out.println(map.inWall(new Vector2D(1.999,1.999)));
		System.out.println(map.inWall(new Vector2D(2.0,2.0)));
	}
	
}
