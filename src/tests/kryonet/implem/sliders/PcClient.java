package tests.kryonet.implem.sliders;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class PcClient {
	
	private JFrameClient jfc;

	public PcClient(String ip, String pseudo) throws IOException {
		
		Client client = new Client();
		Registerer.registerFor(client);
		
		client.start();
		client.connect(5000, ip, 54555, 54777);

		jfc = new JFrameClient();
		
		ClientConnexionMessage ccm = new ClientConnexionMessage(pseudo);
		client.sendUDP(ccm);
		
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof AcceptClientMessage) {
					AcceptClientMessage acm = (AcceptClientMessage) object;
					System.out.println(acm.getMsg());
					
					Thread t = new Thread(new Runnable() {
						
						@Override
						public void run() {
							ClientUpdateMessage cum = new ClientUpdateMessage();
							while(true){
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								cum.setPosition(jfc.getValue());
								client.sendUDP(cum);
							}
						}
					});
					t.start();
					
				} else if (object instanceof Partie) {
					jfc.debug(object.toString());
				}else if (object instanceof String) {
					System.out.println(object);
				}
			}
		});
	}

}
