package rattco.messages;

import rattco.server.Partie;

public class FinPartieMessage {

	private String mapSuivante;
	private Partie partie;

	public FinPartieMessage() {

	}

	public FinPartieMessage(String map, Partie part) {
		mapSuivante = map;
		partie = part;
	}

	public String getMap() {
		return mapSuivante;
	}

	public Partie getPartie() {
		return partie;
	}

}