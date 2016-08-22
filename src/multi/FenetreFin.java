package multi;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import base.tools.MapFileChooser;

public class FenetreFin extends JFrame {

	private static final long serialVersionUID = -1129083443697734135L;
	private JButton btnRecommencer;
	private JLabel lblMessage;

	public FenetreFin(String message) {

		setSize(250, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		btnRecommencer = new JButton("Recommencer");
		
		lblMessage = new JLabel(message);
		lblMessage.setFont(new Font("Arial", 10,50));
		lblMessage.setHorizontalAlignment(0);
		setTitle("Fin de la partie");
		
		JPanel panel = new JPanel();
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
		
		panel.add(lblMessage, BorderLayout.CENTER);
		panel.add(btnRecommencer, BorderLayout.SOUTH);
		
		add(panel);
		
		btnRecommencer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MapFileChooser();
				dispose();
			}
		});

		setVisible(true);
	}
	
}
