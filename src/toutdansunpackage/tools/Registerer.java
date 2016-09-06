package toutdansunpackage.tools;

import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import toutdansunpackage.thing.Thing;
import toutdansunpackage.thing.personnage.Joueur;
import toutdansunpackage.thing.personnage.Personnage;
import toutdansunpackage.thing.weapon.AssaultRifle;
import toutdansunpackage.thing.weapon.Chainsaw;
import toutdansunpackage.thing.weapon.HandGun;
import toutdansunpackage.thing.weapon.PrecisionRifle;
import toutdansunpackage.thing.weapon.ShootGun;
import toutdansunpackage.thing.weapon.SubmachineGun;
import toutdansunpackage.thing.weapon.Weapon;
import toutdansunpackage.tools.raycasting.Vector2D;
import toutdansunpackage.messages.AcceptClientMessage;
import toutdansunpackage.messages.ClientConnexionMessage;
import toutdansunpackage.messages.PickUpMessage;
import toutdansunpackage.messages.PlayerUpdateMessage;
import toutdansunpackage.server.JoueurOnline;
import toutdansunpackage.server.Partie;

public class Registerer {

	public static void registerFor(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		// Messages
		kryo.register(ClientConnexionMessage.class);
		kryo.register(PlayerUpdateMessage.class);
		kryo.register(AcceptClientMessage.class);
		kryo.register(PickUpMessage.class);
		kryo.register(Partie.class);
		
		// Joueur
		kryo.register(JoueurOnline.class);
		kryo.register(Joueur.class);
		kryo.register(Personnage.class);
		kryo.register(Thing.class);
		kryo.register(Vector2D.class);

		kryo.register(HashMap.class);

		// Armes (seulement parce que l'arme est un attribut de la classe Personnage)
		kryo.register(Weapon.class);
		kryo.register(HandGun.class);
		kryo.register(AssaultRifle.class);
		kryo.register(Chainsaw.class);
		kryo.register(PrecisionRifle.class);
		kryo.register(ShootGun.class);
		kryo.register(SubmachineGun.class);
		
	}

}
