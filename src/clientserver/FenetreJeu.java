package clientserver;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class FenetreJeu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Robot robot;
	private boolean robotOff;
	
	public FenetreJeu(String pngFileName) throws AWTException {
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

		setSize(1000, 600);
		setLocation(600, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		robot = new Robot();
		robotOff=false;
		
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if(!robotOff){
					int dx = e.getX() - getWidth()/2;
					logique.heros.rotate(dx);
					robot.mouseMove((int) (getLocation().getX()+getWidth()/2), (int) (getLocation().getY()+getHeight()/2));
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				robot.mouseMove((int) (getLocation().getX()+getWidth()/2), (int) (getLocation().getY()+getHeight()/2));
				// cache le curseur
				setCursor(getToolkit().createCustomCursor( new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null"));
				robotOff=false;
			}

			@Override
			public void mousePressed(MouseEvent e){
				logique.fire();
			}
			
		});
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					 robotOff = true;
					 setCursor(Cursor.getDefaultCursor());
				}
			}
		});

	}



}
