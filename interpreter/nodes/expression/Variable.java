package interpreter.nodes.expression;

import common.Errors;
import common.SymbolTable;

import java.io.PrintWriter;

/**
 * The ExpressionNode for a simple variable
 * @author Jolin Qiu
 */
public class Variable implements ExpressionNode {

    /**
     * the name of the variable
     */
    private final String name;

    /**
     * create a new variable for the identifier
     * @param name the name of the variable
     */
    public Variable(String name){
        this.name = name;
    }

    /**
     * print to standard output the name of the Variable
     */
    @Override
    public void emit() {
        System.out.print(name);
    }

    /**
     * generates the MAQ instruction for loading the variable name
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        out.println("LOAD " + name);
    }

    /**
     * Get the value of the variable name from the symbol table.
     * @param symTbl the table containing all variable values
     * @return the value of the variable name
     */
    @Override
    public int evaluate(SymbolTable symTbl) {
        // Attempting to use a variable that has not been assigned to yet.
        if (!symTbl.has(name)){
            Errors.report(Errors.Type.UNINITIALIZED, name);
        }
        return symTbl.get(name);

    }
}
