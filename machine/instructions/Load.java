package machine.instructions;

import common.Errors;
import common.SymbolTable;
import machine.InstructionStack;
import machine.Maquina;

/**
 * The LOAD Instruction
 * @author jolin qiu
 */

public class Load implements Instruction{

    /**
     * name of variable stored
     */
    private final String name;

    /**
     * the instruction stack
     */
    private final InstructionStack stack;

    /**
     * SymbolTable that stores the variables and their values
     */
    private final SymbolTable symbolTable;

    /**
     * create new instruction
     * @param name the name of the variable stored
     * @param machine the machine
     */
    public Load(String name, Maquina machine) {
        this.name = name;
        this.stack = machine.getInstructionStack();
        this.symbolTable = machine.getSymbolTable();
    }

    /**
     * Load the variables value from the symbol table and push it
     * onto the stack.
     */
    @Override
    public void execute() {
        // Attempting to load a variable name that has not been stored yet.
        if (!symbolTable.has(name)) {
            Errors.report(Errors.Type.UNINITIALIZED, name);
        }
        int value = symbolTable.get(name);
        stack.push(value);
    }

    @Override
    public String toString(){
        return Maquina.LOAD + " " + name;
    }
}
