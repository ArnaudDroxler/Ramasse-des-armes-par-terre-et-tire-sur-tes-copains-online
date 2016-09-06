package toutdansunpackage.tools.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JFrameMap extends javax.swing.JFrame {

	private LvlMap map;

	public JFrameMap(LvlMap map) {
		this.map=map;
		setVisible(true);
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.black);
		JPanel panel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				BufferedImage bg = map.getMapBackground();
				double factor = Math.min(getWidth()/(double)bg.getWidth(),getHeight()/(double)bg.getHeight());
				g2d.scale(factor, factor);
				g2d.drawImage(bg,0,0,null);
			}
		};
		add(panel);
	}
	

}
