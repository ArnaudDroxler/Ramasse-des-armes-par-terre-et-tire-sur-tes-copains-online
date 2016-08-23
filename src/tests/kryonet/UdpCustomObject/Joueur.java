package tests.kryonet.UdpCustomObject;

public class Joueur{
	private int posx;
	private int posy;
	private String pseudo;
	
	public Joueur(String p, int x, int y){
		this.posx = x;
		posy=y;
		pseudo=p;
	}
	
	public Joueur(){
		
	}

	@Override
	public String toString() {
		return "Joueur [posx=" + posx + ", posy=" + posy + ", pseudo=" + pseudo + "]";
	}
	
}
