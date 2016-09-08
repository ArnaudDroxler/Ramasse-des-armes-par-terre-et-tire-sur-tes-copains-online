package rattco.tools;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JPanelTemps extends JPanel {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * 
	 */
	private static final long serialVersionUID = -1977706852312512043L;
	
	public JPanelTemps() {

		geometry();
		control();
		appearance();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/
	public void setMinute(int min) {
		jtfMinutes.setText("" + min);
	}

	public void setSeconde(int sec) {
		jtfSecondes.setText("" + sec);
	}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getTimeMillis() {
		return ((Integer.parseInt(jtfMinutes.getText()) * 60) + (Integer.parseInt(jtfSecondes.getText()))) * 1000 + "";
	}

	public int getMinute() {
		return Integer.parseInt(jtfMinutes.getText());

	}

	public int getSecondes() {
		return Integer.parseInt(jtfSecondes.getText());
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {
		// Layout : Specification

		jtfMinutes = new JTextField(2);
		jtfSecondes = new JTextField(2);
		jtfMinutes.setText("5");
		jtfSecondes.setText("30");

		{
			FlowLayout layout = new FlowLayout();
			setLayout(layout);
		}

		// JComponent : add
		add(jtfMinutes);
		add(new JLabel(" : "));
		add(jtfSecondes);

	}

	private void control() {
		// rien
	}

	private void appearance() {
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools

	private JTextField jtfMinutes;
	private JTextField jtfSecondes;
}
