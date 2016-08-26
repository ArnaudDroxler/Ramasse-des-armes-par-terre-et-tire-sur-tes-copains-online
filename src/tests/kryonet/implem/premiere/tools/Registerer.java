package tests.kryonet.implem.premiere.tools;

import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import tests.kryonet.implem.premiere.messages.AcceptClientMessage;
import tests.kryonet.implem.premiere.messages.ClientConnexionMessage;
import tests.kryonet.implem.premiere.messages.ClientUpdateMessage;
import tests.kryonet.implem.premiere.server.Joueur;
import tests.kryonet.implem.premiere.server.Partie;

public class Registerer {

	public static void registerFor(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(ClientConnexionMessage.class);
		kryo.register(Joueur.class);
		kryo.register(ClientUpdateMessage.class);
		kryo.register(AcceptClientMessage.class);
		kryo.register(Partie.class);
		kryo.register(HashMap.class);
	}

}
