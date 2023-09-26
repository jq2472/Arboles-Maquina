package machine.test;

import common.SymbolTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A JUnit tested for the symbol table.
 *
 * @author RIT CS
 */
public class TestSymbolTable {
    @Test
    public void testSymbolTable() {
        SymbolTable symTbl = new SymbolTable();
        assertFalse(symTbl.has("x"));

        symTbl.set("x", 10);
        assertTrue(symTbl.has("x"));
        assertEquals(10, symTbl.get("x"));

        symTbl.set("x", 20);
        assertTrue(symTbl.has("x"));
        assertEquals(20, symTbl.get("x"));

        symTbl.set("y", 30);
        symTbl.set("z", 40);
        String expected = "x: 20" + System.lineSeparator() +
                "y: 30" + System.lineSeparator() +
                "z: 40" + System.lineSeparator();
        assertEquals(expected, symTbl.toString());
    }
}
