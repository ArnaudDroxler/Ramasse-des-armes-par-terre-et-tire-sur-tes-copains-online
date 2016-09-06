package toutdansunpackage.server;

import java.util.ArrayList;
import java.util.HashMap;

import toutdansunpackage.thing.Thing;
import toutdansunpackage.tools.map.ImageParser;
import toutdansunpackage.tools.map.LvlMap;

public class LogiqueServer {

	private LvlMap map;
	private Partie partie;
	
	public LogiqueServer(String nomMap, Partie partie) {
		this.partie = partie;
		map = ImageParser.getMap(nomMap);

				
	}

	public void fireFromPlayer(int clientId) {
		System.out.println(clientId + " a fait feu\n" + partie);
	}

}
