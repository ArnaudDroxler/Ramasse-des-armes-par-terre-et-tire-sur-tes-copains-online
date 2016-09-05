package tests.kryonet.implem.logiqueCoteClient.server;

import java.util.ArrayList;
import java.util.HashMap;

import multi.thing.Thing;
import multi.tools.map.ImageParser;
import multi.tools.map.LvlMap;

public class LogiqueServer {

	private LvlMap map;
	private HashMap<Integer, Thing> mapThings;
	private Partie partie;
	
	public LogiqueServer(String nomMap, Partie partie) {
		this.partie = partie;
		
		map = ImageParser.getMap(nomMap);
		ArrayList<Thing> listThing = map.getListThing();
		
		mapThings = new HashMap<Integer, Thing>();
		for(int i=0; i<listThing.size(); i++){
			mapThings.put(i, listThing.get(i));
		}
		
		
	}

}
