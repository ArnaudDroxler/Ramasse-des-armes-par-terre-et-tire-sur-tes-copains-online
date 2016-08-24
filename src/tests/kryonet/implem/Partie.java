package tests.kryonet.implem;

import java.util.HashMap;
import java.util.HashSet;

public class Partie {

	private HashMap<Integer,Joueur> joueurs;
	private String nomMap;
	
	public Partie(){
		joueurs = new HashMap<Integer,Joueur>();
	}

	public void updateJoueur(int id, Joueur j) {
		joueurs.put(id, j);
	}

	public void removeJoueur(int id) {
		joueurs.remove(id);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("map :");
		sb.append(nomMap+"\n");
		sb.append("Joueurs :\n");
		for (Joueur j : joueurs.values()) {
			sb.append(j + "\n");
		}
		return sb.toString();
	}
	
}
