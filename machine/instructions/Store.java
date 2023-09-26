package machine.instructions;

import common.SymbolTable;
import machine.InstructionStack;
import machine.Maquina;

/**
 * the STORE instruction.
 *
 * @author jolin qiu
 */
public class Store implements Instruction {
    /**
     * name of variable stored
     */
    private final String name;

    /**
     * the instruction stack
     */
    private final InstructionStack stack;

    /**
     * the symbolTable
     */
    private SymbolTable symbolTable;

    /**
     * create a new instruction
     * @param name the variable name
     * @param machine the machine
     */
    public Store(String name, Maquina machine){
        this.name = name;
        this.stack = machine.getInstructionStack();
        this.symbolTable = machine.getSymbolTable();
    }

    /**
     * Pops the value off the top of stack and sets the variable's
     * value in the symbol table to the value.
     */
    @Override
    public void execute() {
        int value = stack.pop();
        this.symbolTable.set(name, value);
    }

    @Override
    public String toString(){
        return Maquina.STORE + " " + name;
    }
}
