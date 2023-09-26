package common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The machine's symbol table for handling the mapping of
 * variable names to their integer values.
 *
 * @author RIT CS
 */
public class SymbolTable {
    /** the symbol table */
    private final Map<String, Integer> symTbl;

    /**
     * Create an empty symbol table.
     */
    public SymbolTable() {
         // use a LinkedHashMap so that we have O(1) access,
         // but the insertion order is maintained.
        this.symTbl = new LinkedHashMap<>();
    }

    /**
     * Set a variable name in the table to an associated value.
     * @param name the variable name
     * @param value the associated value
     */
    public void set(String name, int value) {
        this.symTbl.put(name, value);
    }

    /**
     * Retrieve the associated value of a variable name from the table.
     * @param name the variable name
     * @return the value
     */
    public int get(String name) {
        return this.symTbl.get(name);
    }

    /**
     * Does a variable name already exist in the symbol table?
     * @param name the variable name
     * @return whether the name is in the table or not
     */
    public boolean has(String name) {
        return this.symTbl.containsKey(name);
    }

    /**
     * The size of the symbol table.
     * @return the size
     */
    public int size() { return this.symTbl.size(); }

    /**
     * Returns a string representation of the symbol table:<br>
     * <pre>
     *     (MAQ) Symbol table:
     * 	        {variable-1}: {value}
     * 	        {variable-2}: {value}
     * 	        ...
     * 	        {variable-N}: {value}
     * </pre>
     * @return the string described here
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : this.symTbl.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append(System.lineSeparator());
        }
        return result.toString();
    }
}
