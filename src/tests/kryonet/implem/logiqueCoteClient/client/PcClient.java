package tests.kryonet.implem.logiqueCoteClient.client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JSlider;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import tests.kryonet.implem.logiqueCoteClient.messages.AcceptClientMessage;
import tests.kryonet.implem.logiqueCoteClient.messages.ClientConnexionMessage;
import tests.kryonet.implem.logiqueCoteClient.messages.PlayerUpdateMessage;
import tests.kryonet.implem.logiqueCoteClient.server.Partie;
import tests.kryonet.implem.logiqueCoteClient.tools.Registerer;

public class PcClient {

	private JFrameClient jfcMap;
	private LogiqueClient lc;
	public JSlider slider;
	private VueMap vueMap;
	private final int networkDelay = 20;

	public PcClient(String ip, String pseudo) throws IOException {

		Client client = new Client();
		Registerer.registerFor(client);

		client.start();

		// lance une IOException si ça se passe mal
		client.connect(5000, ip, 54555, 54777);

		ClientConnexionMessage ccm = new ClientConnexionMessage(pseudo);
		client.sendTCP(ccm);

		client.addListener(new Listener() {
			
			public void received(Connection connection, Object object) {

				if (object instanceof AcceptClientMessage) {
					AcceptClientMessage acm = (AcceptClientMessage) object;
					System.out.println(acm.getMsg());

					lc = new LogiqueClient(acm.getMapPath(), acm.getPartie(), acm.getId());
					VueMap vueMap = new VueMap(lc);
					VueCamera vueCamera = new VueCamera(lc);
					//jfcMap = new JFrameClient(vueMap);
					JFrameClient jfcCamera = new JFrameClient(vueCamera);
					//jfcMap.setLocation(0, 720);
					//jfcMap.setSize(400, 280);

//					jfcMap.addWindowListener(new WindowAdapter() {
//						@Override
//						public void windowClosing(WindowEvent e) {
//							client.close();
//						}
//					});

					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							PlayerUpdateMessage pum = new PlayerUpdateMessage();
							while (true) {
								pum.setJoueur(lc.joueur);
								client.sendUDP(pum);
								try {
									Thread.sleep(networkDelay);
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
					System.out.println((String)object);
				}
			}
		});

	}

}
