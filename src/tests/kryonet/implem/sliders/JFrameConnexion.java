
package tests.kryonet.implem.sliders;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		// JComponent : Instanciation
		JPanel panel = new JPanel();
		tfIp = new JTextField();
		tfPseudo = new JTextField();
		btnConnexion = new JButton("Rejoindre");

		// Layout : Specification
		{
			GridLayout layout = new GridLayout(3, 2);

			layout.setHgap(5);
			layout.setVgap(5);

			panel.setLayout(layout);
		}

		// JComponent : add

		panel.add(new JLabel("Adresse du serveur"));
		panel.add(tfIp);
		panel.add(new JLabel("Votre pseudo"));
		panel.add(tfPseudo);
		panel.add(new JLabel(""));
		panel.add(btnConnexion);
		add(new JPanelDecorator(panel, 20));
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
						try {
							btnConnexion.setText("Connexion en cours");
							btnConnexion.setEnabled(false);
							connect();
							// dispose(); // close the JFrame
							btnConnexion.setText("Rejoindre");
							btnConnexion.setEnabled(true);
						} catch (RemoteException | UnknownHostException e) {
							e.printStackTrace();
						}
					}
				});
				threadConnect.start();
			}
		});
	}

	private void appearance() {
		setSize(450, 150);
		setResizable(false);
		setTitle("Connexion");
		tfIp.selectAll();

		loadPreferences();
		setVisible(true); // last!
	}

	protected void connect() throws RemoteException, UnknownHostException {
		try {
			new PcClient(tfIp.getText(), tfPseudo.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.err.println("Serveur inaccessible");
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
