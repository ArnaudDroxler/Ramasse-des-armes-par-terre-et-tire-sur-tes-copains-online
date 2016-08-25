package tests.kryonet.implem.sliders;

public class ClientConnexionMessage {

	private String pseudo;

	public ClientConnexionMessage(){
		
	}
	
	public ClientConnexionMessage(String pseudo){
		this.pseudo=pseudo;
	}

	public String getPseudo() {
		return pseudo;
	}
	
}
