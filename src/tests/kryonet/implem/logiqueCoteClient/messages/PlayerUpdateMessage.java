package tests.kryonet.implem.logiqueCoteClient.messages;

import tests.kryonet.implem.logiqueCoteClient.server.JoueurOnline;

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
