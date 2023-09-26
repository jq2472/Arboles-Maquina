package interpreter.nodes.action;

import common.SymbolTable;
import interpreter.nodes.expression.ExpressionNode;

import java.io.PrintWriter;

/**
 * A node that represents the displaying of the value of an expression.
 * @author Jolin Qiu
 */
public class Print implements ActionNode {

    /**
     * the child expression
     */
    private final ExpressionNode child;

    /**
     * Creates a new Print node
     * @param child the child expression
     */
    public Print(ExpressionNode child){
        this.child = child;
    }

    /**
     * Print the statement to standard output in the format "Print"
     * followed by the infix form of the expression.
     */
    @Override
    public void emit() {
        System.out.print("Print ");
        child.emit();
    }

    /**
     * Generates the MAQ instructions that when instructed will perform
     * the print action.
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        // automatically identifies if child is a variable (load) or integer (push)
        //PUSH 10
        //LOAD y
        child.compile(out);
        out.println("PRINT");
    }

    /**
     * Evaluate the child expression and print the result to standard output.
     * @param symTbl the table where variable values are stored
     */
    @Override
    public void execute(SymbolTable symTbl) {
        System.out.println(child.evaluate(symTbl));
    }
}
