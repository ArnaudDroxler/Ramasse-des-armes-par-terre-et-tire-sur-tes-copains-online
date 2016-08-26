package tests.kryonet.implem.premiere.server;

public class JoueurOnline {

	public String pseudo;
	public int vie;
	public double posx;
	public double posy;
	public int dirx;
	public int diry;
	private int connectionId;
	
	public JoueurOnline() {
		
	}

	public JoueurOnline(String pseudo, int connectionId) {
		this.pseudo=pseudo;
		this.connectionId=connectionId;
		posx=posy=20;
	}

	public String getPseudo() {
		return pseudo;
	}

	@Override
	public String toString() {
		return pseudo + ", vie=" + vie + ", pos=(" + posx + "," + posy + "), dir=(" + dirx
				+ "," + diry + ")";
	}
	
	public void setPosx(double posx2) {
		posx=posx2;		
	}

	public void setPosy(double posy2) {
		posy=posy2;		
	}
	
}
