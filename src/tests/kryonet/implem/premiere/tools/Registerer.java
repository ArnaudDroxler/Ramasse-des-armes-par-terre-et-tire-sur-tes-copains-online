package tests.kryonet.implem.premiere.tools;

import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import base.thing.Thing;
import multi.thing.personnage.Joueur;
import multi.thing.personnage.Personnage;
import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.premiere.messages.AcceptClientMessage;
import tests.kryonet.implem.premiere.messages.ClientConnexionMessage;
import tests.kryonet.implem.premiere.messages.ClientUpdateMessage;
import tests.kryonet.implem.premiere.server.JoueurOnline;
import tests.kryonet.implem.premiere.server.Partie;

public class Registerer {

	public static void registerFor(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		// Messages
		kryo.register(ClientConnexionMessage.class);
		kryo.register(ClientUpdateMessage.class);
		kryo.register(AcceptClientMessage.class);
		kryo.register(Partie.class);
		
		// Joueur
		kryo.register(JoueurOnline.class);
		kryo.register(Joueur.class);
		kryo.register(Personnage.class);
		kryo.register(Thing.class);
		kryo.register(Vector2D.class);

		kryo.register(HashMap.class);
		
	}

}
