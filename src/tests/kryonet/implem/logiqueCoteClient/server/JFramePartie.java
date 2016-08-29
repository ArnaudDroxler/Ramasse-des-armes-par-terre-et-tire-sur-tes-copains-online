package tests.kryonet.implem.logiqueCoteClient.server;

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
		
		long t1=System.currentTimeMillis(),t2;
		while(true){
			t2=System.currentTimeMillis();
			partie.setTempsSecondes((t2-t1)/1000);
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
