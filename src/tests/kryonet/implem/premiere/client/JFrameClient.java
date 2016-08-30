package tests.kryonet.implem.premiere.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import multi.Logique;
import multi.VueJeu;

public class JFrameClient extends JFrame {

	public JSlider slider1;
	public JSlider slider2;

	public JFrameClient(VueMap vueMap) {
		super();
		setSize(600, 600);
		setTitle("Client");
		/*
		LogiqueClient lc = new LogiqueClient("StandDeTire.png");
		VueMap vueMap = new VueMap(lc);
		addKeyListener(lc);
		*/
		slider1 = new JSlider();
		vueMap.add(slider1);
		slider2 = new JSlider();
		vueMap.add(slider2);
		add(vueMap);
		
		setVisible(true);
		
	}
	
}
