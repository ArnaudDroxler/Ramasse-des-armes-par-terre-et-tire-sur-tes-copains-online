package tests.kryonet.implem.logiqueCoteClient.client;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Renderer extends JPanel {

	private static final long serialVersionUID = 3838853989214554795L;
	protected static final int renderRate = 20;
	public LogiqueClient lc;

	public Renderer(LogiqueClient _logique) {
		lc = _logique;
		setFocusable(true);
		addKeyListener(lc);
		animer();
	}

	private void animer() {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (!lc.fin) {
						repaint();
						Thread.sleep(renderRate);
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
