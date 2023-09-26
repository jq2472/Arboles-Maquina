package interpreter.nodes.expression;

import common.SymbolTable;
import interpreter.nodes.ArbolesNode;

/**
 * An abstraction for all ArbolesNode's that can be evaluated to get
 * an integer value back.  By definition, they do not alter the "state"
 * of the program when evaluated, i.e., the symbol table.
 *
 * @author RIT CS
 */
public interface ExpressionNode extends ArbolesNode {
    /**
     * Evaluate the expression representing by this node.
     *
     * @param symTbl the symbol table, if needed, to fetch the variable values.
     * @return the result of the evaluation.
     */
    int evaluate(SymbolTable symTbl);
}
