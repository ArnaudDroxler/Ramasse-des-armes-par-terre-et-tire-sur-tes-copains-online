package multi;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import multi.tools.MagasinImage;


public class FenetreJeu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Robot robot;
	private boolean robotOff;
	public static boolean mouseRightPressed;
	public static boolean mouseLeftPressed;

	public FenetreJeu(String pngFileName) throws AWTException {
			
		new MagasinImage();
		
		Logique logique = new Logique(pngFileName);

		Camera camera = new Camera(logique);
		VueJeu vueJeu = new VueJeu(logique);

		addKeyListener(logique);
		add(camera);

		JFrame frameVueJeu = new JFrame();
		frameVueJeu.add(vueJeu);
		frameVueJeu.setSize(600, 600);
		frameVueJeu.setVisible(true);
		frameVueJeu.addKeyListener(logique);

		setSize(camera.customWidth, camera.customHeight);
		setLocation(0, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		robot = new Robot();
		robotOff = false;

		mouseRightPressed = false;
		mouseLeftPressed = false;

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (!robotOff) {
					int dx = e.getX() - getWidth() / 2;
					logique.heros.rotate(dx);
					robot.mouseMove((int) (getLocation().getX() + getWidth() / 2),
							(int) (getLocation().getY() + getHeight() / 2));

				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (!robotOff) {
					int dx = e.getX() - getWidth() / 2;
					logique.heros.rotate(dx);
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
				robotOff = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseLeftPressed = true;
					logique.mousePressed();
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

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					robotOff = true;
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_Q) {
					logique.setAffichageScore(true);
				}
			}
		});

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_Q) {
					logique.setAffichageScore(false);
				}
			}
		});

	}

}
