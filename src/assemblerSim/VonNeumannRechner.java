package assemblerSim;

public class VonNeumannRechner
{
	/*
	 * Variables
	 */
	Controller controller;
	/*
	 * Program Variables
	 */
	int accumulator;
	int valueRegister;
	int instructionRegister;
	int programCounter;
	int addressRegister;
	int stackPointer;
	int[] ram;
	
	int instruction;
	
	public final static int ACCUMULATOR = 0;
	public final static int VALUEREGISTER = 1;
	public final static int INSTRUCTIONREGISTER = 2;
	public final static int PROGRAMMCOUNTER = 3;
	public final static int ADRESSREGISTER = 4;
	public final static int STACKPOINTER = 5;
	
	
	int nextStep;	
	public final static int STEP_HALT = 0;
	public final static int STEP_FETCH = 1;
	public final static int STEP_DECODE = 2;
	public final static int STEP_INDIRECT = 3;
	public final static int STEP_EXECUTE = 4;
	
	/*
	 * INSTRUCTIONS
	 */
	public final static int NOP =  0;
	public final static int HALT =  1;
	public final static int LOAD =  2;
	public final static int STORE =  3;
	public final static int ADD =  4;
	public final static int SUB =  5;
	public final static int DIV =  6;
	public final static int MULT =  7;
	public final static int MOD =  8;
	public final static int AND =  9;
	public final static int OR = 10;
	public final static int NOT =  11;
	public final static int JMP =  12;
	public final static int JMPEQ =  13;
	public final static int JMPGT =  14;
	public final static int JMPLT =  15;
	public final static int JMPGE =  16;
	public final static int JMPLE =  17;

	public VonNeumannRechner(int nramSize)
	{
		// TODO Auto-generated constructor stub
	}
	
	protected void setRam(int[] nram)
	{
		ram = nram;
	}
	public void step()
	{
		switch(nextStep)
		{
			case STEP_FETCH:
				fetch();
				break;
			case STEP_DECODE:
				decode();
				break;
			case STEP_INDIRECT:
				indirect();
				break;
			case STEP_EXECUTE:
				execute();
				break;
		}
	}
	protected void fetch()
	{
		instructionRegister = ram[programCounter];
		controller.setRegister(INSTRUCTIONREGISTER, instructionRegister);
		nextStep = STEP_DECODE;
	}
	
	protected void decode()
	{
		nextStep = STEP_INDIRECT;	

		instruction = Integer.rotateRight(instructionRegister, 24);
	}
	
	protected void indirect()
	{
		nextStep = STEP_EXECUTE;		
	}
	
	protected void execute()
	{
		nextStep = STEP_FETCH;
		
		
		switch (instruction)
		{
		case HALT:
			nextStep = STEP_HALT;
			break;
		case LOAD:
			accumulator = ram[addressRegister];
			break;
		case STORE:
			ram[addressRegister] = accumulator;
			break;
		case ADD:
			accumulator = accumulator + valueRegister;
			break;
		case SUB:
			accumulator = accumulator - valueRegister;
			break;
		case MULT:
			accumulator = accumulator * valueRegister;
			break;
		case DIV:
			accumulator = accumulator / valueRegister;
			break;
		case MOD:
			accumulator = accumulator % valueRegister;
			break;
		case AND:
			accumulator = accumulator & valueRegister;
			break;
		case OR:
			accumulator = accumulator | valueRegister;
			break;
		case NOT:
			accumulator = ~accumulator;
			break;
		case JMP:
			programCounter = valueRegister;
			break;
		case JMPEQ:
			if(accumulator == 0)
			{
				programCounter = valueRegister;				
			}
			break;
		case JMPGT:
			if(accumulator > 0)
			{
				programCounter = valueRegister;				
			}
			break;
		case JMPLT:
			if(accumulator < 0)
			{
				programCounter = valueRegister;				
			}
			break;
		case JMPGE:
			if(accumulator >= 0)
			{
				programCounter = valueRegister;				
			}
		case JMPLE:
			if(accumulator <= 0)
			{
				programCounter = valueRegister;				
			}
			break;
		}
	}
}
