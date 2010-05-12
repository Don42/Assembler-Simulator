package assemblerSim;

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

		for(int i = 0; i < lines.length && i < output.length; i++)
		{
			int code;
			int instruction;
			int adresse;
			String input[] = lines[i].split(";")[0].split(" ");
			try
			{
				//guess what this does ;-)
				instruction = Opcodes.valueOf(input[0]).ordinal()*16777216;
				
			}
			catch(Exception e)
			{
				instruction = 0;
			}
			try
			{
				if(instruction !=0)
				{
					if(input[1].indexOf('[') == 0)
					{
						adresse  =  Integer.parseInt(input[1].substring(input[1].indexOf('[')+1, input[1].indexOf(']')))+8388608;						
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
			}
			
			code = instruction+adresse;
			output[i] = code;
		}
		return output;
	}
	
	protected String ramToString(int[] ram)
	{
		return "";
	}
	
	protected int stringToOpcode(String assemblerCode)
	{
		return 0;
	}
	
	protected String opcodeToString(int nOpcode)
	{
		return "";
	}

}
