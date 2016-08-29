
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

	public static final BufferedImage buffYoanBlanc = ImageLoader.loadBufferedImage("yoan00.png");
	public static final BufferedImage buffCle = ImageLoader.loadBufferedImage("cle.png");
	public static final BufferedImage buffPorte = ImageLoader.loadBufferedImage("porte.png");
	public static final BufferedImage buffMedipack = ImageLoader.loadBufferedImage("medipack.png");
	public static final BufferedImage buffArmure = ImageLoader.loadBufferedImage("armure.png");
	
	
	public static final BufferedImage buffHandGun = ImageLoader.loadBufferedImage("weapon/testgun.png");
	public static final BufferedImage buffShootGun = ImageLoader.loadBufferedImage("weapon/shootgun.png");
	public static final BufferedImage buffSubMachinGun = ImageLoader.loadBufferedImage("weapon/submachingun.png");
	public static final BufferedImage buffPrecisionRifle = ImageLoader.loadBufferedImage("weapon/sniper.png");
	public static final BufferedImage buffChainsaw = ImageLoader.loadBufferedImage("weapon/chainsaw/chainsaw.png");
	
	public static final BufferedImage buffAmmoPackHG = ImageLoader.loadBufferedImage("ammopackHG.png");
	public static final BufferedImage buffAmmoPackSmG = ImageLoader.loadBufferedImage("ammopackSmG.png");

	public static final BufferedImage buffFantome0 = ImageLoader.loadBufferedImage("images fantome/fantome0.png");
	public static final BufferedImage buffFantome1 = ImageLoader.loadBufferedImage("images fantome/fantome1.png");
	public static final BufferedImage buffFantome2 = ImageLoader.loadBufferedImage("images fantome/fantome2.png");
	public static final BufferedImage buffFantome3 = ImageLoader.loadBufferedImage("images fantome/fantome3.png");
	public static final BufferedImage buffFantome4 = ImageLoader.loadBufferedImage("images fantome/fantome4.png");
	public static final BufferedImage buffFantome5 = ImageLoader.loadBufferedImage("images fantome/fantome5.png");
	public static final BufferedImage buffFantome6 = ImageLoader.loadBufferedImage("images fantome/fantome6.png");
	public static final BufferedImage buffFantome7 = ImageLoader.loadBufferedImage("images fantome/fantome7.png");
	
	public static final BufferedImage buffFantomes[] = {
			buffFantome0,buffFantome1,buffFantome2,buffFantome3,buffFantome4,buffFantome5,buffFantome6,buffFantome7
		};

	/*------------------------------------------------------------------*\
	|*		Version Assynchrone	(non bloquant)							*|
	\*------------------------------------------------------------------*/

}
