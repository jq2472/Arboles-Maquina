package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

/**
 * The SUBTRACT instruction
 * @author jolin qiu
 */
public class Subtract implements Instruction{

    /**
     * the instruction stack
     */
    private final InstructionStack stack;

    /**
     * creates a new instruciton
     * @param machine the machine
     */
    public Subtract(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack, and pushes the result
     * of the first subtracted by the second.
     */
    @Override
    public void execute() {
        int secondOperand = stack.pop();
        int firstOperand = stack.pop();
        int value = (firstOperand - secondOperand);
        stack.push(value);
    }

    /**
     * The string representation
     * @return (str) "SUB"
     */
    @Override
    public String toString() {
        return Maquina.SUBTRACT;
    }
}
