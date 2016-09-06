
package toutdansunpackage.tools;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageLoader {

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public int[][] transformImageEnTableau(BufferedImage image) {
		int[][] tabImage = null;
		int largeurImage = image.getWidth();
		int hauteurImage = image.getHeight();

		for (int colonne = 0; colonne < largeurImage; colonne++) {
			for (int ligne = 0; ligne < hauteurImage; ligne++) {
				// Traitement ici
				tabImage[colonne][ligne] = image.getRGB(colonne, ligne);
			}
		}
		return tabImage;

	}

	/*----------------------------------------------*\
	|*		 Version Synchrone (bloquant)			*|
	\*----------------------------------------------*/

	/**
	 * Bloquant
	 */
	public static ImageIcon loadSynchrone(String nameFile) {
		return new ImageIcon(nameFile);
	}

	/**
	 * Bloquant Depuis un .jar
	 */
	public static ImageIcon loadSynchroneJar(String nameFile) {
		URL url = ClassLoader.getSystemResource(nameFile);
		return new ImageIcon(url);
	}

	public static BufferedImage loadBufferedImage(String nameFile) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(nameFile));
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return img;
	}

	/*---------------------------------------------*\
	|*		 Version Assynchrone (non-bloquant)		*|
	\*----------------------------------------------*/

	/**
	 * Non Bloquant
	 */
	public static ImageIcon loadAsynchrone(String nameFile) {
		Image image = Toolkit.getDefaultToolkit().getImage(nameFile);
		return new ImageIcon(image);
	}

	/**
	 * Non Bloquant Depuis un .jar
	 */
	public static ImageIcon loadAsynchroneJar(String nameFile)// Non Bloquant
	{
		URL url = ClassLoader.getSystemResource(nameFile);
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		return new ImageIcon(image);
	}

	public static BufferedImage[] loadImagesFromFolder(String folderName) {
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		BufferedImage[] listOfImages = new BufferedImage[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					listOfImages[i] = ImageIO.read(listOfFiles[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listOfImages;
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

}
