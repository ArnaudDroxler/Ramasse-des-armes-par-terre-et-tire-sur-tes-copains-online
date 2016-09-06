
package toutdansunpackage.tools;

import java.awt.image.BufferedImage;


/**
 * Les images doivent se trouver dans un jar, et le jar dans le classpth! Le jar
 * doit contenir le folder ressources. A l'interieur du folder ressource doit se
 * trouver les images aux formats (jpg, voir mieux png pour la transparance)
 */
public class MagasinImage {

	/*------------------------------------------------------------------*\
	|*		 Version Synchrone (bloquant)								*|
	\*------------------------------------------------------------------*/
	public MagasinImage(){
		
	}
	
	//HUD
	public static final BufferedImage[] buffHud = ImageLoader.loadImagesFromFolder("sprite/hud");
	
	//WALL TEXT
	public static final BufferedImage[] buffTextMur = ImageLoader.loadImagesFromFolder("sprite/wall/yolo");
	
	//THING
	public static final BufferedImage[] buffMedipack = ImageLoader.loadImagesFromFolder("sprite/thing/medipack");
	public static final BufferedImage[] buffArmure = ImageLoader.loadImagesFromFolder("sprite/thing/armor");
	
	//CHARCTER
	public static final BufferedImage[] buffPika = ImageLoader.loadImagesFromFolder("sprite/thing/character/pika");
	
	//WEAPON
	public static final BufferedImage[] buffHandGun = ImageLoader.loadImagesFromFolder("sprite/thing/weapon/handgun");
	public static final BufferedImage[] buffShootGun = ImageLoader.loadImagesFromFolder("sprite/thing/weapon/shootgun");
	public static final BufferedImage[] buffSubMachinGun = ImageLoader.loadImagesFromFolder("sprite/thing/weapon/submachingun");
	public static final BufferedImage[] buffAssaltRifle = ImageLoader.loadImagesFromFolder("sprite/thing/weapon/assaltrifle");
	public static final BufferedImage[] buffPrecisionRifle = ImageLoader.loadImagesFromFolder("sprite/thing/weapon/precisionrifle");
	public static final BufferedImage[] buffChainsaw = ImageLoader.loadImagesFromFolder("sprite/thing/weapon/chainsaw");
	
	public static final BufferedImage[] buffChainsawAnimation = {buffChainsaw[2],buffChainsaw[3],buffChainsaw[4],buffChainsaw[5]};
	public static final BufferedImage[] buffPrecisionRifleAnimation  = {buffPrecisionRifle[2],buffPrecisionRifle[3],buffPrecisionRifle[4],buffPrecisionRifle[5],buffPrecisionRifle[4]};
	public static final BufferedImage[] buffShootGunAnimation  = {buffShootGun[2],buffShootGun[3],buffShootGun[4],buffShootGun[5],buffShootGun[6],buffShootGun[7],buffShootGun[6],buffShootGun[5]};
	
	//IMPACT
	public static final BufferedImage[] buffImpactEnnemi = ImageLoader.loadImagesFromFolder("sprite/impact/ennemie");
	public static final BufferedImage[] buffImpactMur = ImageLoader.loadImagesFromFolder("sprite/impact/mur");
	

}
