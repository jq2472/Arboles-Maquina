package machine;

import java.util.LinkedList;
import java.util.List;

/**
 * The machine's instruction stack for handling instructions.
 *
 * @author RIT CS
 */
public class InstructionStack {
    /** the stack is a list */
    private final List<Integer> stack;

    /**
     * Create an empty stack.
     */
    public InstructionStack() {
        this.stack = new LinkedList<>();
    }

    /**
     * Adds the value to the top of the stack.
     * @param value the value to push
     */
    public void push(int value) {
        this.stack.add(0, value);
    }

    /**
     * Remove and return the top value on the stack.
     * @return the previous top value
     */
    public int pop() { return this.stack.remove(0); }

    /**
     * Get the size of the stack.
     * @return the size of the stack
     */
    public int size() { return this.stack.size(); }

    /**
     * Get the top value from the stack (without removing it).
     * @return the top value on the stack
     */
    public int top() { return this.stack.get(0); }

    /**
     * Returns a string representation of the instruction set in the format:<br>
     * <pre>
     *     If empty:
     *
     *     (MAQ) Instruction set:
     *          EMPTY
     *
     *     If not empty:
     *
     *     (MAQ) Instruction set:
     * 	        0: {first-value}
     * 	        1: {second-value}
     * 	        ...
     * 	        N: {last-value}
     * </pre>
     *
     * @return the string described here.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("(MAQ) Instruction stack:" + System.lineSeparator());
        if (this.stack.size() == 0) {
            result.append('\t').append("EMPTY").append(System.lineSeparator());
        } else {
            int i = 0;
            for (Integer instr : this.stack) {
                result.append('\t').append(i++).append(": ").append(instr).append(System.lineSeparator());
            }
        }
        return result.toString();
    }
}
