package toutdansunpackage.server;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import base.FenetreJeu;
import toutdansunpackage.client.JFrameConnexion;
import toutdansunpackage.client.PcClient;
import toutdansunpackage.tools.JPanelDecorator;
import toutdansunpackage.tools.JPanelTemps;
import toutdansunpackage.tools.SpringUtilities;

public class JFrameConfiguration extends JFrame {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameConfiguration() {
		fc.setDialogTitle("Choisissez une carte, cliquez sur \"Annuler\" pour charger la map par défaut");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map images", "png", "jpg", "gif");
		fc.setFileFilter(filter);

		geometry();
		control();
		appearance();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {
		JPanel formPanel = new JPanel(new SpringLayout());

		// Configuration des JSpinner
		int valMin = 2;
		int valMax = 12;

		SpinnerModel modelNbJoueur = new SpinnerNumberModel(2, valMin, valMax, 1);

		spinNbJoueur = new JSpinner(modelNbJoueur);

		panChoixTemps = new JPanelTemps();
		btnChoixMap = new JButton("Map");
		btnLancer = new JButton("Démarrer");
		btnAnnuler = new JButton("Annuler");

		JLabel lblChoixMap = new JLabel("Choix de la map : ", JLabel.TRAILING);
		formPanel.add(lblChoixMap);
		lblChoixMap.setLabelFor(btnChoixMap);
		formPanel.add(btnChoixMap);

		JLabel lblNbJoueur = new JLabel("Maximum de joueurs : ", JLabel.TRAILING);
		formPanel.add(lblNbJoueur);
		lblNbJoueur.setLabelFor(spinNbJoueur);
		formPanel.add(spinNbJoueur);

		JLabel lblTempsPartie = new JLabel("Temps de la partie : ", JLabel.TRAILING);
		formPanel.add(lblTempsPartie);
		lblTempsPartie.setLabelFor(panChoixTemps);
		formPanel.add(panChoixTemps);

		formPanel.add(btnLancer);
		formPanel.add(btnAnnuler);

		SpringUtilities.makeCompactGrid(formPanel, 4, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		JPanel panel = new JPanel();
		panel.add(formPanel);

		setContentPane(new JPanelDecorator(panel, 10));

		mapPath = new String("sprite/map/StandDeTire");
		args = new String[3];
	}

	private void control() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getRootPane().setDefaultButton(btnLancer);

		btnAnnuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnLancer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				savePreferences();
				args[0] = mapPath;
				args[1] = spinNbJoueur.getValue() + "";
				args[2] = panChoixTemps.getTimeMillis();
				new PcServer(args);
				dispose();

			}
		});

		btnChoixMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showOpenDialog(JFrameConfiguration.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					mapPath = fc.getSelectedFile().getAbsolutePath();
					// Suppression du .png à la fin du chemin
					mapPath = mapPath.substring(0, mapPath.length() - 4);
					System.out.println(mapPath);
				}
			}

		});
	}

	private void appearance() {
		setSize(350, 200);
		setResizable(false);
		setTitle("Configuration");

		loadPreferences();
		setVisible(true); // last!
	}

	private void savePreferences() {
		PREFERENCES.putInt("px", (int) this.getLocation().getX());
		PREFERENCES.putInt("py", (int) this.getLocation().getY());
		PREFERENCES.putInt("minutes", panChoixTemps.getMinute());
		PREFERENCES.putInt("secondes", panChoixTemps.getSecondes());
		PREFERENCES.putInt("nbJoueurs", (int) spinNbJoueur.getValue());
	}

	private void loadPreferences() {
		int px = PREFERENCES.getInt("px", 0);
		int py = PREFERENCES.getInt("py", 0);
		if (px + py == 0) {
			setLocationRelativeTo(null); // frame centrer
		} else {
			setLocation(px, py);
		}

		int minutes = PREFERENCES.getInt("minutes", 0);
		int secondes = PREFERENCES.getInt("secondes", 0);
		int joueurs = PREFERENCES.getInt("nbJoueurs", 2);

		panChoixTemps.setMinute(minutes);
		panChoixTemps.setSeconde(secondes);
		spinNbJoueur.setValue(joueurs);

	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools

	private JSpinner spinNbJoueur;
	private JPanelTemps panChoixTemps;
	private JButton btnChoixMap;

	private String mapPath;

	private JButton btnLancer;
	private JButton btnAnnuler;

	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(JFrameConfiguration.class);
	private String[] args;

	private final JFileChooser fc = new JFileChooser();

}
