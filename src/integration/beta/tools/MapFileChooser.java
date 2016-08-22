package integration.beta.tools;

import java.awt.AWTException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import integration.beta.FenetreJeu;

public class MapFileChooser extends JFrame {

	public MapFileChooser() {
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Choisissez une carte, cliquez sur \"Annuler\" pour charger la map par défaut");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map images", "png", "jpg", "gif");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(this);

		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				new FenetreJeu(fc.getSelectedFile().getAbsolutePath());
			} else {
				new FenetreJeu("faqfinouWorld.png");
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
