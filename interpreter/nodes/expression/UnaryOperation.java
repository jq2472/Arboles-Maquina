package interpreter.nodes.expression;

import common.Errors;
import common.SymbolTable;
import machine.Maquina;

import java.io.PrintWriter;
import java.util.List;

/**
 * A calculation represented by a unary operator and its operand
 * @author Jolin Qiu
 */
public class UnaryOperation implements ExpressionNode{

    /**
     * ARB negation operator
     */
    private static final String NEG = "!";

    /**
     * ARB square root operator
     */
    private static final String SQRT = "$";

    /**
     * the legal unary operators, for use when parsing
     */
    private static final List<String> OPERATORS =
            List.of(
                    NEG,
                    SQRT
            );

    /**
     * the unary operator
     */
    private final String operator;

    /**
     * the ExpressionNode
     */
    private final ExpressionNode child;

    /**
     * Create a new UnaryOperation node.
     * @param operator the unary operator
     * @param child the child (constant) ExpressionNode (10, -20, ..etc.)
     */
    public UnaryOperation(String operator, ExpressionNode child){
        this.operator = operator;
        this.child = child;
    }

    /**
     * Print to standard output the infix display of the child nodes
     * preceded by the operator and without an intervening blank.
     */
    @Override
    public void emit() {
        System.out.print(operator);
        child.emit();
    }

    /**
     * Generates the MAQ instructions for this operation.
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        if (operator.equals(SQRT)) {
            child.compile(out);
            out.println(Maquina.SQUARE_ROOT);
        }
        if (operator.equals(NEG)){
            child.compile(out);
            out.println(Maquina.NEGATE);
        }
    }

    /**
     * compute the result of evaluating the expression and applying
     * the operator to it
     * @param symTbl the symbol table, if needed, to fetch the variable values.
     * @return the result of evaluation
     */
    @Override
    public int evaluate(SymbolTable symTbl) {
        int value = child.evaluate(symTbl);
        if (operator.equals("!")){
            value = value * (-1);
        } else {
            // Attempting to take the square root of a negative number.
            if (value < 0){
                Errors.report(Errors.Type.NEGATIVE_SQUARE_ROOT);
            }
            value = (int) Math.sqrt(value);
        }
        return value;
    }
}
