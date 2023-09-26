package machine.test;

import machine.Maquina;
import machine.instructions.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A JUnit tester for all the MAQ machine instructions.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestInstructions {
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
    public void testPush() {
        Maquina machine = new Maquina();

        Push push10 = new Push(10, machine);
        assertEquals("PUSH 10", push10.toString());
        Push push20 = new Push(20, machine);
        assertEquals("PUSH 20", push20.toString());
        Push push30 = new Push(30, machine);
        assertEquals("PUSH 30", push30.toString());

        push10.execute();
        push20.execute();
        push30.execute();

        assertEquals(3, machine.getInstructionStack().size());
        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 30" + System.lineSeparator() +
                "\t1: 20" + System.lineSeparator() +
                "\t2: 10" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }

    @Test
    @Order(2)
    public void testPrint() {
        Maquina machine = new Maquina();

        Push push10 = new Push(10, machine);
        Push push20 = new Push(20, machine);
        Push push30 = new Push(30, machine);

        push10.execute();
        push20.execute();
        push30.execute();

        Print print30 = new Print(machine);
        assertEquals("PRINT", print30.toString());
        print30.execute();
        String expected = "30" + System.lineSeparator();

        assertEquals(2, machine.getInstructionStack().size());
        String expectedStack = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 20" + System.lineSeparator() +
                "\t1: 10" + System.lineSeparator();
        assertEquals(expectedStack, machine.getInstructionStack().toString());

        Print print20 = new Print(machine);
        assertEquals("PRINT", print20.toString());
        print20.execute();
        expected += "20" + System.lineSeparator();
        assertEquals(expected, outContent.toString());

        assertEquals(1, machine.getInstructionStack().size());
        expectedStack = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 10" + System.lineSeparator();
        assertEquals(expectedStack, machine.getInstructionStack().toString());
    }

    @Test
    @Order(3)
    public void testStore() {
        Maquina machine = new Maquina();

        Push push10 = new Push(10, machine);
        Push push20 = new Push(20, machine);
        Push push30 = new Push(30, machine);

        push10.execute();
        push20.execute();
        push30.execute();

        Store storeX = new Store("x", machine);
        Store storeY = new Store("y", machine);
        Store storeZ = new Store("z", machine);

        assertEquals("STORE x", storeX.toString());
        assertEquals("STORE y", storeY.toString());
        assertEquals("STORE z", storeZ.toString());

        storeX.execute();
        storeY.execute();
        storeZ.execute();

        assertEquals(3, machine.getSymbolTable().size());
        String expected = "x: 30" + System.lineSeparator() +
                "y: 20" + System.lineSeparator() +
                "z: 10" + System.lineSeparator();
        assertEquals(expected, machine.getSymbolTable().toString());

        assertEquals(0, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\tEMPTY" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }

    @Test
    @Order(4)
    public void testLoad() {
        Maquina machine = new Maquina();

        Push push10 = new Push(10, machine);
        Push push20 = new Push(20, machine);
        Push push30 = new Push(30, machine);

        push10.execute();
        push20.execute();
        push30.execute();

        Store storeX = new Store("x", machine);
        Store storeY = new Store("y", machine);
        Store storeZ = new Store("z", machine);

        storeX.execute();
        storeY.execute();
        storeZ.execute();

        assertEquals(3, machine.getSymbolTable().size());
        String expected = "x: 30" + System.lineSeparator() +
                "y: 20" + System.lineSeparator() +
                "z: 10" + System.lineSeparator();
        assertEquals(expected, machine.getSymbolTable().toString());

        Load loadZ = new Load("z", machine);
        Load loadY = new Load("y", machine);
        Load loadX = new Load("x", machine);

        loadX.execute();
        loadY.execute();
        loadZ.execute();

        assertEquals("LOAD x", loadX.toString());
        assertEquals("LOAD y", loadY.toString());
        assertEquals("LOAD z", loadZ.toString());

        assertEquals(3, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 10" + System.lineSeparator() +
                "\t1: 20" + System.lineSeparator() +
                "\t2: 30" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }
    @Test
    @Order(5)
    public void testNegate() {
        Maquina machine = new Maquina();

        Push push10 = new Push(10, machine);
        Push push20 = new Push(-20, machine);
        Push push30 = new Push(30, machine);

        push10.execute();
        push20.execute();
        push30.execute();

        Negate neg30 = new Negate(machine);
        assertEquals("NEG", neg30.toString());
        neg30.execute();

        assertEquals(3, machine.getInstructionStack().size());
        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: -30" + System.lineSeparator() +
                "\t1: -20" + System.lineSeparator() +
                "\t2: 10" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());

        machine.getInstructionStack().pop();
        Negate neg20 = new Negate(machine);
        assertEquals("NEG", neg20.toString());
        neg20.execute();

        assertEquals(2, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 20" + System.lineSeparator() +
                "\t1: 10" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }

    @Test
    @Order(6)
    public void testSquareRoot() {
        Maquina machine = new Maquina();

        Push push30 = new Push(30, machine);
        Push push112 = new Push(112, machine);
        Push push25 = new Push(25, machine);

        push30.execute();
        push112.execute();
        push25.execute();

        SquareRoot sqrt5 = new SquareRoot(machine);
        assertEquals("SQRT", sqrt5.toString());
        sqrt5.execute();

        assertEquals(3, machine.getInstructionStack().size());
        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 5" + System.lineSeparator() +
                "\t1: 112" + System.lineSeparator() +
                "\t2: 30" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());

        machine.getInstructionStack().pop();
        SquareRoot sqrt10 = new SquareRoot(machine);
        assertEquals("SQRT", sqrt10.toString());
        sqrt5.execute();

        assertEquals(2, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 10" + System.lineSeparator() +
                "\t1: 30" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }

    @Test
    @Order(7)
    public void testAdd() {
        Maquina machine = new Maquina();

        Push push10 = new Push(10, machine);
        assertEquals("PUSH 10", push10.toString());
        Push push20 = new Push(20, machine);
        assertEquals("PUSH 20", push20.toString());
        Push push60 = new Push(60, machine);
        assertEquals("PUSH 60", push60.toString());

        push10.execute();
        push20.execute();
        push60.execute();

        Add add80 = new Add(machine);
        assertEquals("ADD", add80.toString());
        add80.execute();

        assertEquals(2, machine.getInstructionStack().size());
        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 80" + System.lineSeparator() +
                "\t1: 10" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());

        Add add90 = new Add(machine);
        assertEquals("ADD", add90.toString());
        add90.execute();

        assertEquals(1, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 90" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }

    @Test
    @Order(8)
    public void testSubtract() {
        Maquina machine = new Maquina();

        Push push10 = new Push(10, machine);
        assertEquals("PUSH 10", push10.toString());
        Push push20 = new Push(20, machine);
        assertEquals("PUSH 20", push20.toString());
        Push push60 = new Push(60, machine);
        assertEquals("PUSH 60", push60.toString());

        push10.execute();
        push20.execute();
        push60.execute();

        Subtract sub40 = new Subtract(machine);
        assertEquals("SUB", sub40.toString());
        sub40.execute();

        assertEquals(2, machine.getInstructionStack().size());
        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: -40" + System.lineSeparator() +
                "\t1: 10" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());

        Subtract sub50 = new Subtract(machine);
        assertEquals("SUB", sub50.toString());
        sub50.execute();

        assertEquals(1, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 50" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }

    @Test
    @Order(9)
    public void testMultiply() {
        Maquina machine = new Maquina();

        Push push10 = new Push(10, machine);
        assertEquals("PUSH 10", push10.toString());
        Push push20 = new Push(20, machine);
        assertEquals("PUSH 20", push20.toString());
        Push push60 = new Push(60, machine);
        assertEquals("PUSH 60", push60.toString());

        push10.execute();
        push20.execute();
        push60.execute();

        Multiply mul1200 = new Multiply(machine);
        assertEquals("MUL", mul1200.toString());
        mul1200.execute();

        assertEquals(2, machine.getInstructionStack().size());
        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 1200" + System.lineSeparator() +
                "\t1: 10" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());

        Multiply mul12000 = new Multiply(machine);
        assertEquals("MUL", mul12000.toString());
        mul12000.execute();

        assertEquals(1, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 12000" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }

    @Test
    @Order(10)
    public void testDivide() {
        Maquina machine = new Maquina();

        Push push13 = new Push(13, machine);
        assertEquals("PUSH 13", push13.toString());
        Push push60 = new Push(60, machine);
        assertEquals("PUSH 60", push60.toString());
        Push push20 = new Push(20, machine);
        assertEquals("PUSH 20", push20.toString());

        push13.execute();
        push60.execute();
        push20.execute();

        Divide div3 = new Divide(machine);
        assertEquals("DIV", div3.toString());
        div3.execute();

        assertEquals(2, machine.getInstructionStack().size());
        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 3" + System.lineSeparator() +
                "\t1: 13" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());

        Divide div4 = new Divide(machine);
        assertEquals("DIV", div4.toString());
        div4.execute();

        assertEquals(1, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 4" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }

    @Test
    @Order(11)
    public void testModulus() {
        Maquina machine = new Maquina();

        Push push3 = new Push(3, machine);
        assertEquals("PUSH 3", push3.toString());
        Push push64 = new Push(64, machine);
        assertEquals("PUSH 64", push64.toString());
        Push push20 = new Push(20, machine);
        assertEquals("PUSH 20", push20.toString());

        push3.execute();
        push64.execute();
        push20.execute();

        Modulus mod4 = new Modulus(machine);
        assertEquals("MOD", mod4.toString());
        mod4.execute();

        assertEquals(2, machine.getInstructionStack().size());
        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 4" + System.lineSeparator() +
                "\t1: 3" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());

        Modulus mod3 = new Modulus(machine);
        assertEquals("MOD", mod3.toString());
        mod3.execute();

        assertEquals(1, machine.getInstructionStack().size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 3" + System.lineSeparator();
        assertEquals(expected, machine.getInstructionStack().toString());
    }
}
