package tests.kryonet.implem.logiqueCoteClient.client;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JSpinner;

public class JFrameClient extends JFrame {

	public JSpinner spinner;

	public JFrameClient(VueMap vueMap) {
		super();
		setSize(600, 600);
		setTitle("Client");
		/*
		LogiqueClient lc = new LogiqueClient("StandDeTire.png");
		VueMap vueMap = new VueMap(lc);
		addKeyListener(lc);
		*/
		
		spinner = new JSpinner();
		spinner.setValue(10);
		vueMap.add(spinner);
		
		add(vueMap);
		
		setVisible(true);
		
	}
	
}
