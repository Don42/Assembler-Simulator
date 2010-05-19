package assemblerSim;

/**
 * @author Tim Borcherding; Marco "Don" Kaulea
 *
 */
public enum Opcodes {
	/**
	 * direct addressing or no addressing(NOP/HALT/NOT)
	 */
	NOP,
	HALT,
	BREAK,
	LOAD,
	STORE,
	ADD,
	SUB,
	DIV,
	MULT,
	MOD,
	AND,
	OR,
	NOT,
	JMP,
	JMPEQ,
	JMPNE,
	JMPGT,
	JMPLT,
	JMPGE,
	JMPLE,
	/**
	 * immediate addressing
	 */
	LOADM,
	ADDM,
	SUBM,
	DIVM,
	MULTM,
	MODM,
	ANDM,
	ORM,
	JMPM,
	JMPEQM,
	JMPNEM,
	JMPGTM,
	JMPLTM,
	JMPGEM,
	JMPLEM,
	/**
	 * indirect addressing
	 */
	LOADI,
	STOREI,
	ADDI,
	SUBI,
	DIVI,
	MULTI,
	MODI,
	ANDI,
	ORI,
	JMPI,
	JMPEQI,
	JMPNEI,
	JMPGTI,
	JMPLTI,
	JMPGEI,
	JMPLEI
}
