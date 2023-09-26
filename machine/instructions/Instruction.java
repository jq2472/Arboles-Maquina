package machine.instructions;

/**
 * An interface for a MAQ machine instruction.
 *
 * @author RIT CS
 */
public interface Instruction {
    /**
     * Run this instruction on the Machine, using the Machine's
     * value stack and symbol table.
     */
    void execute();

    /**
     * Show the instruction using text so that it can be understood
     * by a person.
     *
     * @return a short string describing what this instruction will do
     */
    @Override
    String toString();
}
