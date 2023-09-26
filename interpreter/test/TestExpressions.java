package interpreter.test;

import common.SymbolTable;
import interpreter.nodes.expression.BinaryOperation;
import interpreter.nodes.expression.Constant;
import interpreter.nodes.expression.UnaryOperation;
import interpreter.nodes.expression.Variable;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A JUnit tester for all the ARB expressions.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestExpressions {
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
    public void testConstant() {
        Constant c1 = new Constant(10);
        Constant c2 = new Constant(20);

        c1.emit();
        assertEquals("10", outContent.toString());
        outContent.reset();
        c2.emit();
        assertEquals("20", outContent.toString());
        outContent.reset();

        assertEquals(10, c1.evaluate(null));
        assertEquals(20, c2.evaluate(null));

        PrintWriter out = new PrintWriter(System.out);
        c1.compile(out);
        c2.compile(out);
        out.close();
        String expected = "PUSH 10" + System.lineSeparator() +
                "PUSH 20" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    @Order(2)
    public void testVariable() {
        SymbolTable symTbl = new SymbolTable();
        symTbl.set("x", 10);
        symTbl.set("y", 20);
        symTbl.set("z", 30);

        Variable v1 = new Variable("x");
        Variable v2 = new Variable("y");

        v1.emit();
        assertEquals("x", outContent.toString());
        outContent.reset();
        v2.emit();
        assertEquals("y", outContent.toString());
        outContent.reset();

        assertEquals(10, v1.evaluate(symTbl));
        assertEquals(20, v2.evaluate(symTbl));

        PrintWriter out = new PrintWriter(System.out);
        v1.compile(out);
        v2.compile(out);
        out.close();
        String expected = "LOAD x" + System.lineSeparator() +
                "LOAD y" +  System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    @Order(3)
    public void testUnaryOperation() {
        UnaryOperation neg1 = new UnaryOperation("!", new Constant(10));
        UnaryOperation neg2 = new UnaryOperation("!", new Constant(-20));
        UnaryOperation sq3 = new UnaryOperation("$", new Constant(25));
        UnaryOperation sq4 = new UnaryOperation("$", new Constant(124));

        neg1.emit();
        assertEquals("!10", outContent.toString());
        outContent.reset();
        neg2.emit();
        assertEquals("!-20", outContent.toString());
        outContent.reset();
        sq3.emit();
        assertEquals("$25", outContent.toString());
        outContent.reset();
        sq4.emit();
        assertEquals("$124", outContent.toString());
        outContent.reset();

        assertEquals(-10, neg1.evaluate(null));
        assertEquals(20, neg2.evaluate(null));
        assertEquals(5, sq3.evaluate(null));
        assertEquals(11, sq4.evaluate(null));

        PrintWriter out = new PrintWriter(System.out);
        neg1.compile(out);
        neg2.compile(out);
        sq3.compile(out);
        sq4.compile(out);
        out.close();
        String expected = "PUSH 10" + System.lineSeparator() +
                "NEG" + System.lineSeparator() +
                "PUSH -20" + System.lineSeparator() +
                "NEG" + System.lineSeparator() +
                "PUSH 25" + System.lineSeparator() +
                "SQRT" + System.lineSeparator() +
                "PUSH 124" + System.lineSeparator() +
                "SQRT" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }
    @Test
    @Order(4)
    public void testBinaryOperation() {
        BinaryOperation add1 = new BinaryOperation("+", new Constant(10), new Constant(20));
        BinaryOperation sub1 = new BinaryOperation("-", new Constant(10), new Constant(20));
        BinaryOperation mul1 = new BinaryOperation("*", new Constant(10), new Constant(20));
        BinaryOperation div1 = new BinaryOperation("/", new Constant(10), new Constant(20));
        BinaryOperation mod1 = new BinaryOperation("%", new Constant(10), new Constant(20));

        add1.emit();
        assertEquals("( 10 + 20 )", outContent.toString());
        outContent.reset();
        sub1.emit();
        assertEquals("( 10 - 20 )", outContent.toString());
        outContent.reset();
        mul1.emit();
        assertEquals("( 10 * 20 )", outContent.toString());
        outContent.reset();
        div1.emit();
        assertEquals("( 10 / 20 )", outContent.toString());
        outContent.reset();
        mod1.emit();
        assertEquals("( 10 % 20 )", outContent.toString());
        outContent.reset();

        assertEquals(30, add1.evaluate(null));
        assertEquals(-10, sub1.evaluate(null));
        assertEquals(200, mul1.evaluate(null));
        assertEquals(0, div1.evaluate(null));
        assertEquals(10, mod1.evaluate(null));

        PrintWriter out = new PrintWriter(System.out);
        add1.compile(out);
        sub1.compile(out);
        mul1.compile(out);
        div1.compile(out);
        mod1.compile(out);
        out.close();
        String expected = "PUSH 10" + System.lineSeparator() +
                "PUSH 20" + System.lineSeparator() +
                "ADD" + System.lineSeparator() +
                "PUSH 10" + System.lineSeparator() +
                "PUSH 20" + System.lineSeparator() +
                "SUB" + System.lineSeparator() +
                "PUSH 10" + System.lineSeparator() +
                "PUSH 20" + System.lineSeparator() +
                "MUL" + System.lineSeparator() +
                "PUSH 10" + System.lineSeparator() +
                "PUSH 20" + System.lineSeparator() +
                "DIV" + System.lineSeparator() +
                "PUSH 10" + System.lineSeparator() +
                "PUSH 20" + System.lineSeparator() +
                "MOD" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }
}

