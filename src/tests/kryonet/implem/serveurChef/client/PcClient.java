package tests.kryonet.implem.serveurChef.client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JSlider;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import tests.kryonet.implem.serveurChef.messages.AcceptClientMessage;
import tests.kryonet.implem.serveurChef.messages.ClientConnexionMessage;
import tests.kryonet.implem.serveurChef.messages.ClientUpdateMessage;
import tests.kryonet.implem.serveurChef.server.Partie;
import tests.kryonet.implem.serveurChef.tools.Registerer;

public class PcClient {

	private JFrameClient jfc;
	private Partie partie;
	private LogiqueClient lc;
	public JSlider slider;
	private VueMap vueMap;

	private boolean WaitingForResponse;

	public PcClient(String ip, String pseudo) throws IOException {

		Client client = new Client();
		Registerer.registerFor(client);

		client.start();

		// lance une IOException si ça se passe mal
		client.connect(5000, ip, 54555, 54777);

		lc = new LogiqueClient("StandDeTire.png");
		vueMap = new VueMap(lc);
		jfc = new JFrameClient(vueMap);
		jfc.addKeyListener(lc);

		ClientConnexionMessage ccm = new ClientConnexionMessage(pseudo);
		client.sendTCP(ccm);

		client.addListener(new Listener() {
			public void connected (Connection connection) {
				System.out.println("client connecté");
			}

			public void received(Connection connection, Object object) {

				if (object instanceof AcceptClientMessage) {
					AcceptClientMessage acm = (AcceptClientMessage) object;
					System.out.println(acm.getMsg());
					lc.setId(acm.getId());
					
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							ClientUpdateMessage cum = new ClientUpdateMessage();
							while (true) {
								cum.setPosition(jfc.slider1.getValue(),jfc.slider2.getValue());
								client.sendUDP(cum);
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					});
					t.start();

				} else if (object instanceof Partie) {
					// jfc.debug(object.toString());
					lc.updatePartie((Partie) object);
				} else if (object instanceof String) {
					System.out.println(object);
				}
			}
		});

		jfc.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				client.close();
			}
		});

	}

}
