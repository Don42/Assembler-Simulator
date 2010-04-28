package assemblerSim;

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
	private JLabel label = new JLabel("----- Clock in ms -----");
	private JSlider slider = new JSlider(JSlider.HORIZONTAL,1,5000,1000);
	private JTextField field = new JTextField(String.valueOf(slider.getValue()));
	private JTextArea area = new JTextArea();
	private JScrollPane scroll;
	
	
	/**
	 * This is the Constructor in which the Elements of the GUI are created and placed within the frame.
	 */
	public GUIFrame(View nView, Controller nParent)
	{ 
		super("VonNeumann-Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100,628);
		
		view = nView;
		parent = nParent;
		
		// Save-Button
		add(save);
		save.setBounds(958,15,127,30);
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
		load.setBounds(815,15,127,30);
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadRamFromFile();
			}
		});
		
		// Run-Button
		add(run);
		run.setBounds(815,60,80,30);
		run.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				run();
			}
		});
		
		// Step-Button
		add(step);
		step.setBounds(910,60,80,30);
		step.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				step();
			}
		});
		
		// Stop-Button
		add(stop);
		stop.setBounds(1005,60,80,30);
		stop.setEnabled(false);
		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				stop();
			}
		});
		
		// Label for the clock elements
		add(label);
		label.setBounds(880,105,140,20);
		
		// Clock-Slider
		add(slider);
		slider.setBounds(815,125,200,50);
		slider.setMajorTickSpacing(1000);
		slider.setMinorTickSpacing(500);
		slider.setPaintTicks(true);
		slider.setPaintLabels(false);
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				field.setText(String.valueOf(slider.getValue()));
				parent.setStepTime(slider.getValue());
			}
		});
		
		// Clock-JTextField
		add(field);
		field.setBounds(1030,135,55,30);
		field.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				slider.setValue(Integer.parseInt(field.getText()));
			}
		});
		
		// Program Code Field
		area.setLineWrap(true);
		scroll = new JScrollPane(area);
		add(scroll);
		scroll.setBounds(815,190,270,398);
		
		// The Von-Neumann Animation
		add(view);
		
		// makes the GUI visible
		setVisible(true);
	}
	
	/**
	 * @return Assembler-Code of the Program Code Field
	 */
	public String eingabeCodeUebergabe()
	{
		return area.getText();
	}
	
	private void setRAM()
	{
		String input = area.getText();
		int[] tRAM = parent.interpretInput(input);
		parent.setRAM(tRAM);
	}
	
	protected void updateRAMAnimation(int[] nRAM)
	{
		int[] tRAM = nRAM;
		String output = "";
		
		for(int i = 0; i<tRAM.length;i++)
		{
			String tOut =  ""+tRAM[i];
			String cell = ""+i;
			while(tOut.length()<10)
			{
				tOut = "0" + tOut;
			}
			
			while(cell.length()<3)
			{
				cell = "0"+cell;
			}
			output = output + cell + " | " + tOut+"\n";
		}
		view.setRAMAnimation(output);
	}
	
	private void run()
	{
		setRAM();
		area.setText(null);
		step.setEnabled(false);
		run.setEnabled(false);
		stop.setEnabled(true);
		parent.setStepTime(slider.getValue());
		parent.run();
	}
	
	private void step()
	{
		parent.setStepTime(slider.getValue());
		parent.step();
	}
	
	private void stop()
	{
		parent.halt();
		step.setEnabled(true);
		run.setEnabled(true);
		stop.setEnabled(false);
	}
	
	public void loadRamFromFile()
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
				JOptionPane.showMessageDialog(this, "An Error has occured /n" + e.getMessage());
				e.printStackTrace();
			}
        }
	}
	
	public void saveRamToFile()
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
				JOptionPane.showMessageDialog(this, "An Error has occured /n" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
}