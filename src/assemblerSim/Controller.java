package assemblerSim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;;

public class Controller
{

	/*
	 * Variables
	 */
	
	Clock clock;
	Interpreter interpreter;
	VonNeumannRechner rechner;
	View view;
	GUIFrame frm;
	
	public Controller(int nramSize)
	{
		
		interpreter = new Interpreter();
		rechner = new VonNeumannRechner(this, nramSize);
		view = new View(this);
		clock = new Clock(rechner);
		frm = new GUIFrame(view, this);
	}
	
	public void step()
	{
	clock.step();
	}
	
	public void run()
	{
		clock.run();
	}
	
	public void halt() 
	{
		clock.halt();
	}
	
	public void setStepTime(int nStepTime)
	{
		clock.setStepTime(nStepTime);
	}
	
	public void setRegister(int nregister, int nvalue)
	{
		view.setRegister(nregister, nvalue);
	}
	
	public void loadRamFromFile(File nfile) throws Exception
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

	public void saveRamToFile(File nfile) throws Exception
	{
		String tRam;
		FileWriter wrt = new FileWriter(nfile);
		BufferedWriter bwriter = new BufferedWriter(wrt);
		
		tRam = interpreter.ramToString(rechner.getRam());
		for(int i = 0; i<rechner.getRamSize(); i++)
		{
			bwriter.write(tRam,0,tRam.length());
		}
	}

	protected int[] interpretInput(String input)
	{
		return interpreter.stringToRam(input);
		
	}
	
	protected void setRAM(int[] nRAM)
	{
		rechner.setRam(nRAM);
	}
	
	protected void setCodeArea(String string)
	{
		frm.setCodeArea(string);
	}
	
	protected void updateRAMAnimation(int[] input)
	{
		frm.updateRAMAnimation(input);
	}

	protected void reset() 
	{
		rechner.reset();
		
	}

}
