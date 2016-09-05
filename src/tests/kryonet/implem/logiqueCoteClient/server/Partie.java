package tests.kryonet.implem.logiqueCoteClient.server;

import java.util.HashMap;

import tests.kryonet.implem.logiqueCoteClient.messages.PlayerUpdateMessage;

public class Partie{

	private HashMap<Integer,JoueurOnline> joueurs;
	private long tempsSecondes;
	
	public Partie(String nomMap){
		joueurs = new HashMap<Integer,JoueurOnline>();
	}
	
	public Partie(){}

	public void updateJoueur(int id, PlayerUpdateMessage cum) {
		joueurs.put(id, cum.getJoueur());
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
		sb.append("Temps : " + tempsSecondes/60 + " min " + tempsSecondes%60 + " s");
		sb.append("\nJoueurs :");
		for (JoueurOnline j : joueurs.values()) {
			sb.append("\n\t" + j);
		}
		return sb.toString();
	}

	public HashMap<Integer,JoueurOnline> getJoueurs() {
		return joueurs;
	}

	public void setTempsSecondes(long l) {
		tempsSecondes = l;
	}
	
}
