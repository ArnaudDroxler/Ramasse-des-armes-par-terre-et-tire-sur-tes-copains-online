package tests.kryonet.implem.premiere.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import multi.Logique;
import multi.VueJeu;
import tests.kryonet.implem.premiere.server.Partie;

public class JFrameClient extends JFrame {

	public JSlider slider;

	public JFrameClient(VueMap vueMap) {
		super();
		setSize(600, 600);
		setTitle("Client");
		/*
		LogiqueClient lc = new LogiqueClient("StandDeTire.png");
		VueMap vueMap = new VueMap(lc);
		addKeyListener(lc);
		*/
		slider = new JSlider();
		vueMap.add(slider);
		add(vueMap);
		
		setVisible(true);
		
	}
	
}
