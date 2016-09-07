package toutdansunpackage.server;

import java.util.HashMap;

import toutdansunpackage.messages.PlayerUpdateMessage;

public class Partie {

	private HashMap<Integer, JoueurOnline> joueurs;
	private long tempsSecondes;
	protected int nbBytesSent;
	private long tempsRestantSecondes;
	private int nombreJoueurs;
	private PcServer pcServer;

	public Partie(String nomMap) {
		joueurs = new HashMap<Integer, JoueurOnline>();
	}

	public Partie() {
	}

	public Partie(String nomMap, int nbJoueurs, int tempsMax, PcServer server) {
		joueurs = new HashMap<Integer, JoueurOnline>();
		tempsRestantSecondes = tempsMax;
		nombreJoueurs = nbJoueurs;
		pcServer = server;
	}

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
		sb.append("Temps : " + tempsSecondes / 60 + " min " + tempsSecondes % 60 + " s");
		sb.append("\nJoueurs :");
		for (JoueurOnline j : joueurs.values()) {
			sb.append("\n\t" + j);
		}
		sb.append("\nNb Octets envoyés : " + nbBytesSent);
		return sb.toString();
	}

	public HashMap<Integer, JoueurOnline> getJoueurs() {
		return joueurs;
	}

	public void setTempsSecondes(long l) {
		tempsSecondes = l;
	}

	public boolean isTempsFini() {
		if (tempsRestantSecondes == tempsSecondes)
			return true;
		else
			return false;
	}

}
