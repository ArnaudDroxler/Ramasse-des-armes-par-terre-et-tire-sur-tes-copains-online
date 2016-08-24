package tests.kryonet.implem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class MainClient {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Client");
		JPanel panel = new JPanel();
		frame.add(panel);
		JButton button = new JButton("yolo");
		panel.add(button);
		JTextField tfPseudo = new JTextField(10);
		panel.add(tfPseudo);
		frame.setVisible(true);

		Client client = new Client();
		Registerer.registerFor(client);

		client.start();
		try {
			client.connect(5000, "localhost", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof AcceptClientMessage) {
					AcceptClientMessage acm = (AcceptClientMessage) object;
					System.out.println(acm.getMsg());
				} else if (object instanceof String) {
					System.out.println(object);
				}
			}
		});

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientConnexionMessage ccm = new ClientConnexionMessage(tfPseudo.getText().toString());
				client.sendUDP(ccm);
			}
		});

	}

}
