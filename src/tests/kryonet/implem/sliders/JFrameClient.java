package tests.kryonet.implem.sliders;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JFrameClient extends JFrame {

	private JPanel panel;
	private JSlider slider;
	private JTextArea textarea;
	
	public JFrameClient(){
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Client");
		
		panel = new JPanel();
		add(panel);
		slider = new JSlider(0,100);
		panel.add(slider);
		textarea = new JTextArea(20,40);
		panel.add(textarea);
		setVisible(true);
	}

	public void debug(String string) {
		textarea.setText(string);
	}

	public int getValue() {
		return slider.getValue();
	}

	
	
}
