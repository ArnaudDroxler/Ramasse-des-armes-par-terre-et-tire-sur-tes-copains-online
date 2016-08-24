package tests.kryonet.implem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class MainClient {

	public static void main(String[] args) {
		String serverIp = "127.0.0.1";

		JFrame frame = new JFrame();
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Client");
		JPanel panel = new JPanel();
		frame.add(panel);
		JTextField tfPseudo = new JTextField(10);
		panel.add(tfPseudo);
		JButton button = new JButton("yolo");
		panel.add(button);
		JSlider slider = new JSlider(0,100);
		panel.add(slider);
		JTextArea textarea = new JTextArea(20,40);
		panel.add(textarea);
		frame.setVisible(true);

		Client client = new Client();
		Registerer.registerFor(client);

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof AcceptClientMessage) {
					AcceptClientMessage acm = (AcceptClientMessage) object;
					System.out.println(acm.getMsg());
					
					Thread t = new Thread(new Runnable() {
						
						@Override
						public void run() {
							ClientUpdateMessage cum = new ClientUpdateMessage();
							int i=0;
							while(true){
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								cum.setPosition(slider.getValue());
								client.sendUDP(cum);
							}
						}
					});
					t.start();
					
				} else if (object instanceof Partie) {
					textarea.setText(object.toString());
				}else if (object instanceof String) {
					System.out.println(object);
				}
			}
		});

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				client.start();
				try {
					client.connect(5000, serverIp, 54555, 54777);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				String pseudo = tfPseudo.getText().toString();
				ClientConnexionMessage ccm = new ClientConnexionMessage(pseudo);
				client.sendUDP(ccm);
			}
		});

	}

}
