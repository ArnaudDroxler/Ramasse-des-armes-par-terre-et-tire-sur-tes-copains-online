package rattco.server;

import java.util.HashMap;

import rattco.messages.PlayerUpdateMessage;
import rattco.thing.personnage.JoueurOnline;

public class Partie{

	private HashMap<Integer, JoueurOnline> joueurs;
	public int tempsSecondes;

	public Partie(){}
	
	public Partie(int tempsPartieSecondes){
		joueurs = new HashMap<Integer,JoueurOnline>();
		tempsSecondes = tempsPartieSecondes;
	}
	

	public void updateJoueur(int id, PlayerUpdateMessage pum) {
		joueurs.put(id, pum.getJoueur());
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

	public void setTempsSecondes(int l) {
		tempsSecondes = l;
	}


	public int getTemps() {
		return tempsSecondes;
	}
	
	public void setTemps(int t) {
		tempsSecondes=t;
	}
}
