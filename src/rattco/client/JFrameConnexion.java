package rattco.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import rattco.tools.JPanelDecorator;
import rattco.tools.SpringUtilities;

/**
 * Cette classe affiche une boite de dialogue pour le client
 * qui permet de se connecter � un serveur en fournissant un 
 * nom d'h�te et un pseudo.
 * Le client peut aussi choisir la r�solution du jeu en fournissant
 * une hauteur en pixel, la largeur est calcul�e pour garantir un rapport
 * 16/9 et ainsi �viter de d�former les objets
 */
public class JFrameConnexion extends JFrame {

	private static final long serialVersionUID = 7051290589750396629L;
	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(JFrameConnexion.class);

	private JTextField tfIp;
	private JTextField tfPseudo;
	private JFormattedTextField tfReso;
	private JButton btnConnexion;


	public JFrameConnexion() {
		geometry();
		control();
		appearance();
	}

	/**
	 * Construction des composants swing.
	 * Pour la mise en page en formulaire, on utilise la classe
	 * SpringUtilities propos�es dans les exemples de java :
	 * https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringGridProject/src/layout/SpringUtilities.java
	 */
	private void geometry() {
		JPanel formPanel = new JPanel(new SpringLayout());

		tfIp = new JTextField(12);
		tfPseudo = new JTextField();
		// Gr�ce au JFormattedTextField, ce champ ne pourra contenir que des entiers
		tfReso = new JFormattedTextField(400);
		btnConnexion = new JButton("Rejoindre");
		
		JLabel lblAdIp = new JLabel("Adresse du serveur : ", JLabel.TRAILING);
		formPanel.add(lblAdIp);
		lblAdIp.setLabelFor(tfIp);
		formPanel.add(tfIp);

		JLabel lblPseudo = new JLabel("Votre pseudo : ", JLabel.TRAILING);
		formPanel.add(lblPseudo);
		lblPseudo.setLabelFor(tfPseudo);
		formPanel.add(tfPseudo);

		JLabel lblReso = new JLabel("R�solution (hauteur) : ", JLabel.TRAILING);
		formPanel.add(lblReso);
		lblPseudo.setLabelFor(tfReso);
		formPanel.add(tfReso);
		
		SpringUtilities.makeCompactGrid(formPanel,
				3, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad
		
		JPanel panel = new JPanel();
		panel.add(formPanel);
		panel.add(btnConnexion);
		JLabel lblExplications = new JLabel(""
				+ "<html><h3>Contr�les :</h3>"
				+ "W,A,S,D pour se d�placer<br>"
				+ "E pour ramasser une arme<br>"
				+ "Q pour afficher le tableau des scores<br>"
				+ "ESC pour lib�rer la souris");
		lblExplications.setPreferredSize(new Dimension(300, 100));
		panel.add(lblExplications);

		// Le d�corateur rajoute simplement une bordure au panel
		setContentPane(new JPanelDecorator(panel, 10));
	}

	/**
	 * Ici on g�re l'�v�nemnt clic sur le bouton Connexion
	 * La m�thode connect se fait dans un autre thread pour
	 * �viter que l'affichage se fige au moment de la connexion
	 */
	private void control() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getRootPane().setDefaultButton(btnConnexion);

		btnConnexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savePreferences();
				Thread threadConnect = new Thread(new Runnable() {

					@Override
					public void run() {
						btnConnexion.setText("Connexion en cours");
						btnConnexion.setEnabled(false);
						connect();
						btnConnexion.setText("Rejoindre");
						btnConnexion.setEnabled(true);
					}
				});
				threadConnect.start();
			}
		});
	}

	private void appearance() {
		setSize(350, 280);
		setResizable(false);
		setTitle("Connexion");
		tfIp.selectAll();

		loadPreferences();
		setVisible(true); // last!
	}

	/**
	 * On instancie un nouveau PcClient qui va tenter de se connecter au serveur,
	 * si �a �choue, PcClient va �mettre une IOException, on affiche simplement un
	 * message d'erreur dans la console
	 */
	protected void connect(){
		try {
			new PcClient(tfIp.getText(), tfPseudo.getText(), Integer.valueOf(tfReso.getText()));
			// dispose(); // close the JFrame
		} catch (IOException e) {
			System.err.println("Serveur inaccessible");
		}
	}

	/**
	 * Ces 2 m�thodes nous permettent de garder les m�mes pr�f�rences
	 * d'une �x�cution du programme � l'autre
	 */
	private void savePreferences() {
		PREFERENCES.putInt("px", (int) this.getLocation().getX());
		PREFERENCES.putInt("py", (int) this.getLocation().getY());
		PREFERENCES.put("ip", tfIp.getText());
		PREFERENCES.put("pseudo", tfPseudo.getText());
		PREFERENCES.put("reso", tfReso.getText());
	}

	private void loadPreferences() {
		int px = PREFERENCES.getInt("px", 0);
		int py = PREFERENCES.getInt("py", 0);
		if (px + py == 0) {
			setLocationRelativeTo(null); // frame centrer
		} else {
			setLocation(px, py);
		}

		String ip = PREFERENCES.get("ip", "");
		String pseudo = PREFERENCES.get("pseudo", "");
		String reso = PREFERENCES.get("reso", "320");
		tfIp.setText(ip);
		tfPseudo.setText(pseudo);
		tfReso.setText(reso);
		tfPseudo.selectAll();
		tfIp.selectAll();
		tfReso.selectAll();

	}

}
