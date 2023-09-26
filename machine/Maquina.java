package machine;

import common.Errors;
import common.SymbolTable;
import machine.instructions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The machine can process/execute a series of low level MAQ instructions using
 * an instruction stack and symbol table.
 *
 * @author RIT CS
 * @author jolin qiu
 */
public class Maquina {
    /** the push instruction */
    public final static String PUSH = "PUSH";
    /** the print instruction */
    public final static String PRINT = "PRINT";
    /** the store instruction */
    public final static String STORE = "STORE";
    /** the load instruction */
    public final static String LOAD = "LOAD";
    /** the negate instruction */
    public final static String NEGATE = "NEG";
    /** the square root instruction */
    public final static String SQUARE_ROOT = "SQRT";
    /** the add instruction */
    public final static String ADD = "ADD";
    /** the subtract instruction */
    public final static String SUBTRACT = "SUB";
    /** the multiply instruction */
    public final static String MULTIPLY = "MUL";
    /** the divide instruction */
    public final static String DIVIDE = "DIV";
    /** the modulus instruction */
    public final static String MODULUS = "MOD";

    /** the list of valid machine instructions */
    public static final List< String > OPERATIONS =
            List.of(
                    ADD,
                    DIVIDE,
                    LOAD,
                    MODULUS,
                    MULTIPLY,
                    NEGATE,
                    PUSH,
                    PRINT,
                    SQUARE_ROOT,
                    STORE,
                    SUBTRACT
            );

    /** the terminating character when reading machine instructions from user (not file) */
    private final static String EOF = ".";

    /**
     * symbol table
     */
    private final SymbolTable symbolTable;

    /**
     * instruction stack
     */
    private final InstructionStack instructionStack;

    /**
     * instruction list
     */
    private final ArrayList<Instruction> instructionList;

    /**
     * Create a new machine, with an empty symbol table, instruction stack, and
     * list of instructions.
     */
    public Maquina() {
        //initialize an empty symbol table and instruction list
        this.symbolTable = new SymbolTable();
        this.instructionStack = new InstructionStack();
        this.instructionList = new ArrayList<>();
    }

    //getters

    /**
     * Return the instruction stack.
     *
     * @return the stack
     */
    public InstructionStack getInstructionStack() {
        return instructionStack;
    }

    /**
     * Return the symbol table.
     *
     * @return the symbol table
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    // Maquina construction
    /**
     * Assemble the machine instructions.
     *
     * @param maqIn the input source
     * @param stdin true if input is coming from standard input (for prompting)
     */
    public void assemble(Scanner maqIn, boolean stdin) {
        Instruction instruction = null;
        if (stdin) {
            System.out.print("🤖 ");
            maqIn = new Scanner(System.in);
        }
        // for each instruction, make an Instruction object and store in a list
        while (maqIn.hasNextLine()) {
            //read and split the line into an array of strings
            String[] fields = maqIn.nextLine().strip().split("\\s+");
            String operation = fields[0];
            //[PUSH, 10] instruction = index 0, value/var = index 1
            switch (operation) {
                case "PUSH" -> instruction = new Push(Integer.parseInt(fields[1]), this);
                case "PRINT" -> instruction = new Print(this);
                case "STORE" -> instruction = new Store((fields[1]), this);
                case "LOAD" -> instruction = new Load((fields[1]), this);
                case "NEG" -> instruction = new Negate(this);
                case "SQRT" -> instruction = new SquareRoot(this);
                case "ADD" -> instruction = new Add(this);
                case "SUB" -> instruction = new Subtract(this);
                case "MUL" -> instruction = new Multiply(this);
                case "DIV" -> instruction = new Divide(this);
                case "MOD" -> instruction = new Modulus(this);
                default -> {
                    // if it's NOT stdin and ".", or stdin and NOT "."
                    // then it's just an illegal operation
                    if (!stdin || !operation.equals(EOF)){
                        Errors.report(Errors.Type.ILLEGAL_INSTRUCTION, fields[0]);
                    }
                }
            }
            // none of the above true => continue with printing bot for operations
            if (stdin && !fields[0].equals(EOF)) {
                System.out.print("🤖 ");
                // it is stdin and EOF = terminate
            } else if (stdin){
                break;
            }
            this.instructionList.add(instruction);
        }
            System.out.println("(MAQ) Machine instructions:");
        // lambda expression test
        // alt method reference : instructionList.forEach(System.out::println);
         instructionList.forEach(t -> System.out.println(t));
        }

    /**
     * Executes each assembled machine instruction in order.  When completed it
     * displays the symbol table and the instruction stack.
     */
    public void execute() {

        System.out.println("(MAQ) Executing...");
        // executes each instruction
        // alt lambda expression : instructionList.forEach(t -> t.execute);
        instructionList.forEach(Instruction::execute);

        System.out.println("(MAQ) Completed execution!");
        System.out.print("(MAQ) Symbol table:"
        + "\n" + symbolTable);

        System.out.println(instructionStack);
    }

    /**
     * The main method.  Machine instructions can either be specified from standard input
     * (no command line), or from a file (only argument on command line).  From
     * here the machine assembles the instructions and then executes them.
     *
     * @param args command line argument (optional)
     * @throws FileNotFoundException if the machine file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        // determine input source
        Scanner maqIn = null;
        boolean stdin = false;
        if (args.length == 0) {
            maqIn = new Scanner(System.in);
            stdin = true;
        } else if (args.length == 1){
            maqIn = new Scanner(new File(args[0]));
        } else {
            System.out.println("Usage: java Maquina [filename.maq]");
            System.exit(1);
        }

        Maquina machine = new Maquina();
        machine.assemble(maqIn, stdin);     // assemble the machine instructions
        machine.execute();                  // execute the program
        maqIn.close();
    }
}
