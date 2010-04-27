package assemblerSim;

public enum Opcodes {

	NOP(0,"NOP"),
	HALT(1,"HALT"),
	LOAD(2,"LOAD"),
	STORE(3,"STORE"),
	ADD(4,"ADD"),
	SUB(5,"SUB"),
	DIV(6,"DIV"),
	MULT(7,"MULT"),
	MOD(8,"MOD"),
	AND(9,"AND"),
	OR(10,"OR"),
	NOT(11,"NOT"),
	JMP(12,"JMP"),
	JMPEQ(13,"JMPEQ"),
	JMPGT(14,"JMPGT"),
	JMPLT(15,"JMPLE"),
	JMPGE(16,"JMPGE"),
	JMPLE(17,"JMPLE");
	
	private final int code;
    private final String instruction;
    
    Opcodes(int nCode, String nInstruction)
    {
        this.code = nCode;
        this.instruction = nInstruction;
    }	
}
