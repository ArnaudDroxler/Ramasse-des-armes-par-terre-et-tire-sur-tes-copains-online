package tests.kryonet.implem.premiere.server;

import java.util.HashMap;

import tests.kryonet.implem.premiere.messages.ClientUpdateMessage;

public class Partie{

	private HashMap<Integer,JoueurOnline> joueurs;
	private String nomMap;
	private long t1,t2;
	private int tempsSecondes;
	
	public Partie(){
		joueurs = new HashMap<Integer,JoueurOnline>();
		nomMap="StandDeTire.png";
	}

	public void updateJoueur(int id, ClientUpdateMessage cum) {
		JoueurOnline j = joueurs.get(id);
		j.setPos(cum.getPos());
	}

	public void addJoueur(int id, JoueurOnline nouveaujoueur) {
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
		sb.append("Temps : " + tempsSecondes);
		sb.append("\nJoueurs :");
		for (JoueurOnline j : joueurs.values()) {
			sb.append("\n\t" + j);
		}
		return sb.toString();
	}
	
	public String getNomMap(){
		return nomMap;
	}

	public HashMap<Integer,JoueurOnline> getJoueurs() {
		return joueurs;
	}
	
}
