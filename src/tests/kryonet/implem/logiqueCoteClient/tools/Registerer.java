package tests.kryonet.implem.logiqueCoteClient.tools;

import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import multi.thing.Thing;
import multi.thing.personnage.Joueur;
import multi.thing.personnage.Personnage;
import multi.thing.weapon.AssaultRifle;
import multi.thing.weapon.Chainsaw;
import multi.thing.weapon.HandGun;
import multi.thing.weapon.PrecisionRifle;
import multi.thing.weapon.ShootGun;
import multi.thing.weapon.SubmachineGun;
import multi.thing.weapon.Weapon;
import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.logiqueCoteClient.messages.AcceptClientMessage;
import tests.kryonet.implem.logiqueCoteClient.messages.ClientConnexionMessage;
import tests.kryonet.implem.logiqueCoteClient.messages.PickUpMessage;
import tests.kryonet.implem.logiqueCoteClient.messages.PlayerUpdateMessage;
import tests.kryonet.implem.logiqueCoteClient.server.JoueurOnline;
import tests.kryonet.implem.logiqueCoteClient.server.Partie;

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
