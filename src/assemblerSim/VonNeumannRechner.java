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
	
	public VonNeumannRechner(Controller nController, int nramSize)
	{
		controller = nController;
		ram = new int[nramSize];
		nextStep = 1;
	}
	
	protected void setRam(int[] nram)
	{
		ram = nram;
		controller.updateRAMAnimation(ram);
	}
	public void step()
	{
		switch(nextStep)
		{
			case STEP_FETCH:
				controller.setCycleDisplay("FETCH");
				fetch();
				break;
			case STEP_DECODE:
				controller.setCycleDisplay("DECODE");
				decode();
				break;
			case STEP_INDIRECT:
				controller.setCycleDisplay("INDIRECT");
				indirect();
				break;
			case STEP_EXECUTE:
				controller.setCycleDisplay("EXECUTE");
				execute();
				break;
		}
	}
	protected void fetch()
	{
		instructionRegister = ram[programCounter];	//line 6	line 2
		controller.setRegister(INSTRUCTIONREGISTER, instructionRegister);	//line 5
		nextStep = STEP_DECODE;
	}
	
	protected void decode()
	{
		nextStep = STEP_INDIRECT;	

		instruction = Integer.rotateRight(instructionRegister, 24)&255;
		
		addressRegister = instructionRegister&16777215%ram.length;	//line 11
		controller.setRegister(ADRESSREGISTER, addressRegister);
	}
	
	protected void indirect()
	{
		nextStep = STEP_EXECUTE;
		if((Integer.rotateRight(instructionRegister,23)&1)==1)
		{
			//direct addressing:

			valueRegister = ram[addressRegister];	//line 2
			controller.setRegister(VALUEREGISTER, valueRegister);	//line 4
		}
		else
		{
			//immediate addressing
			valueRegister = addressRegister;
			controller.setRegister(VALUEREGISTER, valueRegister);
		}
	}
	
	protected void execute()
	{
		nextStep = STEP_FETCH;
		
		Opcodes command = Opcodes.values()[instruction];
		
		switch (command)
		{
		case HALT:
			nextStep = STEP_HALT;
			controller.setCycleDisplay("HALT");
			controller.halt();
			break;
		case LOAD:
			accumulator = ram[addressRegister];
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case STORE:
			ram[addressRegister] = accumulator;
			controller.updateRAMAnimation(ram);
			increaseProgrammCounter();
			break;
		case ADD:
			accumulator = accumulator + valueRegister;
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case SUB:
			accumulator = accumulator - valueRegister;
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case MULT:
			accumulator = accumulator * valueRegister;
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case DIV:
			accumulator = accumulator / valueRegister;
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case MOD:
			accumulator = accumulator % valueRegister;
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case AND:
			accumulator = accumulator & valueRegister;
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case OR:
			accumulator = accumulator | valueRegister;
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case NOT:
			accumulator = ~accumulator;
			controller.setRegister(ACCUMULATOR, accumulator);
			increaseProgrammCounter();
			break;
		case JMP:
			programCounter = valueRegister%ram.length;
			controller.setRegister(PROGRAMMCOUNTER, programCounter);	
			break;
		case JMPEQ:
			if(accumulator == 0)
			{
				programCounter = valueRegister%ram.length;
				controller.setRegister(PROGRAMMCOUNTER, programCounter);					
			}
			else
			{
				increaseProgrammCounter();
			}
			break;
		case JMPNE:
			if(accumulator != 0)
			{
				programCounter = valueRegister%ram.length;
				controller.setRegister(PROGRAMMCOUNTER, programCounter);					
			}
			else
			{
				increaseProgrammCounter();
			}
			break;
		case JMPGT:
			if(accumulator > 0)
			{
				programCounter = valueRegister%ram.length;	
				controller.setRegister(PROGRAMMCOUNTER, programCounter);				
			}
			else
			{
				increaseProgrammCounter();
			}
			break;
		case JMPLT:
			if(accumulator < 0)
			{
				programCounter = valueRegister%ram.length;
				controller.setRegister(PROGRAMMCOUNTER, programCounter);					
			}
			else
			{
				increaseProgrammCounter();
			}
			break;
		case JMPGE:
			if(accumulator >= 0)
			{
				programCounter = valueRegister%ram.length;	
				controller.setRegister(PROGRAMMCOUNTER, programCounter);			
			}
			else
			{
				increaseProgrammCounter();
			}
		case JMPLE:
			if(accumulator <= 0)
			{
				programCounter = valueRegister%ram.length;	
				controller.setRegister(PROGRAMMCOUNTER, programCounter);				
			}
			else
			{
				increaseProgrammCounter();
			}
			break;
		case NOP:
		default:
			increaseProgrammCounter();
			break;
				
		}
	}

	protected int getRamSize()
	{
		return ram.length;
	}

	protected int[] getRam()
	{
		return ram;
	}
	public void increaseProgrammCounter()
	{
		if(programCounter < (ram.length-1))
		{		
			programCounter++;
			controller.setRegister(PROGRAMMCOUNTER, programCounter);
		}
		else
		{
			nextStep = STEP_HALT;
			controller.halt();
			controller.setCycleDisplay("HALT");
		}
	}

	protected void reset() 
	{
		
		accumulator=0;
		valueRegister=0;
		instructionRegister=0;
		programCounter=0;
		addressRegister=0;
		stackPointer=0;
		controller.setRegister(ACCUMULATOR, accumulator);
		controller.setRegister(VALUEREGISTER, valueRegister);
		controller.setRegister(INSTRUCTIONREGISTER, instructionRegister);
		controller.setRegister(PROGRAMMCOUNTER,programCounter);
		controller.setRegister(ADRESSREGISTER,addressRegister);
		controller.setRegister(STACKPOINTER,stackPointer);
		setRam(new int[ram.length]);
		nextStep = STEP_FETCH;
		controller.setCycleDisplay("FETCH");

	}
}
