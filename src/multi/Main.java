package multi;

import java.awt.AWTException;

import multi.FenetreJeu;

public class Main {

	public static void main(String[] args) {
		try {
			new FenetreJeu("testRespawn.png");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
