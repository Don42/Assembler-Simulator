package assemblerSim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This call is responsible for the animation part of the GUI. 
 * @author Simon Alexander Skolik
 * @version 0.1
 */
public class View extends JComponent
{

	
	// object declaration
	private Image image = Toolkit.getDefaultToolkit().getImage("VonNeumannMaschine.png");
	private final Stroke stroke1 = new BasicStroke(13.0F);
	
	// RAM-JTextArea field.
	private JTextArea are = new JTextArea();
	private JScrollPane sco = new JScrollPane(are);
	
	// active line
	private int linie = 0;
	private static final long serialVersionUID = 1L;
	Controller parent;
	
	// register
	private String pc = "00000000";
	private String ir = "00000000";
	private String ar = "00000000";
	private String val = "00000000";
	private String acc = "00000000";
	public View(Controller ncontroller)
	{
		parent = ncontroller;
		are.setLineWrap(true);
		are.setEditable(false);
		add(sco);
		sco.setBounds(43,114,123,283);
	}
	
	/**
	 * This Method is responsible for the animation. It consists of the backgroud image, the lines which display the
	 * simulations activities and the register values. 
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(stroke1);
		g2d.setColor(Color.RED);
		
		// In this case block, depending on linie, the active line is drawn. Default value of linie at the beginning is 0, so no line is drawn.
		switch (linie)
		{
		default:
			// No Line to display
			break;
		case 1:
			// RAM to Address Register
			g2d.drawLine(197,282,320,282);
			g2d.drawLine(319,522,319,281);
			g2d.drawLine(319,522,301,522);
			break;
		case 2:
			// Address Register to RAM
			g2d.drawLine(103,528,126,528);
			g2d.drawLine(104,528,104,427);
			break;
		case 3:
			// RAM to Program Counter
			g2d.drawLine(197,282,347,282);
			g2d.drawLine(347,282,347,148);
			break;
		case 4:
			// RAM to Value
			g2d.drawLine(197,282,478,282);
			g2d.drawLine(478,381,478,281);
			break;
		case 5:
			// RAM to Instruction Register
			g2d.drawLine(197,282,605,282);
			g2d.drawLine(605,282,605,144);
			break;
		case 6:
			// Program Counter to Address Register 
			g2d.drawLine(294,282,294,148);
			g2d.drawLine(293,282,320,282);
			g2d.drawLine(319,522,319,281);
			g2d.drawLine(319,522,301,522);
			break;
		case 7:
			// Program Counter to Program Counter
			g2d.drawLine(294,282,294,148);
			g2d.drawLine(293,282,347,282);
			g2d.drawLine(347,282,347,148);
			break;
		case 8:
			// Program Counter to Value
			g2d.drawLine(294,282,294,148);
			g2d.drawLine(293,282,478,282);
			g2d.drawLine(478,381,478,281);
			break;
		case 9:
			// Program Counter to Instruction Register
			g2d.drawLine(294,282,294,148);
			g2d.drawLine(293,282,604,282);
			g2d.drawLine(605,282,605,144);
			break;
		case 10:
			// Instruction Register to Address Register
			g2d.drawLine(549,282,549,144);
			g2d.drawLine(318,282,550,282);
			g2d.drawLine(319,522,319,281);
			g2d.drawLine(319,522,301,522);
			break;
		case 11:
			// Instruction Register to Program Counter
			g2d.drawLine(549,282,549,144);
			g2d.drawLine(346,282,550,282);
			g2d.drawLine(347,282,347,148);
			break;
		case 12:
			// Instruction Register to Value
			g2d.drawLine(549,282,549,144);
			g2d.drawLine(477,282,550,282);
			g2d.drawLine(478,381,478,281);
			break;
		case 13:
			// Instruction Register to Instruction Register
			g2d.drawLine(549,282,549,144);
			g2d.drawLine(548,282,605,282);
			g2d.drawLine(605,282,605,144);
			break;
		case 14:
			// Accumulator to Instruction Register
			g2d.drawLine(669,380,669,281);
			g2d.drawLine(604,282,669,282);
			g2d.drawLine(605,282,605,144);
			break;
		case 15:
			// Accumulator to Value
			g2d.drawLine(669,380,669,281);
			g2d.drawLine(477,282,669,282);
			g2d.drawLine(478,381,478,281);
			break;
		case 16:
			// Accumulator to Program Counter
			g2d.drawLine(669,380,669,281);
			g2d.drawLine(346,282,669,282);
			g2d.drawLine(347,282,347,148);
			break;
		case 17:
			// Accumulator to Address Register
			g2d.drawLine(669,380,669,281);
			g2d.drawLine(318,282,669,282);
			g2d.drawLine(319,522,319,281);
			g2d.drawLine(319,522,301,522);
			break;
		case 18:
			// Value & Accumulator to ALU
			g2d.drawLine(526,465,526,455);
			g2d.drawLine(631,465,631,455);
			g2d.drawLine(526,465,631,465);
			g2d.drawLine(577,473,577,464);
			break;
		case 19:
			// ALU to Accumulator
			g2d.drawLine(675,473,675,455);
			break;
		}
		
		// the following lines draw the register values
		g2d.setColor(Color.BLACK);
		g2d.drawString(String.valueOf(pc),319,125);
		g2d.drawString(String.valueOf(ar),210,525);
		g2d.drawString(String.valueOf(ir),572,119);
		g2d.drawString(String.valueOf(val),475,432);
		g2d.drawString(String.valueOf(acc),674,432);

	}
	
	/**
	 * @param register defines which display field to change.
	 * @param value is the new value
	 */
	protected void setRegister(int register, int value)
	{
		switch (register)
		{
		case VonNeumannRechner.PROGRAMMCOUNTER:
			// Program Counter
			pc=Integer.toHexString(value);
			break;
		case VonNeumannRechner.INSTRUCTIONREGISTER:
			// Instruction Register
			ir=Integer.toHexString(value);
			break;
		case VonNeumannRechner.ADRESSREGISTER:
			// Address Register
			ar=Integer.toHexString(value);
			break;
		case VonNeumannRechner.VALUEREGISTER:
			// Value
			val=Integer.toHexString(value);
			break;
		case VonNeumannRechner.ACCUMULATOR:
			// Accumulator
			acc=Integer.toHexString(value);
			break;
		default:
			break;
		}
		drawAnimation();
	}
	
	/**
	 * @param line defindes which line to change
	 * @param value is the new value
	 */
	protected void setLine(int line, boolean value)
	{
		linie=line;
	}
	
	/**
	 * Redraws the animation.
	 */
	protected void drawAnimation()
	{
		repaint();
	}
	
	/**
	 * @return RAM-Code, der im der RAM-JTextArea steht.
	 */
	public String getRAMAnimationCode()
	{
		return are.getText();
	}
	
	/**
	 * Sets the code in the RAM-JTextArea field.
	 * @param code Der Code, der im RAM-JTextArea-Feld angezeigt werden soll
	 */
	public void setRAMAnimation(String code)
	{
		are.setText(code);
		are.setCaretPosition(0);
	}
}
