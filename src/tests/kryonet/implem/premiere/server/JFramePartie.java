package tests.kryonet.implem.premiere.server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JFramePartie extends JFrame implements Runnable{
	
	private JTextArea textarea;
	private Partie partie;

	public JFramePartie(Partie partie){
		this.partie = partie;
		setSize(480, 460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Serveur");
		JPanel panel = new JPanel();
		textarea = new JTextArea(25,40);
		textarea.setEditable(false);
		panel.add(textarea);
		add(panel);
		setVisible(true);
		
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		while(true){
			debug(partie.toString());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void debug(String str){
		textarea.setText(str);
	}
}
