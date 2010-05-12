package assemblerSim;

import java.util.HashMap;


public class Interpreter
{
	public Interpreter()
	{
		// TODO Auto-generated constructor stub
	}
	
	protected int[] stringToRam(String assemblerCode, int nRamSize)
	{
		
		int[] output = new int[nRamSize];
		String[] lines = assemblerCode.split("\n");
		HashMap<String, Integer> labels = new HashMap<String, Integer>();
		//Find Labels and create HashMap:
		for(int i = 0; i < lines.length; i++)
		{
			try{
				lines[i] = lines[i].trim();
				if(lines[i].charAt(0)== ':')
				{
					//Labelname
					String labelName;
					int leerzeichen =  lines[i].indexOf(' ');
					if(leerzeichen != -1)
					{
						labelName  = lines[i].substring(1, lines[i].indexOf(' '));
						lines[i] = lines[i].substring(lines[i].indexOf(' ')+1,lines[i].length());
					}	
					else
					{
						labelName  = lines[i].substring(1, lines[i].length());
						lines[i] = "";
					}
					labels.put(labelName, i);
				}	
			}
			catch(Exception e)
			{
				lines[i] = "";
			}
		}
		
		for(int i = 0; i < lines.length && i < output.length; i++)
		{
			output[i] = stringToOpcode(lines[i],labels);
		}
		return output;
	}
	
	protected String ramToString(int[] nRam)
	{		
		String programm = new String();
		for(int i = 0; i < nRam.length; i++)
		{
			programm += opcodeToString(nRam[i])+"\r\n";
		}
		return programm;
	}
	protected int stringToOpcode(String assemblerCode)
	{		
		return stringToOpcode(assemblerCode, null);
		
	}
	protected int stringToOpcode(String assemblerCode, HashMap<String,Integer> nLabels)
	{
		int code;
		int instruction;
		int adresse;
		assemblerCode = assemblerCode.trim();
		String input[] = assemblerCode.split(";")[0].split(" ");
		
		try
		{
			//guess what this does ;-)
			instruction = Opcodes.valueOf(input[0].toUpperCase()).ordinal()*0x1000000;
		}
		catch(Exception e)
		{
			instruction = 0;
		}
		
		String adressString;		
		boolean indirectAdressMode = false;
		try
		{
			if(instruction !=0)
			{
				if(input[1].indexOf('[') == 0)
				{
					indirectAdressMode = true;
					adressString = input[1].substring(input[1].indexOf('[')+1, input[1].indexOf(']'));
				}
				else
				{
					adressString = input[1];
				}
			}
			else
			{
				adressString = input[0];
			}
		}
		catch(Exception e)
		{
			adressString = "0";
		}
		try{
			adresse  =  Integer.parseInt(adressString);
		}
		catch(NumberFormatException e)
		{
			try{
			adresse  = nLabels.get(adressString);
			}
			catch(Exception e2)
			{
				adresse = 0;
			}
		}
		if(indirectAdressMode)
		{
			adresse += 0x800000;
		}
		/*
		try
		{
			if(instruction !=0)
			{
				if(input[1].indexOf('[') == 0)
				{
					adressString = input[1].substring(input[1].indexOf('[')+1, input[1].indexOf(']'));
					//adresse  =  Integer.parseInt(input[1].substring(input[1].indexOf('[')+1, input[1].indexOf(']')))+8388608;						
				}
				else
				{
					adresse = Integer.parseInt(input[1]);
				}
			}
			else
			{
				adresse = Integer.parseInt(input[0]);
			}
		}
		catch(Exception e)
		{
			adresse = 0;
		}*/
		try{
			
		}
		catch(Exception e)
		{
			
		}
		code = instruction+adresse;
		return code;
	}
	
	protected String opcodeToString(int ramCell)

	{
		int instruction = Integer.rotateRight(ramCell, 24)&0xFF;
		String instructionString = Opcodes.values()[instruction].toString();
		if((Integer.rotateRight(ramCell,23)&1)==1)
		{
			instructionString += " ["+(ramCell&0x7FFFFF)+"]";
		}
		else
		{
			instructionString += " "+(ramCell&0x7FFFFF);
		}
		return instructionString;
	}
}

