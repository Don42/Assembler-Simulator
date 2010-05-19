package assemblerSim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;;

/**
 * @author Marco "Don" Kaulea
 * Objects of this class act as an intermediary between the GUI and the virtual maschine
 */
public class Controller
{

	/*
	 * Variables
	 */
	
	Clock clock;
	VonNeumannRechner rechner;
	View view;
	GUIFrame frm;
	
	
	/**
	 * @param nramSize The amount of ram the virtual maschine should have; in 32-bit Cells.
	 * Constructor creates object for the GUI and virtual maschine;
	 * initializes necessary variables and sets the GUI visible. 
	 */
	public Controller(int nramSize)
	{
		
		rechner = new VonNeumannRechner(this, nramSize);
		view = new View(this);
		clock = new Clock(rechner);
		frm = new GUIFrame(view, this);
		rechner.setRam(new int[nramSize]);
		setCycleDisplay("FETCH");
		frm.setVisible(true);	//after everything is created show the GUI
	}
	
	/**
	 * This function executes the next step in the virtual maschine.
	 */
	protected void step()
	{
	clock.step();
	}
	
	/**
	 * Begins to run the virtual maschine continuosly with a delay between steps defined by frm.getSliderValue
	 */
	protected void run()
	{
		setStepTime(frm.getSliderValue());
		frm.run();
		clock.run();
	}
	
	/**
	 * Stops the virtual maschine
	 */
	protected void halt() 
	{
		frm.stop();
		clock.halt();
	}
	
	/**
	 * Sets the delay between steps.
	 * nStepTime is converted to miliseconds using an exponential function. 
	 * This way it is easier to use the slider for small values.
	 * @param nStepTime Value of time to set
	 */
	protected void setStepTime(int nStepTime)
	{
		clock.setStepTime((int)Math.pow(2, (nStepTime/10))+2);//+2 to get to 10ms in lowest setting 2³ + 2=10
	}
	
	/**
	 * Sets the variables in the GUI that are used to display the register fields
	 * @param nregister	Defines which Register to set
	 * @param nvalue The value to which the choosen register ist set
	 */
	protected void setRegister(int nregister, int nvalue)
	{
		view.setRegister(nregister, nvalue);
	}
	
	/**
	 * Sets the Codearea in the GUI to the content of the given file
	 * @param nfile is the file to read
	 * @throws Exception Throws exceptions from FileReader and BufferedReader
	 */
	protected void loadRamFromFile(File nfile) throws Exception
	{
		StringBuilder tString = new StringBuilder(512);
		FileReader reader = new FileReader(nfile);
		BufferedReader breader = new BufferedReader(reader);
		
		while(breader.ready())
		{
			tString.append(breader.readLine()+"\n");
		}
		setCodeArea(tString.toString());
	}

	/**
	 * Writes the Code from the Codearea in the GUI to the given file.
	 * @param nfile is the file to write to
	 * @throws Exception Throws exceptions from FileWriter and BufferedWriter
	 */
	protected void saveCodeToFile(File nfile) throws Exception
	{
		String tCode = frm.getTextFromCodeArea();
		FileWriter wrt = new FileWriter(nfile);
		BufferedWriter bwriter = new BufferedWriter(wrt);

		bwriter.write(tCode);
		bwriter.flush();
		bwriter.close();
		wrt.close();
	}

	/**
	 * Sends a provided String to the interpreter for interpetation and returns the resulting maschinecode
	 * @param input String to interpret
	 * @return int-array the size of ram that represents the String in maschinecode 
	 */
	protected int[] interpretInput(String input)
	{
		return Interpreter.stringToRam(input, rechner.getRamSize());
		
	}
	
	/**
	 * Assemble the Code from Codearea and write resulting maschinecode to ram
	 */
	protected void setRAM()
	{
		this.reset();
		String input = frm.getTextFromCodeArea();
		int[] tRAM = interpretInput(input);
		rechner.setRam(tRAM);
	}
	
	/**
	 * Sets the Codearea in the GUI
	 * @param string to set the CodeArea to
	 */
	protected void setCodeArea(String string)
	{
		frm.setCodeArea(string);
	}
	
	/**
	 * Converts all RAM values to String; combines the to one and formats them
	 * @param nRAM int-array that represents the RAM
	 */
	protected void updateRAMAnimation(int[] nRAM)
	{
		int[] tRAM = nRAM;
		String output = "";
		
		for(int i = 0; i<tRAM.length;i++)
		{
			String tOut =  ""+ Integer.toHexString(tRAM[i]).toUpperCase();
			String cell = ""+i;
			while(tOut.length()<8)
			{
				tOut = "0" + tOut;
			}
			
			while(cell.length()<3)
			{
				cell = "0"+cell;
			}
			
			output = output + "  " + cell + " | " + tOut+" | " + Interpreter.opcodeToString(tRAM[i]) +"\n";
		}
		view.updateRAMAnimation(output);
	}

	/**
	 * resets the virtual maschine and the GUI
	 */
	protected void reset() 
	{
		rechner.reset();
		frm.clearEvents();
	}
	
	/**
	 * Sets the variable in the GUI that defines which part of the animation to display
	 * @param nLine defines which line to set
	 */
	protected void setLine(int nLine)
	{
		view.setLine(nLine);
	}
	
	/**
	 * Sets the variable in the GUI that defines what instruction cycle to display
	 * @param nCycle defines which instruction cycle to set
	 */
	protected void setCycleDisplay(String nCycle)
	{
			view.setCycle(nCycle);
	}
	
	/**
	 * Appends a given String to the console field in the GUI
	 * @param nEvent the String to append
	 */
	protected void appendEvent(String nEvent)
	{
		frm.appendEvent(nEvent);
	}

}
