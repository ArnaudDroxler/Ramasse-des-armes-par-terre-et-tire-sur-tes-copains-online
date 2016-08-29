package tests.kryonet.implem.logiqueCoteClient.server;

import java.util.HashMap;

import multi.tools.raycasting.Vector2D;
import tests.kryonet.implem.logiqueCoteClient.messages.PlayerUpdateMessage;

public class Partie{

	private HashMap<Integer,JoueurOnline> joueurs;
	private String nomMap;
	private long tempsSecondes;
	
	public Partie(){
		joueurs = new HashMap<Integer,JoueurOnline>();
		nomMap="StandDeTire.png";
	}

	public void updateJoueur(int id, PlayerUpdateMessage cum) {
		JoueurOnline j = joueurs.get(id);
		j.setPosition(cum.getPos());
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
		sb.append("Temps : " + tempsSecondes/60 + " min " + tempsSecondes%60 + " s");
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

	public void setTempsSecondes(long l) {
		tempsSecondes = l;
	}
	
}