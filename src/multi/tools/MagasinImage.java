
package multi.tools;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * Les images doivent se trouver dans un jar, et le jar dans le classpth! Le jar
 * doit contenir le folder ressources. A l'interieur du folder ressource doit se
 * trouver les images aux formats (jpg, voir mieux png pour la transparance)
 */
public class MagasinImage {

	/*------------------------------------------------------------------*\
	|*		 Version Synchrone (bloquant)								*|
	\*------------------------------------------------------------------*/

	public static final ImageIcon pingouin = ImageLoader.loadSynchrone("penguin.png");
	public static final BufferedImage buffPingouin = ImageLoader.loadBufferedImage("penguin.png");
	public static final BufferedImage buffGiraffe = ImageLoader.loadBufferedImage("giraffe.png");
	public static final BufferedImage buffGoat = ImageLoader.loadBufferedImage("goat.png");
	public static final BufferedImage buffSheep = ImageLoader.loadBufferedImage("sheep.png");
	public static final BufferedImage buffYoanBlanc = ImageLoader.loadBufferedImage("yoan.png");
	public static final BufferedImage buffCle = ImageLoader.loadBufferedImage("cle.png");
	public static final BufferedImage buffPorte = ImageLoader.loadBufferedImage("porte.png");
	public static final BufferedImage buffMedipack = ImageLoader.loadBufferedImage("medipack.png");
	public static final BufferedImage buffArmure = ImageLoader.loadBufferedImage("armure.png");

	/*------------------------------------------------------------------*\
	|*		Version Assynchrone	(non bloquant)							*|
	\*------------------------------------------------------------------*/

}
