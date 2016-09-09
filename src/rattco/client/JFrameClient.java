package rattco.client;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Cette classe décrit la fenêtre qui contiendra l'objet vueCamera
 * Elle gère les évènements clavier et souris
 */
public class JFrameClient extends JFrame {

	private static final long serialVersionUID = 8545021550002081154L;
	private LogiqueClient lc;
	private Renderer renderer;

	/**
	 * Le robot permet de replacer la souris au centre à chaque mouvement de souris
	 */
	private Robot robot;
	private boolean robotActive;
	public static boolean mouseRightPressed;
	public static boolean mouseLeftPressed;

	public JFrameClient(Renderer render) {
		super();
		setSize(640, 360);
		setTitle("Client");
		renderer = render;
		add(renderer);
		setVisible(true);

		lc = renderer.lc;
		addListeners();
	}

	public void setRenderer(Renderer render) {
		remove(renderer);
		add(render);
	}

	private void addListeners() {
		addMouseListeners();
		addKeyListeners();
	}

	private void addMouseListeners() {
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		robotActive = false;

		mouseRightPressed = false;
		mouseLeftPressed = false;

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				rotatePlayer(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				rotatePlayer(e);
			}

			private void rotatePlayer(MouseEvent e) {
				if (robotActive) {
					int dx = e.getX() - getWidth() / 2;
					lc.joueur.rotate(dx);
					robot.mouseMove((int) (getLocation().getX() + getWidth() / 2),
							(int) (getLocation().getY() + getHeight() / 2));

				}
			}
		});

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				// place le pointeur au centre
				robot.mouseMove((int) (getLocation().getX() + getWidth() / 2),
						(int) (getLocation().getY() + getHeight() / 2));
				// cache le curseur
				setCursor(getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
						new Point(0, 0), "null"));
				// active le robot
				robotActive = true;
			}

			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseLeftPressed = true;
					lc.mouseLeftPressed();
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					mouseRightPressed = true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseLeftPressed = false;
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					mouseRightPressed = false;
				}
			}

		});
	}

	private void addKeyListeners() {

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					robotActive = false;
					setCursor(Cursor.getDefaultCursor());
				} else {
					lc.touchesEnfoncees.add(e.getKeyCode());
				}
			}
		});

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				lc.touchesEnfoncees.remove(e.getKeyCode());
			}
		});
	}

}
