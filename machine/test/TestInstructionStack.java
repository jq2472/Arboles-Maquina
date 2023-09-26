package machine.test;

import machine.InstructionStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A JUnity tested for the instruction stack.
 *
 * @author RIT CS
 */
public class TestInstructionStack {
    @Test
    public void testStack() {
        InstructionStack stack = new InstructionStack();
        assertEquals(0, stack.size());

        stack.push(10);
        assertEquals(1, stack.size());
        assertEquals(10, stack.top());
        assertEquals(10, stack.pop());
        assertEquals(0, stack.size());

        stack.push(20);
        stack.push(30);
        stack.push(40);
        assertEquals(3, stack.size());
        assertEquals(40, stack.top());

        assertEquals(40, stack.pop());
        assertEquals(2, stack.size());
        stack.push(50);
        stack.push(60);
        assertEquals(4, stack.size());

        String expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
                "\t0: 60" + System.lineSeparator() +
                "\t1: 50" + System.lineSeparator() +
                "\t2: 30" + System.lineSeparator() +
                "\t3: 20" + System.lineSeparator();
        assertEquals(expected, stack.toString());

        while (stack.size() > 0) {
            stack.pop();
        }
        assertEquals(0, stack.size());
        expected = "(MAQ) Instruction stack:" + System.lineSeparator() +
            "\tEMPTY" + System.lineSeparator();
        assertEquals(expected, stack.toString());
    }
}
