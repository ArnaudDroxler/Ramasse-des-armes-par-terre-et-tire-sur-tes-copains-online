package clientserver;

import java.awt.AWTException;

import clientserver.FenetreJeu;

public class Main {

	public static void main(String[] args) {
		try {
			new FenetreJeu("faqfinouWorld.png");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
