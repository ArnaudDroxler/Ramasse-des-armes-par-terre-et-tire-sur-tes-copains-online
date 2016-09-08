package rattco.client;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import rattco.messages.AcceptClientMessage;
import rattco.messages.ClientConnexionMessage;
import rattco.messages.DamageMessage;
import rattco.messages.FinPartieMessage;
import rattco.messages.FireMessage;
import rattco.messages.KillMessage;
import rattco.messages.PickUpMessage;
import rattco.messages.PlayerUpdateMessage;
import rattco.server.Partie;
import rattco.tools.Registerer;

public class PcClient {

	private LogiqueClient lc;
	private final int networkDelay = 20;
	private Client client;
	private JFrameClient jfcCamera;
	private VueCamera vueCamera;
	private boolean existe;
	private Dimension dim;
	private Point pt;

	public PcClient(String ip, String pseudo, int customH) throws IOException {

		existe = false;
		dim = new Dimension();
		pt = new Point();

		client = new Client();
		Registerer.registerFor(client);

		client.start();

		PcClient moiMeme = this;

		// lance une IOException si ça se passe mal
		client.connect(5000, ip, 54555, 54777);

		ClientConnexionMessage ccm = new ClientConnexionMessage(pseudo);
		client.sendTCP(ccm);

		client.addListener(new Listener() {

			public void received(Connection connection, Object object) {

				if (object instanceof AcceptClientMessage) {
					AcceptClientMessage acm = (AcceptClientMessage) object;
					System.out.println(acm.getMsg());
					acm.getId();

					lc = new LogiqueClient(acm.getMapPath(), acm.getPartie(), acm.getId(), moiMeme);

					// VueMap vueMap = new VueMap(lc);
					vueCamera = new VueCamera(lc, customH);
					// jfcMap = new JFrameClient(vueMap);
					if (existe) {
						// //SetRenderer ne marche pas!
						// jfcCamera.setRenderer(vueCamera);
						jfcCamera = new JFrameClient(vueCamera);
						jfcCamera.setLocation(pt);
						jfcCamera.setSize(dim);

					} else {
						jfcCamera = new JFrameClient(vueCamera);
						existe = true;
					}

					// jfcMap.setLocation(0, 720);
					// jfcMap.setSize(400, 280);

					// jfcMap.addWindowListener(new WindowAdapter() {
					// @Override
					// public void windowClosing(WindowEvent e) {
					// client.close();
					// }
					// });

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

				}else if (object instanceof Partie) {
					lc.updatePartie((Partie) object);
				} else if (object instanceof PickUpMessage) {
					lc.hideThing(((PickUpMessage) object).getIndexOfThing());
				} else if (object instanceof KillMessage) {
					lc.murderHappened((KillMessage) object);
				} else if (object instanceof DamageMessage) {
					lc.sufferDamages((DamageMessage) object);
				} else if (object instanceof String) {
					System.out.println((String) object);
				} else if (object instanceof FinPartieMessage) {
					dim = jfcCamera.getSize();
					pt = jfcCamera.getLocation();
					jfcCamera.dispose();
					ClientConnexionMessage ccm = new ClientConnexionMessage(pseudo);
					client.sendTCP(ccm);
				}
			}
		});

	}

	public void sendPickUpMessage(int indexOfThing) {
		PickUpMessage pum = new PickUpMessage(indexOfThing);
		client.sendUDP(pum);
	}

	public void sendFireMessage(int idOfPlayer) {
		FireMessage fm = new FireMessage(idOfPlayer);
		client.sendUDP(fm);
	}

}
