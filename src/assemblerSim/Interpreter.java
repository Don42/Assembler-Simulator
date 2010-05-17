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
					labels.put(labelName.toUpperCase(), i);
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
			try{
				//guess what this does ;-)
				instruction = Opcodes.valueOf(input[0].toUpperCase()).ordinal()*0x1000000;
			}
			catch(IllegalArgumentException e)
			{
				instruction = 0;
			}
			
			String adresseString;
			if(input.length==1 && instruction == 0 && !input[0].equalsIgnoreCase("NOP") && !input[0].equals(""))
			{
				adresseString = input[0];
			}
			else if(input.length==2)
			{
				adresseString = input[1];
			}
			else
			{
				adresseString = "0";
			}
			try
			{
				adresse = Integer.parseInt(adresseString);				
			}
			catch(NumberFormatException e)
			{
				adresse = nLabels.get(adresseString.toUpperCase());
			}
			code = instruction+adresse;
		}
		catch(Exception e)
		{
			code = 0xABADC0DE;
		}
		return code;
	}
	
	protected String opcodeToString(int ramCell)

	{
		int instruction = Integer.rotateRight(ramCell, 24)&0xFF;
		String instructionString;
		try{
			instructionString = Opcodes.values()[instruction].toString();			
		}
		catch(IndexOutOfBoundsException e)
		{
			instructionString = "NOP";
		}
		instructionString += " "+(ramCell&0xFFFFFF);
		return instructionString;
	}
}

