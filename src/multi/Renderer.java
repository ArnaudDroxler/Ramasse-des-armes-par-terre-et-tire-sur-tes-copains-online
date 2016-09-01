package multi;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Renderer extends JPanel {

	private static final long serialVersionUID = 3838853989214554795L;
	public Logique logique;

	public Renderer(Logique _logique) {
		logique = _logique;
		animer();
	}

	private void animer() {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (!logique.fin) {
						repaint();
						Thread.sleep(50);
					}
					((JFrame) getTopLevelAncestor()).dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();

	}

}
