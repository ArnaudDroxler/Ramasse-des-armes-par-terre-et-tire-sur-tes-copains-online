package multi.tools.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import multi.thing.Armure;
import multi.thing.Goal;
import multi.thing.Key;
import multi.thing.Medipack;
import multi.thing.Monstre;
import multi.thing.Thing;
import multi.thing.personnage.Ennemi;
import multi.thing.weapon.AmmoPackHG;
import multi.thing.weapon.AmmoPackSmG;
import multi.thing.weapon.HandGun;
import multi.thing.weapon.SubmachineGun;
import multi.tools.raycasting.Vector2D;

public class ImageParser {

	public static LvlMap getMap(String imageName) {
		try {
			return getMap(ImageIO.read(new File(imageName)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LvlMap getMap(BufferedImage imgToParse) {

		ImageLvlMap map = new ImageLvlMap();

		map.width = imgToParse.getWidth();
		map.height = imgToParse.getHeight();

		parse(map, imgToParse);

		// map.setMapBackgroung(imgToParse);

		map.setMapBackground(imgToParse);

		return map;

	}

	private static void parse(ImageLvlMap map, BufferedImage imgToParse) {
		// itérer sur chaque pixel de l'image et ajouter à map startPos,
		// goalPos, listThing
		// remplacer par un pixel blanc à chaque fois qu'on rencontre qqch

		for (int y = 0; y < imgToParse.getHeight(); y++) {
			for (int x = 0; x < imgToParse.getWidth(); x++) {
				int rgb = imgToParse.getRGB(x, y);
				if (rgb != Color.BLACK.getRGB() && rgb != Color.WHITE.getRGB()) {
					// pack de vie
					if (Integer.toHexString(rgb).equals("ffff0000")) {
						map.getListThing().add(new Medipack(new Vector2D(x, y)));
					} // pack armure
					else if (Integer.toHexString(rgb).equals("ff0000ff")) {
						map.getListThing().add(new Armure(new Vector2D(x, y)));
					} // point de spawn
					else if (Integer.toHexString(rgb).equals("ffffff00")) {
						//map.setStartPosition(new Vector2D(x, y));
						map.getListStartPosition().add(new Vector2D(x, y));
					} // PNJ
					else if (Integer.toHexString(rgb).equals("ffffffaa")) {
						Ennemi joueur = new Ennemi(new Vector2D(x, y), new Vector2D(1, 0));
						map.getListEnnemie().add(joueur);
						map.getListThing().add(joueur);
					} // Arme de Poing
					else if (Integer.toHexString(rgb).equals("ff00ff00")) {
						map.getListThing().add(new HandGun(new Vector2D(x, y)));
					} // Mitraillette
					else if (Integer.toHexString(rgb).equals("ff00ee00")) {
						map.getListThing().add(new SubmachineGun(new Vector2D(x, y)));
					} // pack munition arme de poing
					else if (Integer.toHexString(rgb).equals("ff00ff50")) {
						map.getListThing().add(new AmmoPackHG(new Vector2D(x, y)));
					} // pack munition mitraillette
					else if (Integer.toHexString(rgb).equals("ff00ee50")) {
						map.getListThing().add(new AmmoPackSmG(new Vector2D(x, y)));
					}
					imgToParse.setRGB(x, y, Color.white.getRGB());
				} else {
					// rien
				}
			}

		}

		// écrire les murs dans la matriceMap ?

	}

}
