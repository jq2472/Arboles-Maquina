package interpreter.nodes.expression;

import common.Errors;
import common.SymbolTable;
import machine.Maquina;

import java.io.PrintWriter;
import java.util.List;

/**
 * A calculation represented by a binary operator and its two operands.
 * @author Jolin Qiu
 */
public class BinaryOperation implements ExpressionNode {

    /**
     * ARB addition operator
     */
    static final String ADD = "+";

    /**
     * ARB division operator
     */
    static final String DIV = "/";

    /**
     * ARB modulus operator
     */
    static final String MOD = "%";

    /**
     * ARB multiply operator
     */
    static final String MUL = "*";

    /**
     * ARB subtraction operator
     */
    static final String SUB = "-";

    /**
     * the legal binary operators, for use when parsing
     */
    static final List<String> OPERATORS =
            List.of(
                ADD,
                DIV,
                MOD,
                MUL,
                SUB
            );

    /**
     * the unary operator
     */
    private final String operator;

    /**
     * the left Expression Node
     */
    private final ExpressionNode leftChild;

    /**
     * the right Expression Node
     */
    private final ExpressionNode rightChild;

    /**
     * creates a new BinaryOperation Node
     * @param operator the operator
     * @param leftChild the left child expression
     * @param rightChild the right child expression
     */
    public BinaryOperation(String operator, ExpressionNode leftChild,
                                            ExpressionNode rightChild){
        this.operator = operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;

    }

    /**
     * Print to standard output the infix display of the two child nodes
     * separated by the operator and surrounded by parentheses.
     * ex: ( 10 + 20 )
     */
    @Override
    public void emit() {
        System.out.print("( ");
        leftChild.emit();
        System.out.print(" " + operator + " ");
        rightChild.emit();
        System.out.print(" )");

    }

    /**
     * Generates the MAQ instructions for this operation.
     * @param out the stream to write output to using out.println()
     *
     */
    @Override
    public void compile(PrintWriter out) {
        leftChild.compile(out);
        rightChild.compile(out);
        switch (operator) {
            case ADD -> out.println(Maquina.ADD);
            case DIV -> out.println(Maquina.DIVIDE);
            case MOD -> out.println(Maquina.MODULUS);
            case MUL -> out.println(Maquina.MULTIPLY);
            case SUB -> out.println(Maquina.SUBTRACT);
        }
    }

    /**
     * Compute the result of evaluating the child expression and applying the
     * operator to it.
     * @param symTbl the symbol table, if needed, to fetch the variable values.
     * @return the result of the computation
     */
    @Override
    public int evaluate(SymbolTable symTbl) {
        int left = leftChild.evaluate(symTbl);
        int right = rightChild.evaluate(symTbl);
        int value = 0;
        switch (operator) {
            case ADD -> value = left + right;
            case DIV -> {
                if (right == 0){
                    Errors.report(Errors.Type.DIVIDE_BY_ZERO);
                }
                value = left / right;
            }
            case MOD -> value = left % right;
            case MUL -> value = left * right;
            case SUB -> value = left - right;
        }
        return value;
    }
}
