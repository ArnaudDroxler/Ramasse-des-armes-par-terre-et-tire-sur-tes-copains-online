package rattco.tools;

import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import rattco.messages.AcceptClientMessage;
import rattco.messages.ClientConnexionMessage;
import rattco.messages.DamageMessage;
import rattco.messages.FireMessage;
import rattco.messages.KillMessage;
import rattco.messages.PickUpMessage;
import rattco.messages.PlayerUpdateMessage;
import rattco.server.JoueurOnline;
import rattco.server.Partie;
import rattco.thing.Thing;
import rattco.thing.personnage.Joueur;
import rattco.thing.personnage.Personnage;
import rattco.thing.weapon.AssaultRifle;
import rattco.thing.weapon.Axe;
import rattco.thing.weapon.Chainsaw;
import rattco.thing.weapon.HandGun;
import rattco.thing.weapon.PrecisionRifle;
import rattco.thing.weapon.ShootGun;
import rattco.thing.weapon.SubmachineGun;
import rattco.thing.weapon.Weapon;
import rattco.tools.raycasting.Vector2D;

public class Registerer {

	public static void registerFor(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		// Messages
		kryo.register(ClientConnexionMessage.class);
		kryo.register(PlayerUpdateMessage.class);
		kryo.register(AcceptClientMessage.class);
		kryo.register(PickUpMessage.class);
		kryo.register(FireMessage.class);
		kryo.register(KillMessage.class);
		kryo.register(DamageMessage.class);
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
		kryo.register(Axe.class);
		
	}

}
