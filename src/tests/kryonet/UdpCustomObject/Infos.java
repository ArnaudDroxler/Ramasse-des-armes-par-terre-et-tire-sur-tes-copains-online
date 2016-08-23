package tests.kryonet.UdpCustomObject;

import java.util.ArrayList;

public class Infos {
	private ArrayList<Joueur> joueurs;
	private String texte;
	
	public Infos(ArrayList<Joueur> joueurs, String texte) {
		this.joueurs = joueurs;
		this.texte = texte;
	}

	public Infos() {
		this.joueurs = new ArrayList<Joueur>();
	}

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	public void addJoueurs(Joueur joueur) {
		this.joueurs.add(joueur);
	}

	@Override
	public String toString() {
		return "Infos [joueurs=" + joueurs + "\nTexte=" + texte + "]";
	}	
}
