package machine.instructions;

import common.Errors;
import machine.InstructionStack;
import machine.Maquina;

/**
 * The SQUAREROOT instruction
 * @author jolin qiu
 */
public class SquareRoot implements Instruction{

    /**
     * the instruction stack
     */
    private final InstructionStack stack;

    /**
     *  creates new instruction
     * @param machine the machine
     */
    public SquareRoot(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the operand off the stack, and pushes the integer result of
     * taking the square root of it.
     */
    @Override
    public void execute() {
        int value = stack.pop();
        // Attempting to take the square root of a negative number.
        if (value < 0){
            Errors.report(Errors.Type.NEGATIVE_SQUARE_ROOT);
        } else {
            stack.push((int)Math.sqrt(value));

        }
    }

    /**
     * returns the string representation of square root
     * @return (str) "SQRT"
     */
    @Override
    public String toString() {
        return Maquina.SQUARE_ROOT;
    }
}
