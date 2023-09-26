package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

/**
 * the print instruction
 * @author jolin qiu
 */
public class Print implements Instruction {

    /** the instruction stack */
    private final InstructionStack stack;

    /**
     * Create a new instruction
     * @param machine the machine
     */
    public Print(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the top operand off the stack and prints the resulting value.
     */
    @Override
    public void execute() {
        System.out.println(stack.pop());
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return (str) a short string describing what this instruction will do
     */
    @Override
    public String toString(){
        return Maquina.PRINT;
    }
}
