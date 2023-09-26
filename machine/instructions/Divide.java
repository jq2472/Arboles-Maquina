package machine.instructions;

import common.Errors;
import machine.InstructionStack;
import machine.Maquina;

/**
 * the DIVIDE instruction
 * @author jolin qiu
 */
public class Divide implements Instruction{

    /**
     * the instruction stack
     */
    private final InstructionStack stack;

    /**
     * creates a new instruction
     * @param machine the machine
     */
    public Divide(Maquina machine) {
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack, and
     * pushes the result of the first divided by the second.
     */
    @Override
    public void execute() {
        int secondOperand = stack.pop();
        int firstOperand = stack.pop();
        // Attempting to divide by a zero denominator.
        if (secondOperand == 0){
            Errors.report(Errors.Type.DIVIDE_BY_ZERO);
        } else {
            int value = (firstOperand / secondOperand);
            stack.push(value);
        }
    }

    /**
     * The string representation
     * @return (str) "DIV"
     */
    @Override
    public String toString() {
        return Maquina.DIVIDE;
    }

}
