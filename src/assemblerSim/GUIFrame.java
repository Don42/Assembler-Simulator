package assemblerSim;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class creates the GUI Frame and the GUI elements.
 * @author Simon Alexander Skolik
 * @version 0.1
 */
public class GUIFrame extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// object declaration
	private View view;
	private Controller parent;
	
	// declaration of the GUI elements
	private JButton save = new JButton("Save");
	private JButton load = new JButton("Open");
	private JButton step = new JButton("Step");
	private JButton run = new JButton("Run");
	private JButton stop = new JButton("Stop");
	private JButton reset = new JButton("Reset");
	private JButton toRAM = new JButton("Assemble");
	private JLabel sliderLabel = new JLabel("Delay in ms");
	private JSlider slider = new JSlider(JSlider.VERTICAL,1,5000,1000);
//	private JTextField field = new JTextField(String.valueOf(slider.getValue()));
	private JTextArea area = new JTextArea();
	private JTextArea console = new JTextArea();
	private JScrollPane scroll1, scroll2;
	
	private final static int BUTTON_WIDTH = 100;
	private final static int BUTTON_SPACER = 15;
	private final static int AREA_WIDTH = 130;
	
	
	/**
	 * This is the Constructor in which the Elements of the GUI are created and placed within the frame.
	 */
	public GUIFrame(View nView, Controller nParent)
	{ 
		super("VonNeumann-Simulator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1024,768)); 
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.setMaximizedBounds(env.getMaximumWindowBounds());
		this.setPreferredSize(env.getMaximumWindowBounds().getSize());
		this.setSize(this.getPreferredSize());
		this.setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH);
		
		this.addComponentListener(new ResizeListener(this));
		
		view = nView;
		parent = nParent;
		
		// Save-Button
		add(save);
		save.setBounds(this.getWidth()-BUTTON_WIDTH,30,BUTTON_WIDTH,30);
		save.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveRamToFile();
			}

		});
		
		// Load-Button
		add(load);
		load.setBounds(this.getWidth()-BUTTON_WIDTH,0,BUTTON_WIDTH,30);
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadRamFromFile();
			}
		});
		
		// Reset-Button
		add(reset);
		reset.setBounds(this.getWidth()-BUTTON_WIDTH,180,BUTTON_WIDTH,30);
		reset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.reset();
			}
		});
		
		// Run-Button
		add(run);
		run.setBounds(this.getWidth()-BUTTON_WIDTH,90,BUTTON_WIDTH,30);
		run.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.run();
			}
		});
		
		// Button to write Input frome the Code Area to RAM
		add(toRAM);
		toRAM.setBounds(this.getWidth()-BUTTON_WIDTH,60,BUTTON_WIDTH,30);
		toRAM.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.setRAM();
			}
		});
		
		// Step-Button
		add(step);
		step.setBounds(this.getWidth()-BUTTON_WIDTH,120,BUTTON_WIDTH,30);
		step.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.step();
			}
		});
		
		// Stop-Button
		add(stop);
		stop.setBounds(this.getWidth()-BUTTON_WIDTH,150,BUTTON_WIDTH,30);
		stop.setEnabled(false);
		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.halt();
			}
		});
		
		// Label for the clock elements
		add(sliderLabel);
		sliderLabel.setBounds(this.getWidth()-BUTTON_WIDTH,220,BUTTON_WIDTH,20);
		
		// Clock-Slider
		add(slider);
		slider.setBounds(this.getWidth()-BUTTON_WIDTH,240,BUTTON_WIDTH,200);
		slider.setMajorTickSpacing(1000);
		slider.setMinorTickSpacing(500);
		slider.setPaintTicks(true);
		slider.setPaintLabels(false);
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
//				field.setText(String.valueOf(slider.getValue()));
				parent.setStepTime(slider.getValue());
			}
		});
		
		// Clock-JTextField
//		add(field);
//		field.setBounds(this.getWidth()-BUTTON_WIDTH,450,BUTTON_WIDTH,30);
//		field.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				slider.setValue(Integer.parseInt(field.getText()));
//			}
//		});
		
		// Program Code Field
		area.setLineWrap(true);
		scroll1 = new JScrollPane(area);
		add(scroll1);
		scroll1.setBounds(this.getWidth()-220,0,130,this.getHeight());

		// Console
//		console.setLineWrap(true);
//		scroll2 = new JScrollPane(console);
//		add(scroll2);
//		scroll2.setBounds(13,615,1072,193);
//		console.setEditable(false);
		
		// The Von-Neumann Animation
		add(view);
		
		// makes the GUI visible
		setVisible(true);
	}
	
	/**
	 * @return Assembler-Code of the Program Code Field
	 */
	String eingabeCodeUebergabe()
	{
		return area.getText();
	}
	
	void updateRAMAnimation(int[] nRAM)
	{
		int[] tRAM = nRAM;
		String output = "";
		
		for(int i = 0; i<tRAM.length;i++)
		{
			String tOut =  ""+ Integer.toHexString(tRAM[i]);
			String cell = ""+i;
			while(tOut.length()<8)
			{
				tOut = "0" + tOut;
			}
			
			while(cell.length()<3)
			{
				cell = "0"+cell;
			}
			output = output + "  " + cell + " | " + tOut+"\n";
		}
		view.setRAMAnimation(output);
	}
	
	protected void setCodeArea(String in)
	{
		this.area.setText(in); 
	}
	protected String getTextFromCodeArea()
	{
		return this.area.getText();
	}
	
	void run()
	{
		step.setEnabled(false);
		run.setEnabled(false);
		reset.setEnabled(false);
		stop.setEnabled(true);
	}
	
	void stop()
	{
		step.setEnabled(true);
		run.setEnabled(true);
		reset.setEnabled(true);
		stop.setEnabled(false);
	}
	
	void loadRamFromFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//chooser.setFileFilter(new FileFilter());
		int returnVal = chooser.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			File out = chooser.getSelectedFile();
            try
			{
				parent.loadRamFromFile(out);
			} 		
            catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "An Error has occured \n" + e.getMessage());
				e.printStackTrace();
			}
        }
	}
	
	void saveRamToFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = chooser.showSaveDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			File out = chooser.getSelectedFile();
			
			try
			{
				if (!out.exists())
				{
			        parent.saveRamToFile(out);
				}
				else
				{
					int ans = JOptionPane.showConfirmDialog(null, "" + out.getName() + " exists. Overwrite?", "Overwrite existing File", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (ans == JOptionPane.OK_OPTION)
					{
						parent.saveRamToFile(out);
					}
				}
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "An Error has occured \n" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	void updateSize() 
	{
		//Move Buttons
		save.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),save.getY(),save.getWidth(),save.getHeight());
		load.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),load.getY(),load.getWidth(),load.getHeight());
		reset.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),reset.getY(),reset.getWidth(),reset.getHeight());
		toRAM.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),toRAM.getY(),toRAM.getWidth(),toRAM.getHeight());
		run.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),run.getY(),run.getWidth(),run.getHeight());
		step.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),step.getY(),step.getWidth(),step.getHeight());
		stop.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),stop.getY(),stop.getWidth(),stop.getHeight());
		slider.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),slider.getY(),slider.getWidth(),slider.getHeight());
		sliderLabel.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),sliderLabel.getY(),sliderLabel.getWidth(),sliderLabel.getHeight());
//		field.setBounds(this.getWidth()-(BUTTON_SPACER+BUTTON_WIDTH),field.getY(),field.getWidth(),field.getHeight());
		scroll1.setBounds(this.getWidth()-(AREA_WIDTH+BUTTON_SPACER+BUTTON_WIDTH),scroll1.getY(),scroll1.getWidth(),this.getHeight());
		scroll1.revalidate();
		view.updateSize();
	}

	int getSliderValue()
	{
		return slider.getValue();
	}
	
}