package assemblerSim;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

/**
 * This class is starts the rest of the program
 * @author Marco "Don" Kaulea 
 */
public class Starter 
{

	/**
	 * @param args 
	 * Creates an object of the Controller class, which the takes over control.
	 */
	public static void main(String[] args) 
	{
		Dimension scrnsize = Toolkit.getDefaultToolkit().getScreenSize();
		if(scrnsize.height<768||scrnsize.width<1024)
			{
			int ans = JOptionPane.showConfirmDialog(null, "Your Resolution is not supported. If you continue there might be graphical errors. Do you want to continue?", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ans == JOptionPane.OK_OPTION)
			{
				new Controller(512);
			}
			else
			{
				return;
			}
		}
		else
		{
			new Controller(512);
		}
	}

}
