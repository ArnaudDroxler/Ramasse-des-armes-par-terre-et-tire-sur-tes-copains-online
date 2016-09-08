package toutdansunpackage.thing.weapon;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import toutdansunpackage.thing.Thing;
import toutdansunpackage.tools.MagasinImage;
import toutdansunpackage.tools.raycasting.Vector2D;

public class AmmoPack extends Thing {
	
	private static final TreeMap<String, BufferedImage> buffsSprite = new TreeMap<String, BufferedImage>();
	static {
		buffsSprite.put("AssaultRifle", MagasinImage.buffAssaultRifle[1]);
		buffsSprite.put("Chainsaw", MagasinImage.buffChainsaw[1]);
		buffsSprite.put("HandGun", MagasinImage.buffHandGun[1]);
		buffsSprite.put("PrecisionRifle", MagasinImage.buffPrecisionRifle[1]);
		buffsSprite.put("ShootGun", MagasinImage.buffShootGun[1]);
		buffsSprite.put("SubmachineGun", MagasinImage.buffSubMachinGun[1]);
	}

	private String WeaponSelected;

	public AmmoPack(Vector2D pos, String i) {
		super(pos);
		WeaponSelected = i;
	}

	public String getAmmoType() {
		return WeaponSelected;
	}

	@Override
	public BufferedImage getSprite() {
		return buffsSprite.get(WeaponSelected);
	}
}
