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

public class JFrameClient extends JFrame {

	private LogiqueClient lc;

	public JFrameClient(Renderer renderer) {
		super();
		setSize(640, 360);
		setTitle("Client");
		add(renderer);
		setVisible(true);
		
		lc = renderer.lc;	
		addListeners();			
	}

	private Robot robot;
	private boolean robotActive;
	public static boolean mouseRightPressed;
	public static boolean mouseLeftPressed;
	

	private void addListeners() {
		addMouseListeners();
		addKeyListeners();
	}
	

	private void addMouseListeners() {
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
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
				setCursor(getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),new Point(0, 0), "null"));
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
				}else if (e.getKeyCode() == KeyEvent.VK_Q) {
					lc.setAffichageScore(true);
				}else{
					lc.touchesEnfoncees.add(e.getKeyCode());
				}
			}
		});

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_Q) {
					lc.setAffichageScore(false);
				}else{
					lc.touchesEnfoncees.remove(e.getKeyCode());
				}
			}
		});
	}
	
}
