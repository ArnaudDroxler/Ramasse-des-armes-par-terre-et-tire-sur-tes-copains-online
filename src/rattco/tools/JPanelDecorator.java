
package rattco.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class JPanelDecorator extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelDecorator(JComponent jcomponentcentre, int marge)
		{
		this.jcomponentcentre = jcomponentcentre;
		this.marge = marge;

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

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// Layout : Specification
			{
			BorderLayout layout = new BorderLayout();
			setLayout(layout);
			}

		// JComponent : add
		add(jcomponentcentre, BorderLayout.CENTER);
		add(createJPanelMarge(), BorderLayout.NORTH);
		add(createJPanelMarge(), BorderLayout.SOUTH);
		add(createJPanelMarge(), BorderLayout.EAST);
		add(createJPanelMarge(), BorderLayout.WEST);
		}

	private JPanel createJPanelMarge()
		{
		JPanel panel = new JPanel();
		Dimension dim = new Dimension(marge, marge);
		panel.setPreferredSize(dim);
		//debug
			{
			//panel.setBackground(Color.BLACK);
			}

		return panel;
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools

	//input
	private JComponent jcomponentcentre;
	private int marge;

	}
