package assemblerSim;

import java.util.HashMap;


/**
 * @author Tim Borcherding
 * Contains methods to translate between opcode and machine language
 */
public class Interpreter
{
	/**
	 * Translates a program to its machinecode representation 
	 * @param assemblerCode The program to translate
	 * @param nRamSize The machinecode representation of the program 
	 * @return
	 */
	protected static int[] stringToRam(String assemblerCode, int nRamSize)
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
	/**
	 * Translates the whole ram to its assemblercode representations
	 * @param nRam The content of the ram
	 * @return The assemblercodes of the ram
	 */
	protected static String ramToString(int[] nRam)
	{		
		String programm = new String();
		for(int i = 0; i < nRam.length; i++)
		{
			programm += opcodeToString(nRam[i])+"\r\n";
		}
		return programm;
	}
	/**
	 * Translates a assemblercode to its machinecode
	 * @param AssemblerCode The assemblercode to translate
	 * @return The translated machinecode
	 */
	protected static int stringToOpcode(String assemblerCode)
	{		
		return stringToOpcode(assemblerCode, null);
		
	}
	/**
	 * Translates a assemblercode to its machinecode
	 * @param AssemblerCode the assemblercode to translate
	 * @param nLabels Defined jumplabels
	 * @return The translated machinecode
	 */
	protected static int stringToOpcode(String assemblerCode, HashMap<String,Integer> nLabels)
	{
		int code;
		int instruction;
		int adresse;
		assemblerCode = assemblerCode.trim();
		String input[] = assemblerCode.split(";")[0].split(" ");
		if(input.length == 1 && input[0].length() == 0)
		{
			code = 0;
		}
		else
		{
			try
			{
				try
				{
					instruction = Opcodes.valueOf(input[0].toUpperCase()).ordinal()*0x1000000;
					if(input.length>1)
					{
						try
						{
							adresse = Integer.parseInt(input[1]);
							if(adresse < 0 || adresse > 0xFFFFFF)
							{
								throw new Exception();
							}
						}
						catch(NumberFormatException e)
						{
							adresse = nLabels.get(input[1].toUpperCase());					
						}
					}
					else
					{
						adresse = 0;
					}
				}
				catch(IllegalArgumentException e)
				{
					instruction = 0;
					try
					{
						adresse = Integer.parseInt(input[0]);
					}
					catch(NumberFormatException e2)
					{
						adresse = nLabels.get(input[0].toUpperCase());					
					}				
				}
				code = instruction+adresse;
				
			}
			catch(Exception e)
			{
				code = 0xABADC0DE;			
			}
		}
		return code;
	}
	/**
	 * Translates a machinecode to its assemblercode representations
	 * @param ramCell The machinecode to translate
	 * @return The assemblercode of the input
	 */
	protected static String opcodeToString(int ramCell)

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

