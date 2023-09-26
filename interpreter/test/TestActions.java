package interpreter.test;

import common.SymbolTable;
import interpreter.nodes.action.Assignment;
import interpreter.nodes.action.Print;
import interpreter.nodes.expression.Constant;
import interpreter.nodes.expression.Variable;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A JUnit tester for the ActionNode's.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestActions {
    /**
     * Used to test that expected print's happen
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    @Order(1)
    public void testPrint() {
        SymbolTable symTbl = new SymbolTable();
        symTbl.set("x", 10);
        symTbl.set("y", 20);
        symTbl.set("z", 30);

        Print p1 = new Print(new Constant(10));
        Print p2 = new Print(new Variable("y"));

        p1.emit();
        assertEquals("Print 10", outContent.toString());
        outContent.reset();
        p2.emit();
        assertEquals("Print y", outContent.toString());
        outContent.reset();

        p1.execute(symTbl);
        assertEquals("10" + System.lineSeparator(), outContent.toString());
        outContent.reset();
        p2.execute(symTbl);
        assertEquals("20" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        PrintWriter out = new PrintWriter(System.out);
        p1.compile(out);
        p2.compile(out);
        out.close();
        String expected = "PUSH 10" + System.lineSeparator() +
                "PRINT" + System.lineSeparator() +
                "LOAD y" + System.lineSeparator() +
                "PRINT" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }
    @Test
    @Order(2)
    public void testAction() {
        SymbolTable symTbl = new SymbolTable();

        Assignment a1 = new Assignment("x", new Constant(10));
        Assignment a2 = new Assignment("y", new Variable("x"));

        a1.emit();
        assertEquals("x = 10", outContent.toString());
        outContent.reset();
        a2.emit();
        assertEquals("y = x", outContent.toString());
        outContent.reset();

        a1.execute(symTbl);
        assertEquals(10, symTbl.get("x"));
        outContent.reset();
        a2.execute(symTbl);
        assertEquals(10, symTbl.get("y"));
        outContent.reset();

        PrintWriter out = new PrintWriter(System.out);
        a1.compile(out);
        a2.compile(out);
        out.close();
        String expected = "PUSH 10" + System.lineSeparator() +
                "STORE x" + System.lineSeparator() +
                "LOAD x" + System.lineSeparator() +
                "STORE y"+ System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }
}
