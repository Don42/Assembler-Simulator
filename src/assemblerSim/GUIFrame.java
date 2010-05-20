package assemblerSim;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class creates the GUI Frame and the GUI elements.
 * @author Simon Alexander Skolik; Marco "Don" Kaulea
 * 
 */
public class GUIFrame extends JFrame
{
	//GUI size constants
	private static final int BUTTON_HEIGHT = 30;
	private static final int CONSOLE_STD_HEIGHT = 100;
	private static final int CONSOLE_TO_CODE_MARGIN = 10;
	private static final int CODE_FIELD_WIDTH = 130;
	private final static int BUTTON_WIDTH = 100;
	private final static int BUTTON_SPACER = 15;

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
	private JLabel sliderLabel = new JLabel("Delay");
	private JSlider slider = new JSlider(JSlider.VERTICAL,30,120,75);
	private JTextArea area = new JTextArea();
	private JTextArea console = new JTextArea();
	private JScrollPane scroll1, scroll2;
	
	
	/**
	 * This is the Constructor in which the Elements of the GUI are created and placed within the frame.
	 */
	public GUIFrame(View nView, Controller nParent)
	{ 
		super("VonNeumann-Simulator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1024,768)); 
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.setPreferredSize(env.getMaximumWindowBounds().getSize());
		this.setSize(this.getPreferredSize());
		this.setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH);
		
		this.addComponentListener(new ResizeListener(this));
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("images/simulator_LOGO_icon_sq.png"));
		
		view = nView;
		parent = nParent;
		
		// Save-Button
		add(save);
		save.setBounds(this.getWidth()-BUTTON_WIDTH,30,BUTTON_WIDTH,BUTTON_HEIGHT);
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
		load.setBounds(this.getWidth()-BUTTON_WIDTH,0,BUTTON_WIDTH,BUTTON_HEIGHT);
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadRamFromFile();
			}
		});
		
		// Reset-Button
		add(reset);
		reset.setBounds(this.getWidth()-BUTTON_WIDTH,180,BUTTON_WIDTH,BUTTON_HEIGHT);
		reset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.reset();
			}
		});
		
		// Run-Button
		add(run);
		run.setBounds(this.getWidth()-BUTTON_WIDTH,90,BUTTON_WIDTH,BUTTON_HEIGHT);
		run.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.run();
			}
		});
		
		// Button to write Input frome the Code Area to RAM
		add(toRAM);
		toRAM.setBounds(this.getWidth()-BUTTON_WIDTH,60,BUTTON_WIDTH,BUTTON_HEIGHT);
		toRAM.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.setRAM();
			}
		});
		
		// Step-Button
		add(step);
		step.setBounds(this.getWidth()-BUTTON_WIDTH,120,BUTTON_WIDTH,BUTTON_HEIGHT);
		step.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.step();
			}
		});
		
		// Stop-Button
		add(stop);
		stop.setBounds(this.getWidth()-BUTTON_WIDTH,150,BUTTON_WIDTH,BUTTON_HEIGHT);
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
				parent.setStepTime(slider.getValue());
			}
		});
		
		// Program Code Field
		area.setLineWrap(true);
		area.setMargin(new Insets(area.getMargin().top+2, area.getMargin().left+2, area.getMargin().bottom+1, area.getMargin().right+1));
		scroll1 = new JScrollPane(area);
		add(scroll1);
		scroll1.setBounds(this.getWidth()-(CODE_FIELD_WIDTH+BUTTON_SPACER+BUTTON_WIDTH),0,CODE_FIELD_WIDTH,this.getHeight()-(CONSOLE_STD_HEIGHT+CONSOLE_TO_CODE_MARGIN));

		// Console
		console.setLineWrap(true);
		scroll2 = new JScrollPane(console);
		console.setMargin(new Insets(2,2,1,1));
		add(scroll2);
		scroll2.setBounds(0,this.getHeight()-100,this.getWidth(),CONSOLE_STD_HEIGHT);
		console.setEditable(false);
		
		// The Von-Neumann Animation
		add(view);
	}
	
	/**
	 * @return Assembler-Code of the Program Code Field
	 */
	String eingabeCodeUebergabe()
	{
		return area.getText();
	}
	
	/**
	 * Sets the text in the codefield
	 * @param in text to set the codefield to
	 */
	protected void setCodeArea(String in)
	{
		this.area.setText(in); 
	}
	
	/**
	 * Returns the content of the codefield
	 * @return content of the codefield
	 */
	protected String getTextFromCodeArea()
	{
		return this.area.getText();
	}
	
	/**
	 * appends a provided string to the console
	 * @param nEvent String to be appended to the console
	 */
	protected void appendEvent(String nEvent)
	{
		console.append(nEvent);
		console.setCaretPosition(console.getText().length());
	}
	
	/**
	 * clears the console
	 */
	protected void clearEvents()
	{
		console.setText("");
	}
	
	/**
	 * de/activates buttons to reflect the running status
	 */
	void run()
	{
		step.setEnabled(false);
		run.setEnabled(false);
		reset.setEnabled(false);
		toRAM.setEnabled(false);
		stop.setEnabled(true);
	}
	
	/**
	 * de/activates buttons to reflect the stoped status
	 */
	void stop()
	{
		step.setEnabled(true);
		run.setEnabled(true);
		reset.setEnabled(true);
		toRAM.setEnabled(true);
		stop.setEnabled(false);
	}
	
	/**
	 * Open FileChooser and supply the controller with a file object to read
	 */
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
	
	/**
	 * Open FileChooser and supply the controller with a file object to write to
	 */
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
			        parent.saveCodeToFile(out);
				}
				else
				{
					int ans = JOptionPane.showConfirmDialog(null, "File " + out.getName() + " exists. Overwrite?", "Overwrite existing File", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (ans == JOptionPane.OK_OPTION)
					{
						parent.saveCodeToFile(out);
					}
					else
					{
						JOptionPane.showMessageDialog(this, "Datei wurde nicht gespeichert.");
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

	/**
	 * Update the size and position of all graphic elements inside the Frame; call
	 */
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
		scroll1.setBounds(this.getWidth()-(CODE_FIELD_WIDTH+BUTTON_SPACER+BUTTON_WIDTH),scroll1.getY(),CODE_FIELD_WIDTH,this.getHeight()-(CONSOLE_STD_HEIGHT+CONSOLE_TO_CODE_MARGIN));	
		Dimension pictureSize = view.updateSize((CODE_FIELD_WIDTH+BUTTON_SPACER+BUTTON_WIDTH),CONSOLE_STD_HEIGHT+CONSOLE_TO_CODE_MARGIN);
		scroll1.setBounds((int)(pictureSize.getWidth())+5,scroll1.getY(),(int)(this.getWidth()-(pictureSize.getWidth()+BUTTON_SPACER+BUTTON_WIDTH+5)),this.getHeight()-40);//code-fu
		scroll2.setBounds(1,(int)(this.getHeight()-(this.getHeight()-pictureSize.getHeight())),scroll1.getX()-5,(int)(this.getHeight()-(pictureSize.getHeight()+40)));//definitely code-fu
		scroll1.revalidate();
		scroll2.revalidate();
	}

	/**
	 * @return the value of the slider
	 */
	int getSliderValue()
	{
		return slider.getValue();
	}
	
}