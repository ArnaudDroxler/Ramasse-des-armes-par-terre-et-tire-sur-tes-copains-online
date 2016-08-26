package tests.kryonet.implem.premiere.server;

public class JoueurOnline {

	private String pseudo;
	private int vie;
	public double posx;
	public double posy;
	private int dirx;
	private int diry;
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

	public void setPos(double d) {
		posx=d;		
	}
	
}
