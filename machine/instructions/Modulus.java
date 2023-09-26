package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

/**
 * the MODULUS instruction
 * @author jolin qiu
 */
public class Modulus implements Instruction {

    /**
     * the instruction stack
     */
    private final InstructionStack stack;

    /**
     * creates a new instruction
     * @param machine the machine
     */
    public Modulus(Maquina machine) {
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack, and pushes the
     * result of the first modulus by the second.
     */
    @Override
    public void execute() {
        int secondOperand = stack.pop();
        int firstOperand = stack.pop();
        int value = (firstOperand % secondOperand);
        stack.push(value);
    }


    /**
     * The string representation
     * @return (str) "MOD"
     */
    @Override
    public String toString() {
        return Maquina.MODULUS;
    }
}
