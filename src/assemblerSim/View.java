package assemblerSim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This call is responsible for the animation part of the GUI. 
 * @author Simon Alexander Skolik; Marco "Don" Kaulea
 * 
 */
public class View extends JComponent
{

	
	// object declaration
	private Image main_image = Toolkit.getDefaultToolkit().getImage("images/VonNeumannMaschine.png");
	private Image line_ram_ar = Toolkit.getDefaultToolkit().getImage("images/lines/RAMAddressRegister.png");
	private Image line_ar_ram = Toolkit.getDefaultToolkit().getImage("images/lines/AddressRegisterRAM.png");
	private Image line_ram_pc = Toolkit.getDefaultToolkit().getImage("images/lines/RAMProgramCounter.png");
	private Image line_ram_value = Toolkit.getDefaultToolkit().getImage("images/lines/RAMValue.png");
	private Image line_ram_ir = Toolkit.getDefaultToolkit().getImage("images/lines/RAMInstructionRegister.png");
	private Image line_ram_acc = Toolkit.getDefaultToolkit().getImage("images/lines/RAMAccumulator.png");
	private Image line_pc_ar = Toolkit.getDefaultToolkit().getImage("images/lines/ProgramCounterAddressRegister.png");
	private Image line_ir_pc = Toolkit.getDefaultToolkit().getImage("images/lines/InstructionRegisterProgramCounter.png");
	private Image line_ir_value = Toolkit.getDefaultToolkit().getImage("images/lines/InstructionRegisterValue.png");
	private Image line_ir_ar = Toolkit.getDefaultToolkit().getImage("images/lines/InstructionRegisterAddressRegister.png");
	private Image line_ir_acc = Toolkit.getDefaultToolkit().getImage("images/lines/InstructionRegisterAccumulator.png");
	private Image line_alu_acc = Toolkit.getDefaultToolkit().getImage("images/lines/ALUAccumulator.png");
	private Image line_valueacc_alu = Toolkit.getDefaultToolkit().getImage("images/lines/ValueandAccumulatorALU.png");
	private Image line_acc_alu = Toolkit.getDefaultToolkit().getImage("images/lines/AccumulatorALU.png");
	private Image line_acc_ram = Toolkit.getDefaultToolkit().getImage("images/lines/AccumulatorRAM.png");
	private Image led_ram = Toolkit.getDefaultToolkit().getImage("images/LEDs/RAM.png");
	private Image led_pc = Toolkit.getDefaultToolkit().getImage("images/LEDs/ProgramCounter.png");
	private Image led_ar = Toolkit.getDefaultToolkit().getImage("images/LEDs/AddressRegister.png");
	private Image led_ir = Toolkit.getDefaultToolkit().getImage("images/LEDs/InstructionRegister.png");
	private Image led_value = Toolkit.getDefaultToolkit().getImage("images/LEDs/Value.png");
	private Image led_acc = Toolkit.getDefaultToolkit().getImage("images/LEDs/Accumulator.png");
	
	private Image main_image_scale = main_image;
	private Image line_ram_ar_scale = line_ram_ar;
	private Image line_ar_ram_scale = line_ar_ram;
	private Image line_ram_pc_scale = line_ram_pc;
	private Image line_ram_value_scale = line_ram_value;
	private Image line_ram_ir_scale = line_ram_ir;
	private Image line_ram_acc_scale = line_ram_acc;
	private Image line_pc_ar_scale = line_pc_ar;
//	private Image line_pc_value_scale = line_pc_value;
	private Image line_ir_pc_scale = line_ir_pc;
	private Image line_ir_value_scale = line_ir_value;
	private Image line_ir_ar_scale = line_ir_ar;
	private Image line_ir_acc_scale = line_ir_acc;
	private Image line_alu_acc_scale = line_alu_acc;
//	private Image line_acc_value_scale = line_acc_value;
//	private Image line_acc_pc_scale = line_acc_pc;
//	private Image line_acc_ir_scale = line_acc_ir;
//	private Image line_acc_ar_scale = line_acc_ar;
	private Image line_valueacc_alu_scale = line_valueacc_alu;
	private Image line_acc_alu_scale = line_acc_alu;
	private Image line_acc_ram_scale = line_acc_ram;
	private Image led_ram_scale = led_ram;
	private Image led_pc_scale = led_pc;
	private Image led_ar_scale = led_ar;
	private Image led_ir_scale = led_ir;
	private Image led_value_scale = led_value;
	private Image led_acc_scale = led_acc;
	
	// RAM-JTextArea field.
	private JTextArea are = new JTextArea();
	private JScrollPane sco = new JScrollPane(are);
	
	// active line
	private int linie = 0;
	private static final long serialVersionUID = 1L;
	Controller parent;
	
	//Scalling
	private double scale = 1.0;
	private double scaleToPicture = 1.0;
	
	//Text
	Font overlayFont;
	Font ramFont;

	// register
	private String pc = "00000000";
	private String ir = "00000000";
	private String ar = "00000000";
	private String val = "00000000";
	private String acc = "00000000";
	private String cycle;
	protected void setCycle(String cycle)
	{
		if(!cycle.equals(null))
		{
			this.cycle = cycle;
		}
		else
		{
			this.cycle="";
		}
		repaint();
	}

	public View(Controller ncontroller)
	{
		//Parent is used for function calls
		parent = ncontroller;
		
		//Initializing Fonts
		overlayFont = new Font("Courier",Font.PLAIN, 16);
		ramFont = new Font("Courier",Font.BOLD, 16);
		
		are.setLineWrap(false);
		are.setEditable(false);
		add(sco);
		are.setFont(ramFont);
		
		//waiting until image is loaded or max iterated through 32bit
		int max = 0;
		while(main_image.getHeight(this)==-1&&max>=0)
		{
			scale = scale*1;
			max = max+1;
		}
		//Initialize scaling factor to given picture size
		scaleToPicture = main_image.getHeight(this)/(double)600;
	}
	
	/**
	 * This Method is responsible for the animation. It consists of the backgroud image, the lines which display the
	 * simulations activities and the register values. 
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(main_image_scale, 0, 0, this);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(overlayFont);
		
		sco.setBounds((int)(37*scale*scaleToPicture),(int)(89*scale*scaleToPicture),(int)(289*scale*scaleToPicture),(int)(290*scale*scaleToPicture));
		sco.revalidate();
		
		
		// In this case block, depending on linie, the active line is drawn. Default value of linie at the beginning is 0, so no line is drawn.
		switch (linie)
		{
		default:
			// No Line to display
			break;
		case 1:
			// RAM to Address Register
			g.drawImage(line_ram_ar_scale,0,0, this);
			g.drawImage(line_ar_ram_scale,0,0, this);
			// Address Register LED
			g.drawImage(led_ar_scale,0,0, this);
			break;
		case 2:
			// RAM to Accumulator
			g.drawImage(line_ram_acc_scale,0,0, this);
			// Accumulator LED
			g.drawImage(led_acc_scale,0,0, this);
			break;
		case 3:
			// RAM to Program Counter
			g.drawImage(line_ram_pc_scale,0,0, this);
			// Program Counter LED
			g.drawImage(led_pc_scale,0,0, this);
			break;
		case 4:
			// RAM to Value
			g.drawImage(line_ram_value_scale,0,0, this);
			// Value LED
			g.drawImage(led_value_scale,0,0, this);
			break;
		case 5:
			// RAM to Instruction Register
			g.drawImage(line_ram_ir_scale,0,0, this);
			// Instruction Register LED
			g.drawImage(led_ir_scale,0,0, this);
			break;
		case 6:
			// Program Counter to Address Register 
			g.drawImage(line_pc_ar_scale,0,0, this);
			g.drawImage(line_ar_ram_scale,0,0, this);
			// Address Register LED
			g.drawImage(led_ar_scale,0,0, this);
			break;
		case 7:
			// Instructionregister to Accumulator
			g.drawImage(line_ir_acc_scale,0,0, this);
			// Accumulator LED
			g.drawImage(led_acc_scale,0,0, this);
			break;
		case 8:
			// Instruction Register to Program Counter
			g.drawImage(line_ir_pc_scale,0,0, this);
			// Program Counter LED
			g.drawImage(led_pc_scale,0,0, this);
			break;
		case 9:
			// Instruction Register to Value
			g.drawImage(line_ir_value_scale,0,0, this);
			// Value LED
			g.drawImage(led_value_scale,0,0, this);
			break;
		case 10:
			// Instruction Register to Address Register
			g.drawImage(line_ir_ar_scale,0,0, this);
			g.drawImage(line_ar_ram_scale,0,0, this);
			// Address Register LED
			g.drawImage(led_ar_scale,0,0, this);
			break;
		case 11:
			// ALU Calculation
			g.drawImage(line_valueacc_alu_scale,0,0,this);
			g.drawImage(line_alu_acc_scale,0,0, this);
			break;
		case 12:
			//single operand operation to alu and back
			g.drawImage(line_acc_alu_scale,0,0,this);
			g.drawImage(line_alu_acc_scale,0,0, this);
			break;
		case 13:
			// Accumulator to ram
			g.drawImage(line_acc_ram_scale,0,0, this);
			// RAM LED
			g.drawImage(led_ram_scale,0,0, this);
			break;
		}
		
			// the following lines draw the register values
			g2d.setColor(Color.BLACK);
			g2d.drawString(pc,(int)(430*scale*scaleToPicture),(int)(108*scale*scaleToPicture));
			g2d.drawString(ar,(int)(170*scale*scaleToPicture),(int)(510*scale*scaleToPicture));
			g2d.drawString(ir,(int)(648*scale*scaleToPicture),(int)(119*scale*scaleToPicture));
			g2d.drawString(val,(int)(458*scale*scaleToPicture),(int)(451*scale*scaleToPicture));
			g2d.drawString(acc,(int)(668*scale*scaleToPicture),(int)(451*scale*scaleToPicture));
			try {
				g2d.drawString(cycle,(int)(648*scale*scaleToPicture),(int)(36*scale*scaleToPicture));
			} catch (Exception e) {/**ignore this exception only happens when program is closing**/			}

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
		case VonNeumannRechner.ADDRESSREGISTER:
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
		repaint();
	}
	
	
	/**
	 * @param line defines which line to change
	 */
	protected void setLine(int line)
	{
		linie=line;
	}
	
	/**
	 * Sets the code in the RAM-JTextArea field.
	 * @param code Der Code, der im RAM-JTextArea-Feld angezeigt werden soll
	 */
	public void updateRAMAnimation(String code)
	{
		are.setText(code);
		are.setCaretPosition(0);
	}
	
	private void resizePictures(int x, int y,int hints)
	{
		main_image_scale = main_image.getScaledInstance(x,y,hints);
		line_ram_ar_scale = line_ram_ar.getScaledInstance(x,y,hints);
		line_ar_ram_scale = line_ar_ram.getScaledInstance(x,y,hints);
		line_ram_pc_scale = line_ram_pc.getScaledInstance(x,y,hints);
		line_ram_value_scale = line_ram_value.getScaledInstance(x,y,hints);
		line_ram_ir_scale = line_ram_ir.getScaledInstance(x,y,hints);
		line_ram_acc_scale = line_ram_acc.getScaledInstance(x,y,hints);
		line_pc_ar_scale = line_pc_ar.getScaledInstance(x,y,hints);
		line_ir_pc_scale = line_ir_pc.getScaledInstance(x,y,hints);
		line_ir_value_scale = line_ir_value.getScaledInstance(x,y,hints);
		line_ir_ar_scale = line_ir_ar.getScaledInstance(x,y,hints);
		line_ir_acc_scale = line_ir_acc.getScaledInstance(x, y, hints);
		line_alu_acc_scale = line_alu_acc.getScaledInstance(x,y,hints);
		line_valueacc_alu_scale = line_valueacc_alu.getScaledInstance(x,y,hints);
		line_acc_ram_scale = line_acc_ram.getScaledInstance(x,y,hints);
		line_acc_alu_scale = line_acc_alu.getScaledInstance(x,y,hints);
		led_ram_scale = led_ram.getScaledInstance(x,y,hints);
		led_pc_scale = led_pc.getScaledInstance(x,y,hints);
		led_ar_scale = led_ar.getScaledInstance(x,y,hints); 
		led_ir_scale = led_ir.getScaledInstance(x,y,hints); 
		led_value_scale = led_value.getScaledInstance(x,y,hints);
		led_acc_scale = led_acc.getScaledInstance(x,y,hints);
	}

	protected Dimension updateSize(int nCodeFieldX, int nConsoleHeight) 
	{
		double maxHeightScale = ((this.getParent().getHeight() - nConsoleHeight) / (double) main_image.getHeight(this));
		double maxWidthScale = ((this.getParent().getWidth() - nCodeFieldX) /(double) main_image.getWidth(this));
		if ( maxWidthScale<=maxHeightScale)
		{
			resizePictures(this.getParent().getWidth()-nCodeFieldX, -1, Image.SCALE_DEFAULT);
			scale=maxWidthScale;
		}
		else
		{
			resizePictures(-1, this.getParent().getHeight()-nConsoleHeight, Image.SCALE_DEFAULT);
			scale = maxHeightScale;
		}
		
		//Change font size
		if((this.getParent().getWidth() - nCodeFieldX) >= 1200)
		{
			overlayFont = new Font(overlayFont.getFamily(),overlayFont.getStyle(), 16);
			ramFont = new Font(ramFont.getFamily(),ramFont.getStyle(), 16);
		}
		else if((this.getParent().getWidth() - nCodeFieldX) >= 1000)
		{
			overlayFont = new Font(overlayFont.getFamily(),overlayFont.getStyle(), 14);
			ramFont = new Font(ramFont.getFamily(),ramFont.getStyle(), 14);
		}
		else if((this.getParent().getWidth() - nCodeFieldX) <= 800)
		{
			overlayFont = new Font(overlayFont.getFamily(),overlayFont.getStyle(), 12);
			ramFont = new Font(ramFont.getFamily(),ramFont.getStyle(), 12);
		}
		Dimension out = new Dimension();
		out .setSize(main_image.getWidth(this)*scale, main_image.getHeight(this)*scale);
		return out;
	}
	
	public void update(Graphics g) {
		   paint(g);
		}
}
