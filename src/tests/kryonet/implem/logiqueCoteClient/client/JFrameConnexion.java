package tests.kryonet.implem.logiqueCoteClient.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import tests.kryonet.implem.logiqueCoteClient.tools.JPanelDecorator;
import tests.kryonet.implem.logiqueCoteClient.tools.SpringUtilities;

public class JFrameConnexion extends JFrame {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

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
		btnConnexion = new JButton("Rejoindre");
		
		JLabel lblAdIp = new JLabel("Adresse du serveur : ", JLabel.TRAILING);
		formPanel.add(lblAdIp);
		lblAdIp.setLabelFor(tfIp);
		formPanel.add(tfIp);

		JLabel lblPseudo = new JLabel("Votre pseudo : ", JLabel.TRAILING);
		formPanel.add(lblPseudo);
		lblPseudo.setLabelFor(tfPseudo);
		formPanel.add(tfPseudo);
		
		SpringUtilities.makeCompactGrid(formPanel,
				2, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad
		
		JPanel panel = new JPanel();
		panel.add(formPanel);
		panel.add(btnConnexion);

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
		setSize(350, 150);
		setResizable(false);
		setTitle("Connexion");
		tfIp.selectAll();

		loadPreferences();
		setVisible(true); // last!
	}

	protected void connect(){
		try {
			new PcClient(tfIp.getText(), tfPseudo.getText());
			// dispose(); // close the JFrame
		} catch (IOException e) {
			System.err.println("Serveur inaccessible");
			e.printStackTrace();
		}
	}

	private void savePreferences() {
		PREFERENCES.putInt("px", (int) this.getLocation().getX());
		PREFERENCES.putInt("py", (int) this.getLocation().getY());
		PREFERENCES.put("ip", tfIp.getText());
		PREFERENCES.put("pseudo", tfPseudo.getText());
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
		tfIp.setText(ip);
		tfPseudo.setText(pseudo);
		tfPseudo.selectAll();
		tfIp.selectAll();

	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools

	private JTextField tfIp;
	private JTextField tfPseudo;

	private JButton btnConnexion;

	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(JFrameConnexion.class);

}
