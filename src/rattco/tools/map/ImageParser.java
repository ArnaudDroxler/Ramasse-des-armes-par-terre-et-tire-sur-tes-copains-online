package rattco.tools.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rattco.thing.Armure;
import rattco.thing.Medipack;
import rattco.thing.weapon.AmmoPack;
import rattco.thing.weapon.AssaultRifle;
import rattco.thing.weapon.Chainsaw;
import rattco.thing.weapon.HandGun;
import rattco.thing.weapon.PrecisionRifle;
import rattco.thing.weapon.ShootGun;
import rattco.thing.weapon.SubmachineGun;
import rattco.tools.ImageLoader;
import rattco.tools.MagasinImage;
import rattco.tools.raycasting.Vector2D;

/**
 * Classe utilitaire qui permet notamment de transformer deux images png en un objet map
 */
public class ImageParser {

	/**
	 * A partir d'un nom de dossier, cette méthode charge un objet map, les textures
	 * et optionnellement une image de fond.
	 * Il faut que les fichiers aient les bons noms (fond.png...)
	 * Les images dans le dossier textures doivent faire 256x256 pixels
	 */
	public static LvlMap getMapFromFolder(String folderName) {
		MagasinImage.buffFond = ImageLoader.loadBufferedImage(folderName + "/fond.png");
		try {
			MagasinImage.buffTextMur = ImageLoader.loadImagesFromFolder(folderName + "/textures");
		} catch (NullPointerException e) {
			MagasinImage.buffTextMur = ImageLoader.loadImagesFromFolder("sprite/wall/texturesMaison");
		}
		try {
			return getMap(ImageIO.read(new File(folderName + "/map.png")),
					ImageIO.read(new File(folderName + "/maptexture.png")));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LvlMap getMap(String mapName) {

		try {
			return getMap(ImageIO.read(new File("sprite/map/" + mapName + ".png")),
					ImageIO.read(new File("sprite/map/" + mapName + "texture.png")));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LvlMap getMap(BufferedImage imgToParse, BufferedImage imgTextureToParse) {
		
		// crée une map de la même taille que l'image
		ImageLvlMap map = new ImageLvlMap(imgToParse.getWidth(), imgToParse.getHeight());

		// place les objets dans l'objet map 
		parse(map, imgToParse);
		
		// défini les index des textures dans un tableau 2d
		parseTexture(map, imgTextureToParse);

		map.setMapBackground(imgToParse);

		return map;

	}
	
	/**
	 * Parseur de textures
	 * Pour savoir quel texture afficher à quel endroit, la map a un tableau 2D de int
	 * textureTab qui contient les index des images de textures chargées dans MagasinImage.buffTextMur
	 * C'est la composante bleue de la map de texture qui définit l'index du tableau
	 */
	private static void parseTexture(ImageLvlMap map, BufferedImage imgTextureToParse) {
		for (int y = 0; y < imgTextureToParse.getHeight(); y++) {
			for (int x = 0; x < imgTextureToParse.getWidth(); x++) {
				int rgb = imgTextureToParse.getRGB(x, y);
				
				// Le ET logique permet d'obtenir la composante bleue à partir du int rgb
				//...................aarrvvbb
				int i = (rgb) & 0x000000FF;
				
				// normalement, i devrait être un chiffre entre 0 et le nb d'images qu'il
				// y a dans le dossier textures de la map
				map.setTextureTab(x, y, i);
			}
		}

	}

	/**
	 * Parseur de map
	 * cette fonction place les objets dans la map à partir de la couleur
	 * des pixels dans l'image imgToParse.
	 * Pour les murs, c'est la méthode inWall() de imageLvlMap qui va 
	 * retourner true si le pixel correspondant à la position donnée est noir
	 */
	private static void parse(ImageLvlMap map, BufferedImage imgToParse) {

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
					}
					else if (Integer.toHexString(rgb).equals("ff00ff00")) {
						map.getListThing().add(new HandGun(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00ff50")) {
						map.getListThing().add(new AmmoPack(new Vector2D(x, y), "HandGun"));
					} else if (Integer.toHexString(rgb).equals("ff00ee00")) {
						map.getListThing().add(new SubmachineGun(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00ee50")) {
						map.getListThing().add(new AmmoPack(new Vector2D(x, y), "SubmachineGun"));
					} else if (Integer.toHexString(rgb).equals("ff00dd00")) {
						map.getListThing().add(new ShootGun(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00dd50")) {
						map.getListThing().add(new AmmoPack(new Vector2D(x, y), "ShootGun"));
					} else if (Integer.toHexString(rgb).equals("ff00cc00")) {
						map.getListThing().add(new PrecisionRifle(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00cc50")) {
						map.getListThing().add(new AmmoPack(new Vector2D(x, y), "PrecisionRifle"));
					} else if (Integer.toHexString(rgb).equals("ff00bb00")) {
						map.getListThing().add(new Chainsaw(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00bb50")) {
						map.getListThing().add(new AmmoPack(new Vector2D(x, y), "Chainsaw"));
					} else if (Integer.toHexString(rgb).equals("ff00aa00")) {
						map.getListThing().add(new AssaultRifle(new Vector2D(x, y)));
					} else if (Integer.toHexString(rgb).equals("ff00aa50")) {
						map.getListThing().add(new AmmoPack(new Vector2D(x, y), "AssaultRifle"));
					}

					imgToParse.setRGB(x, y, Color.white.getRGB());
				} else {
					// rien
				}
			}

		}

	}

}
