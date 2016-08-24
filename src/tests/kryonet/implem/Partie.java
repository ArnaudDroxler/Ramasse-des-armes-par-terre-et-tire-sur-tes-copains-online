package tests.kryonet.implem;

import java.util.HashMap;
import java.util.HashSet;

public class Partie {

	private HashMap<String,Joueur> joueurs;
	private String nomMap;
	
	public Partie(){
		joueurs = new HashMap<String,Joueur>();
	}
	
	public void ajouterJoueur(String pseudo) {
		joueurs.put(pseudo, new Joueur(pseudo));
	}

	public void updateJoueur(Joueur j) {
		joueurs.put(j.getPseudo(), j);
	}
	
}
