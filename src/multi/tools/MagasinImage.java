
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
	
//	public static final BufferedImage buffTextMur0 = ImageLoader.loadBufferedImage("texturemur0.png");
//	public static final BufferedImage buffTextMur1 = ImageLoader.loadBufferedImage("texturemur1.png");
//	public static final BufferedImage buffTextMur2 = ImageLoader.loadBufferedImage("texturemur2.png");
//	public static final BufferedImage buffTextMur3 = ImageLoader.loadBufferedImage("texturemur3.png");
//	public static final BufferedImage buffTextMur4 = ImageLoader.loadBufferedImage("texturemur4.png");
//	
//	public static final BufferedImage buffTextMur[] = {buffTextMur0,buffTextMur1,buffTextMur2,buffTextMur3,buffTextMur4,buffTextMur4,buffTextMur4,buffTextMur4};
	
	public static final BufferedImage buffTextMur[] = ImageLoader.loadImagesFromFolder("yolo");

	
	public static final BufferedImage buffHudLeft = ImageLoader.loadBufferedImage("hudleft.png");
	public static final BufferedImage buffHudRight = ImageLoader.loadBufferedImage("hudright.png");

	public static final BufferedImage buffHandGun = ImageLoader.loadBufferedImage("weapon/handgun/handgun.png");
	public static final BufferedImage buffShootGun = ImageLoader.loadBufferedImage("weapon/shootgun/shootgun.png");
	public static final BufferedImage buffSubMachinGun = ImageLoader.loadBufferedImage("weapon/submachingun/submachingun.png");
	public static final BufferedImage buffAssaltRifle = ImageLoader.loadBufferedImage("weapon/assaltrifle/assaltrifle.png");
	public static final BufferedImage buffPrecisionRifle = ImageLoader.loadBufferedImage("weapon/precisionrifle/precisionrifle.png");
	public static final BufferedImage buffChainsaw = ImageLoader.loadBufferedImage("weapon/chainsaw/chainsaw.png");
	
	public static final BufferedImage buffAmmoPackHG = ImageLoader.loadBufferedImage("ammopackHG.png");
	public static final BufferedImage buffAmmoPackSmG = ImageLoader.loadBufferedImage("ammopackSmG.png");
	public static final BufferedImage buffAmmoPackCS = ImageLoader.loadBufferedImage("weapon/chainsaw/ammopackcs.png");

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
	
	public static final BufferedImage buffPika[] = ImageLoader.loadImagesFromFolder("pika");

	public static final BufferedImage buffChainsawHUD0 = ImageLoader.loadBufferedImage("weapon/chainsaw/chainsawhud0.png");
	public static final BufferedImage buffChainsawHUD1 = ImageLoader.loadBufferedImage("weapon/chainsaw/chainsawhud1.png");
	public static final BufferedImage buffChainsawHUD2 = ImageLoader.loadBufferedImage("weapon/chainsaw/chainsawhud2.png");
	public static final BufferedImage buffChainsawHUD3 = ImageLoader.loadBufferedImage("weapon/chainsaw/chainsawhud3.png");
	
	public final static BufferedImage buffChainsawHUD[] = { buffChainsawHUD0,buffChainsawHUD1,buffChainsawHUD2,buffChainsawHUD3};
	
	public static final BufferedImage buffSubmachingunHUD0 = ImageLoader.loadBufferedImage("weapon/submachingun/submachingunhud0.png");
	public static final BufferedImage buffSubmachingunHUD1 = ImageLoader.loadBufferedImage("weapon/submachingun/submachingunhud1.png");
	
	public final static BufferedImage buffSubmachingunHUD[] = { buffSubmachingunHUD0,buffSubmachingunHUD1};
	
	public static final BufferedImage buffShootgunHUD0 = ImageLoader.loadBufferedImage("weapon/shootgun/shootgunhud0.png");
	public static final BufferedImage buffShootgunHUD1 = ImageLoader.loadBufferedImage("weapon/shootgun/shootgunhud1.png");
	public static final BufferedImage buffShootgunHUD2 = ImageLoader.loadBufferedImage("weapon/shootgun/shootgunhud2.png");
	public static final BufferedImage buffShootgunHUD3 = ImageLoader.loadBufferedImage("weapon/shootgun/shootgunhud3.png");
	public static final BufferedImage buffShootgunHUD4 = ImageLoader.loadBufferedImage("weapon/shootgun/shootgunhud4.png");
	public static final BufferedImage buffShootgunHUD5 = ImageLoader.loadBufferedImage("weapon/shootgun/shootgunhud5.png");
	
	public final static BufferedImage buffShootgunHUD[] = { buffShootgunHUD0,buffShootgunHUD1,buffShootgunHUD2,buffShootgunHUD3,buffShootgunHUD4,buffShootgunHUD5,buffShootgunHUD4,buffShootgunHUD3};
	
	public static final BufferedImage buffAssaltRifleHUD0 = ImageLoader.loadBufferedImage("weapon/assaltrifle/assaltriflehud0.png");
	public static final BufferedImage buffAssaltRifleHUD1 = ImageLoader.loadBufferedImage("weapon/assaltrifle/assaltriflehud1.png");
	public static final BufferedImage buffAssaltRifleHUD2 = ImageLoader.loadBufferedImage("weapon/assaltrifle/assaltriflehud2.png");
	
	public final static BufferedImage buffAssaltRifleHUD[] = { buffAssaltRifleHUD0,buffAssaltRifleHUD1,buffAssaltRifleHUD2};
	
	public static final BufferedImage buffPrecisionRifleHUD0 = ImageLoader.loadBufferedImage("weapon/precisionrifle/precisionriflehud0.png");
	public static final BufferedImage buffPrecisionRifleHUD1 = ImageLoader.loadBufferedImage("weapon/precisionrifle/precisionriflehud1.png");
	public static final BufferedImage buffPrecisionRifleHUD2 = ImageLoader.loadBufferedImage("weapon/precisionrifle/precisionriflehud2.png");
	public static final BufferedImage buffPrecisionRifleHUD3 = ImageLoader.loadBufferedImage("weapon/precisionrifle/precisionriflehud3.png");
	
	public final static BufferedImage buffPrecisionRifleHUD[] = { buffPrecisionRifleHUD0,buffPrecisionRifleHUD1,buffPrecisionRifleHUD2,buffPrecisionRifleHUD3,buffPrecisionRifleHUD2,buffPrecisionRifleHUD1};

	
	
	/*------------------------------------------------------------------*\
	|*		Version Assynchrone	(non bloquant)							*|
	\*------------------------------------------------------------------*/

}
