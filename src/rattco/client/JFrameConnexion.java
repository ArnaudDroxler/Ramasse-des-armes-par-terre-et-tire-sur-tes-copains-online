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

public class JFrameConnexion extends JFrame {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 7051290589750396629L;

	public JFrameConnexion() {
		geometry();
		control();
		appearance();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	
	private void geometry() {
		JPanel formPanel = new JPanel(new SpringLayout());

		tfIp = new JTextField(12);
		tfPseudo = new JTextField();
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

		JLabel lblReso = new JLabel("Résolution (hauteur) : ", JLabel.TRAILING);
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
		JLabel lblExplications = new JLabel("<html><h3>Contrôles</h3>W,A,S,D pour se déplacer<br>E pour ramasser une arme<br>Q pour afficher le tableau des scores");
		lblExplications.setPreferredSize(new Dimension(300, 90));
		panel.add(lblExplications);

		setContentPane(new JPanelDecorator(panel, 10));
	}

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
		setSize(350, 270);
		setResizable(false);
		setTitle("Connexion");
		tfIp.selectAll();

		loadPreferences();
		setVisible(true); // last!
	}

	protected void connect(){
		try {
			new PcClient(tfIp.getText(), tfPseudo.getText(), Integer.valueOf(tfReso.getText()));
			// dispose(); // close the JFrame
		} catch (IOException e) {
			System.err.println("Serveur inaccessible");
			//e.printStackTrace();
		}
	}

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

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools

	private JTextField tfIp;
	private JTextField tfPseudo;
	private JFormattedTextField tfReso;

	private JButton btnConnexion;

	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(JFrameConnexion.class);

}
