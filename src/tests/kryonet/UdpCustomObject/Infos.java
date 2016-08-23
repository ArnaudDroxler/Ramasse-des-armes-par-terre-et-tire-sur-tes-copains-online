package tests.kryonet.UdpCustomObject;

import java.util.ArrayList;

public class Infos {
	private String texte;
	private ArrayList<Joueur> joueurs;
	
	public Infos(String texte) {
		this.texte = texte;
		joueurs=new ArrayList<Joueur>();
	}

	public Infos() {
		
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	public void addJoueur(Joueur j){
		joueurs.add(j);
	}

	@Override
	public String toString() {
		return "Infos [texte=" + texte + ", joueur=" + joueurs + "]";
	}	
}
