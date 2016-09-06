package toutdansunpackage.messages;

import toutdansunpackage.server.JoueurOnline;

public class PlayerUpdateMessage{

	private JoueurOnline joueur;
	
	public PlayerUpdateMessage(){}



	public void setJoueur(JoueurOnline joueur) {
		this.joueur = joueur;
	}



	public JoueurOnline getJoueur() {
		return joueur;
	}

}