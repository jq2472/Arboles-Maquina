package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

/**
 * The ADD instruction
 * @author jolin qiu
 */
public class Add implements Instruction {

    /**
     * the instruction stack
     */
    private final InstructionStack stack;
    /**
     * create a new instruction
     * @param machine the machine
     */
    public Add(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack,
     * and pushes the result of the first added by the second.
     */
    @Override
    public void execute() {
        int secondOperand = stack.pop();
        int firstOperand = stack.pop();
        int value = (firstOperand + secondOperand);
        stack.push(value);
    }

    /**
     * returns the string representation
     * @return (str) "ADD"
     */
    @Override
    public String toString() {
        return Maquina.ADD;
    }
}
