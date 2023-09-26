package interpreter.nodes.expression;

import common.SymbolTable;
import machine.instructions.Push;

import java.io.PrintWriter;

/**
 * An ExpressionNode representing a constant, i.e., a literal integer value.
 * Syntax: A sequence of digits representing a decimal number, possibly
 * preceded by a minus sign, but no spaces.
 * @author jolin qiu
 */
public class Constant implements ExpressionNode{
    /**
     * the value taken as an argument
     */
    private final int value;


    /**
     * create a new constant
     * @param value the value
     */
    public Constant(int value){
        this.value = value;
    }

    /**
     * print the stored value to standard output
     */
    @Override
    public void emit() {
        System.out.print(value);
    }

    /**
     * generates the MAQ instruction for pushing the value
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        out.println("PUSH " + value);
    }

    /**
     * return the stored value when evaluated
     * @param symTbl the symbol table is ignored here
     */
    @Override
    public int evaluate(SymbolTable symTbl) {
        return value;
    }
}
