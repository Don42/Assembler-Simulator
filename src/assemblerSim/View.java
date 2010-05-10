package assemblerSim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	private Image main_image = Toolkit.getDefaultToolkit().getImage("images/VonNeumannMaschine.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_ram_ar = Toolkit.getDefaultToolkit().getImage("images/lines/RAMAdressRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_ar_ram = Toolkit.getDefaultToolkit().getImage("images/lines/AddressRegisterRAM.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_ram_pc = Toolkit.getDefaultToolkit().getImage("images/lines/RAMProgramCounter.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_ram_value = Toolkit.getDefaultToolkit().getImage("images/lines/RAMValue.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_ram_ir = Toolkit.getDefaultToolkit().getImage("images/lines/RAMInstructionRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_pc_ar = Toolkit.getDefaultToolkit().getImage("images/lines/ProgramCounterAddressRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_pc_value = Toolkit.getDefaultToolkit().getImage("images/lines/ProgramCounterValue.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_pc_ir = Toolkit.getDefaultToolkit().getImage("images/lines/ProgramCounterInstructionRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_ir_pc = Toolkit.getDefaultToolkit().getImage("images/lines/InstructionRegisterProgramCounter.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_ir_value = Toolkit.getDefaultToolkit().getImage("images/lines/InstructionRegisterValue.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_ir_ar = Toolkit.getDefaultToolkit().getImage("images/lines/InstructionRegisterAddressRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_alu_acc = Toolkit.getDefaultToolkit().getImage("images/lines/ALUAccumulator.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_acc_value = Toolkit.getDefaultToolkit().getImage("images/lines/AccumulatorValue.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_acc_pc = Toolkit.getDefaultToolkit().getImage("images/lines/AccumulatorProgramCounter.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_acc_ir = Toolkit.getDefaultToolkit().getImage("images/lines/AccumulatorInstructionRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_acc_ar = Toolkit.getDefaultToolkit().getImage("images/lines/AccumulatorAddressRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image line_valueacc_alu = Toolkit.getDefaultToolkit().getImage("images/lines/ValueandAccumulatorALU.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image led_ram = Toolkit.getDefaultToolkit().getImage("images/LEDs/RAM.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image led_pc = Toolkit.getDefaultToolkit().getImage("images/LEDs/ProgramCounter.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image led_ar = Toolkit.getDefaultToolkit().getImage("images/LEDs/AddressRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image led_ir = Toolkit.getDefaultToolkit().getImage("images/LEDs/InstructionRegister.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image led_value = Toolkit.getDefaultToolkit().getImage("images/LEDs/Value.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image led_acc = Toolkit.getDefaultToolkit().getImage("images/LEDs/Accumulator.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	private Image led_alu = Toolkit.getDefaultToolkit().getImage("images/LEDs/ALU.png").getScaledInstance(800, 600,Image.SCALE_SMOOTH);
	
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
		g.drawImage(main_image, 0, 0, this);
		Graphics2D g2d = (Graphics2D) g;
		
		// In this case block, depending on linie, the active line is drawn. Default value of linie at the beginning is 0, so no line is drawn.
		switch (linie)
		{
		default:
			// No Line to display
			break;
		case 1:
			// RAM to Address Register
			g.drawImage(line_ram_ar,0,0, this);
			break;
		case 2:
			// Address Register to RAM
			g.drawImage(line_ar_ram,0,0, this);
			break;
		case 3:
			// RAM to Program Counter
			g.drawImage(line_ram_pc,0,0, this);
			break;
		case 4:
			// RAM to Value
			g.drawImage(line_ram_value,0,0, this);
			break;
		case 5:
			// RAM to Instruction Register
			g.drawImage(line_ram_ir,0,0, this);
			break;
		case 6:
			// Program Counter to Address Register 
			g.drawImage(line_pc_ar,0,0, this);
			break;
		case 7:
			// Program Counter to Value
			g.drawImage(line_pc_value,0,0, this);
			break;
		case 8:
			// Program Counter to Instruction Register
			g.drawImage(line_pc_ir,0,0, this);
			break;
		case 9:
			// Instruction Register to Program Counter
			g.drawImage(line_ir_pc,0,0, this);
			break;
		case 10:
			// Instruction Register to Value
			g.drawImage(line_ir_value,0,0, this);
			break;
		case 11:
			// Instruction Register to Address Register
			g.drawImage(line_ir_ar,0,0, this);
			break;
		case 12:
			// ALU to Accumulator
			g.drawImage(line_alu_acc,0,0, this);
			break;
		case 13:
			// Accumulator to Value
			g.drawImage(line_acc_value,0,0, this);
			break;
		case 14:
			// Accumulator to Program Counter
			g.drawImage(line_acc_pc,0,0, this);
			break;
		case 15:
			// Accumulator to Instruction Register
			g.drawImage(line_acc_ir,0,0, this);
			break;
		case 16:
			// Accumulator to Address Register
			g.drawImage(line_acc_ar,0,0, this);
			break;
		case 17:
			// Value and Accumulator to ALU
			g.drawImage(line_valueacc_alu,0,0,this);
			break;
		case 18:
			// RAM LED
			g.drawImage(led_ram,0,0, this);
			break;
		case 19:
			// Program Counter LED
			g.drawImage(led_pc,0,0, this);
			break;
		case 20:
			// Address Register LED
			g.drawImage(led_ar,0,0, this);
			break;
		case 21:
			// Instruction Register LED
			g.drawImage(led_ir,0,0, this);
			break;
		case 22:
			// Value LED
			g.drawImage(led_value,0,0, this);
			break;
		case 23:
			// Accumulator LED
			g.drawImage(led_acc,0,0, this);
			break;
		case 24:
			// ALU LED
			g.drawImage(led_alu,0,0, this);
			break;
		}
		
		// the following lines draw the register values
		g2d.setColor(Color.BLACK);
		g2d.drawString(String.valueOf(pc),295,125);
		g2d.drawString(String.valueOf(ar),185,525);
		g2d.drawString(String.valueOf(ir),550,119);
		g2d.drawString(String.valueOf(val),450,432);
		g2d.drawString(String.valueOf(acc),650,432);

	}
	
	/**
	 * @param register defines which display field to change.
	 * @param value is the new value
	 */
	protected void setRegister(int register, int value)
	{
		String temp = Integer.toHexString(value).toUpperCase();
		while(temp.length()<8)
		{
			temp = "0" + temp;
		}
		switch (register)
		{
		case VonNeumannRechner.PROGRAMMCOUNTER:
			// Program Counter
			pc=temp;
			break;
		case VonNeumannRechner.INSTRUCTIONREGISTER:
			// Instruction Register
			ir=temp;
			break;
		case VonNeumannRechner.ADRESSREGISTER:
			// Address Register
			ar=temp;
			break;
		case VonNeumannRechner.VALUEREGISTER:
			// Value
			val=temp;
			break;
		case VonNeumannRechner.ACCUMULATOR:
			// Accumulator
			acc=temp;
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
