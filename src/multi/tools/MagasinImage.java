
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
	public static final BufferedImage buffYoanBlanc = ImageLoader.loadBufferedImage("yoan00.png");
	public static final BufferedImage buffCle = ImageLoader.loadBufferedImage("cle.png");
	public static final BufferedImage buffPorte = ImageLoader.loadBufferedImage("porte.png");
	public static final BufferedImage buffMedipack = ImageLoader.loadBufferedImage("medipack.png");
	public static final BufferedImage buffArmure = ImageLoader.loadBufferedImage("armure.png");
	public static final BufferedImage buffHandGun = ImageLoader.loadBufferedImage("handgun2.png");
	public static final BufferedImage buffSubMachinGun = ImageLoader.loadBufferedImage("submachingun.png");
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

	public static final BufferedImage buffImpactEnnemi0 = ImageLoader.loadBufferedImage("images impact/impact_ennemi0.png");
	public static final BufferedImage buffImpactEnnemi1 = ImageLoader.loadBufferedImage("images impact/impact_ennemi1.png");
	public static final BufferedImage buffImpactEnnemi2 = ImageLoader.loadBufferedImage("images impact/impact_ennemi2.png");
	public static final BufferedImage buffImpactEnnemi3 = ImageLoader.loadBufferedImage("images impact/impact_ennemi3.png");

	public static final BufferedImage buffImpactMur0 = ImageLoader.loadBufferedImage("images impact/impact_mur0.png");
	public static final BufferedImage buffImpactMur1 = ImageLoader.loadBufferedImage("images impact/impact_mur1.png");
	public static final BufferedImage buffImpactMur2 = ImageLoader.loadBufferedImage("images impact/impact_mur2.png");
	public static final BufferedImage buffImpactMur3 = ImageLoader.loadBufferedImage("images impact/impact_mur3.png");
	public static final BufferedImage buffImpactMur4 = ImageLoader.loadBufferedImage("images impact/impact_mur4.png");
	
	public static final BufferedImage buffFantomes[] = { buffFantome0, buffFantome1, buffFantome2, buffFantome3,
			buffFantome4, buffFantome5, buffFantome6, buffFantome7 };

	/*------------------------------------------------------------------*\
	|*		Version Assynchrone	(non bloquant)							*|
	\*------------------------------------------------------------------*/

}
