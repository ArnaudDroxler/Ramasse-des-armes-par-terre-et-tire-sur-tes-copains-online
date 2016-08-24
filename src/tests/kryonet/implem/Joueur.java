package tests.kryonet.implem;

public class Joueur {

	private String pseudo;
	private int vie;
	private int posx;
	private int posy;
	private int dirx;
	private int diry;
	
	public Joueur(String pseudo, int vie, int posx, int posy, int dirx, int diry) {
		this.pseudo = pseudo;
		this.vie = vie;
		this.posx = posx;
		this.posy = posy;
		this.dirx = dirx;
		this.diry = diry;
	}
	
	public Joueur(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public Joueur() {
		
	}

	public String getPseudo() {
		return pseudo;
	}
	
}
