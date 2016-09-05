package multi.tools.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import multi.thing.Armure;

import multi.thing.Medipack;
import multi.thing.Monstre;
import multi.thing.Thing;
import multi.thing.personnage.Ennemi;
import multi.thing.weapon.AmmoPackAR;
import multi.thing.weapon.AmmoPackCS;
import multi.thing.weapon.AmmoPackHG;
import multi.thing.weapon.AmmoPackPR;
import multi.thing.weapon.AmmoPackSG;
import multi.thing.weapon.AmmoPackSmG;
import multi.thing.weapon.AssaultRifle;
import multi.thing.weapon.Chainsaw;
import multi.thing.weapon.HandGun;
import multi.thing.weapon.PrecisionRifle;
import multi.thing.weapon.ShootGun;
import multi.thing.weapon.SubmachineGun;
import multi.tools.raycasting.Vector2D;

public class ImageParser {

	public static LvlMap getMap(String imagePath) {

		try {
			return getMap(ImageIO.read(new File(imagePath + ".png")),
					ImageIO.read(new File(imagePath + "texture.png")));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LvlMap getMap(BufferedImage imgToParse, BufferedImage imgTextureToParse) {

		ImageLvlMap map = new ImageLvlMap(imgToParse.getWidth(), imgToParse.getHeight());

		parse(map, imgToParse);

		parseTexture(map, imgTextureToParse);

		// map.setMapBackgroung(imgToParse);

		map.setMapBackground(imgToParse);

		return map;

	}

	private static void parseTexture(ImageLvlMap map, BufferedImage imgTextureToParse) {
		for (int y = 0; y < imgTextureToParse.getHeight(); y++) {
			for (int x = 0; x < imgTextureToParse.getWidth(); x++) {
				int rgb = imgTextureToParse.getRGB(x, y);
				int blue = (rgb) & 0x000000FF;

				// if (rgb == Color.BLACK.getRGB()) {
				// map.getTextureTab()[x][y] = 0;
				// }
				// else if (rgb == Color.RED.getRGB()) {
				// map.getTextureTab()[x][y] = 1;
				// }
				// else if (rgb == Color.GREEN.getRGB()) {
				// map.getTextureTab()[x][y] = 2;
				// }
				// else if (rgb == Color.BLUE.getRGB()) {
				// map.getTextureTab()[x][y] = 3;
				// }

				map.setTextureTab(x, y, blue);

			}

		}

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
						// map.setStartPosition(new Vector2D(x, y));
						map.getListStartPosition().add(new Vector2D(x, y));
					} // PNJ
					else if (Integer.toHexString(rgb).equals("ffffffaa")) {
						Ennemi joueur = new Ennemi(new Vector2D(x, y), new Vector2D(1, 0));
						map.getListEnnemie().add(joueur);
						map.getListThing().add(joueur);
					} else if (Integer.toHexString(rgb).equals("ff00ff00")) {
						map.getListThing().add(new HandGun(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00ff50")) {
						map.getListThing().add(new AmmoPackHG(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00ee00")) {
						map.getListThing().add(new SubmachineGun(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00ee50")) {
						map.getListThing().add(new AmmoPackSmG(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00dd00")) {
						map.getListThing().add(new ShootGun(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00dd50")) {
						map.getListThing().add(new AmmoPackSG(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00cc00")) {
						map.getListThing().add(new PrecisionRifle(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00cc50")) {
						map.getListThing().add(new AmmoPackPR(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00bb00")) {
						map.getListThing().add(new Chainsaw(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00bb50")) {
						map.getListThing().add(new AmmoPackCS(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00aa00")) {
						map.getListThing().add(new AssaultRifle(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00aa50")) {
						map.getListThing().add(new AmmoPackAR(new Vector2D(x, y)));
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
