package assemblerSim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;;

/**
 * @author Marco "Don" Kaulea
 * 
 */
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
		rechner.setRam(new int[nramSize]);
		setCycleDisplay("FETCH");
		frm.setVisible(true);
	}
	
	public void step()
	{
	clock.step();
	}
	
	protected void run()
	{
		setStepTime(frm.getSliderValue());
		frm.run();
		clock.run();
	}
	
	protected void halt() 
	{
		frm.stop();
		clock.halt();
	}
	
	public void setStepTime(int nStepTime)
	{
		clock.setStepTime((int)Math.pow(2, (nStepTime/10))+2);//+2 to get to 10ms in lowest setting 2³ + 2=10
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
		String tCode = frm.getTextFromCodeArea();
		FileWriter wrt = new FileWriter(nfile);
		BufferedWriter bwriter = new BufferedWriter(wrt);

		bwriter.write(tCode);
		bwriter.flush();
		bwriter.close();
		wrt.close();
	}

	protected int[] interpretInput(String input)
	{
		return interpreter.stringToRam(input, rechner.getRamSize());
		
	}
	
	protected void setRAM()
	{
		this.reset();
		String input = frm.getTextFromCodeArea();
		int[] tRAM = interpretInput(input);
		rechner.setRam(tRAM);
	}
	
	protected void setCodeArea(String string)
	{
		frm.setCodeArea(string);
	}
	
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
			
			output = output + "  " + cell + " | " + tOut+" | " + interpreter.opcodeToString(tRAM[i]) +"\n";
		}
		view.updateRAMAnimation(output);
	}

	protected void reset() 
	{
		rechner.reset();
		frm.clearEvents();
	}
	
	protected void setLine(int nLine)
	{
		view.setLine(nLine);
	}
	
	protected void setCycleDisplay(String nCycle)
	{
			view.setCycle(nCycle);
	}
	
	protected void appendEvent(String nEvent)
	{
		frm.appendEvent(nEvent);
	}

}
