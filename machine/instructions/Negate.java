package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

/**
 * The NEGATE instruction
 * @author jolin qiu
 */
public class Negate implements Instruction {

    /**
     * The instruction stack
     */
    private final InstructionStack stack;

    /**
     * create new instruction
     * @param machine the machine
     */
    public Negate(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * pops the operand off the stack, and pushes the result of
     * negating it
     */
    @Override
    public void execute() {
        int value = stack.pop();
        stack.push(value * (-1));
    }

    /**
     * string representation of the negation
     * @return (st) NEG
     */
    @Override
    public String toString() {
        return Maquina.NEGATE;
    }
}
