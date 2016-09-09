package rattco.client;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Il n'y a plus qu'une seule classe qui hérite de Renderer,
 * c'est la vueCamera, avant y avait une autre méthode de rendu
 */
public abstract class Renderer extends JPanel {

	private static final long serialVersionUID = 3838853989214554795L;
	protected static final int renderRate = 20;
	public LogiqueClient lc;

	public Renderer(LogiqueClient _logique) {
		lc = _logique;
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
