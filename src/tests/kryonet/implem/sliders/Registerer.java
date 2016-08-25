package tests.kryonet.implem.sliders;

import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

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