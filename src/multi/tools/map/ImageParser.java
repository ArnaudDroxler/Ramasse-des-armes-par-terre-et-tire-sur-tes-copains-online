package multi.tools.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import multi.thing.Goal;
import multi.thing.Key;
import multi.thing.Monstre;
import multi.thing.Thing;
import multi.tools.raycasting.Vector2D;

public class ImageParser {

	public static LvlMap getMap(String imageName){
		try {
			return getMap(ImageIO.read(new File(imageName)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LvlMap getMap(BufferedImage imgToParse){
		
		ImageLvlMap map = new ImageLvlMap();
		
		map.width = imgToParse.getWidth();
		map.height = imgToParse.getHeight();
		
		parse(map,imgToParse);
		
		//map.setMapBackgroung(imgToParse);
		
		map.setMapBackground(imgToParse);
		
		
		return map;
		
	}
	
	private static void parse(ImageLvlMap map, BufferedImage imgToParse) {
		// itérer sur chaque pixel de l'image et ajouter à map startPos, goalPos, listThing
		// remplacer par un pixel blanc à chaque fois qu'on rencontre qqch
		for (int y = 0; y < imgToParse.getHeight(); y++) {
			for (int x = 0; x < imgToParse.getWidth(); x++) {
				int rgb = imgToParse.getRGB(x, y);
				if(rgb != Color.BLACK.getRGB() && rgb != Color.WHITE.getRGB()){
					if(rgb == Color.RED.getRGB()){
						map.setStartPosition(new Vector2D(x,y));
						//map.getListThing().add(new StartPos(new Vector2D(x,y)));
					}else if(rgb == Color.yellow.getRGB()){
						map.setGoalPosition(new Vector2D(x,y));
						map.getListThing().add(new Goal(new Vector2D(x,y)));
					}else if(rgb == Color.blue.getRGB()){
						Key cle = new Key(new Vector2D(x,y));
						map.setKey(cle);
						map.getListThing().add(cle);
					}else if(rgb == Color.green.getRGB()){
						Monstre monstre = new Monstre(new Vector2D(x,y));
						map.getListThing().add(monstre);
						map.getListMonstre().add(monstre);
					}
					imgToParse.setRGB(x, y, Color.white.getRGB());
				}else{
					//rien
				}
			}
			System.out.println();
			
		}
		
		// écrire les murs dans la matriceMap ?
		
		
	}
	
}
