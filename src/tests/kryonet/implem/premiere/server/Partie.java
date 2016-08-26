package tests.kryonet.implem.premiere.server;

import java.util.HashMap;

import tests.kryonet.implem.premiere.messages.ClientUpdateMessage;

public class Partie {

	private HashMap<Integer,Joueur> joueurs;
	private String nomMap;
	
	public Partie(){
		joueurs = new HashMap<Integer,Joueur>();
	}

	public void updateJoueur(int id, ClientUpdateMessage cum) {
		Joueur j = joueurs.get(id);
		j.setPos(cum.getPos());
	}

	public void addJoueur(int id, Joueur nouveaujoueur) {
		joueurs.put(id, nouveaujoueur);
	}

	public void removeJoueur(int id) {
		joueurs.remove(id);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("map : ");
		sb.append(nomMap+"\n");
		sb.append("Joueurs :");
		for (Joueur j : joueurs.values()) {
			sb.append("\n\t" + j);
		}
		return sb.toString();
	}
	
}
