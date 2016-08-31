package tests.kryonet.implem.logiqueCoteClient.client;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JSpinner;

public class JFrameClient extends JFrame {

	public JSpinner spinner;

	public JFrameClient(Renderer rederer) {
		super();
		setSize(1280, 720);
		setTitle("Client");
		/*
		LogiqueClient lc = new LogiqueClient("StandDeTire.png");
		VueMap vueMap = new VueMap(lc);
		addKeyListener(lc);
		*/
		
		spinner = new JSpinner();
		spinner.setValue(10);
		rederer.add(spinner);
		
		add(rederer);
		
		setVisible(true);
		
	}
	
}
