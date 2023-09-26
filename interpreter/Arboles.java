package interpreter;

import common.Errors;
import common.SymbolTable;
import interpreter.nodes.ArbolesNode;
import interpreter.nodes.action.ActionNode;
import interpreter.nodes.action.Assignment;
import interpreter.nodes.action.Print;
import interpreter.nodes.expression.Constant;
import interpreter.nodes.expression.Variable;
import interpreter.nodes.expression.*;
import machine.Maquina;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The main program for the high level ARB language.  It takes a program in ARB,
 * converts it into a token list, builds the parse trees for each statement,
 * displays the program in infix, interprets/evaluates the program, then
 * compiles into MAQ instructions so that the machine, Maquina, can execute it.
 *
 * @author RIT CS
 * @author Jolin Qiu
 */
public class Arboles {
    /** the terminating character when reading machine instructions from user (not file) */
    private final static String EOF = ".";

    /** the ARB print token */
    private final static String PRINT = "@";
    /** the ARB assignment token */
    private final static String ASSIGN = "=";

    /** the location to generate the compiled ARB program of MAQ instructions */
    private final static String TMP_MAQ_FILE = "tmp/TEMP.maq";

    /**
     * sequence of whitespace separated strings of "tokens"
     * tokenList = ["=", "x", "1", "@", "+", "!", "x", "3"]
     */
    private final ArrayList<String> tokenList;

    /**
     * where the program is being stored
     */
    private ArrayList<ActionNode> programActions;

    /**
     * Create a new Arbelos instance.  The result of this method is the tokenization
     * of the entire ARB input into a list of strings.
     *
     * @param in where to read the ARB input from
     * @param stdin if true, the user should be prompted to enter ARB statements until
     *              a terminating ".".
     */
    public Arboles(Scanner in, boolean stdin) {
        System.out.println("(ARB) prefix...");
        this.tokenList = new ArrayList<>();
        if (stdin) {
            System.out.print("🌳 ");
            in = new Scanner(System.in);
        }
        while (in.hasNextLine()){
            String prefixLine = in.nextLine();
            String[] tokens = prefixLine.strip().split("\\s+");
            if (stdin){
                if (prefixLine.equals(EOF)){
                    break;
                }
                System.out.println(prefixLine);
                tokenList.addAll(Arrays.asList(tokens));
                System.out.print("🌳 ");

            } else {
                System.out.println(prefixLine);
                tokenList.addAll(Arrays.asList(tokens));
            }
        }
    }

    /**
     * build program helper function : a recursive expression parsing method
     * that consumes enough of the token list to get the fully represented expression
     * and returns the root as an Expression node.
     * @return (Constant) fully deduced expression
     */
    public ExpressionNode expressionHelper(){
        // ran out of required input while parsing an expression
        try {
            String token = tokenList.get(0);
        } catch (Exception e) {
            Errors.report(Errors.Type.PREMATURE_END);
        }
        //check if binary operation
        String token = tokenList.remove(0);
        if (token.equals("+") || token.equals("-") ||token.equals("*") ||token.equals("/") || token.equals("%")){
            ExpressionNode leftChild = expressionHelper();
            ExpressionNode rightChild = expressionHelper();
            return new BinaryOperation(token, leftChild, rightChild);
        }
        // check if unary operation
        else if (token.equals("!") || token.equals("$")){
            ExpressionNode value = expressionHelper();
            // return(?)
            return new UnaryOperation(token, value);
        }
        // check if variable
        else if (token.matches("^[a-zA-Z].*")){
            return new Variable(token);
        }
        // otherwise, constant
        else {
            try {
                return new Constant(Integer.parseInt(token));
            } catch (Exception e) {
                Errors.report(Errors.Type.ILLEGAL_OPERATOR, token);
            }
        }
        return new Constant(Integer.parseInt(token));
    }

    /**
     * Build the parse trees into the program which is a list of ActionNode's -
     * one per line of ARB input.
     */
    public void buildProgram() {
        programActions = new ArrayList<>();
        ActionNode action = null;
        while (!tokenList.isEmpty()){
            String token = tokenList.remove(0);
            if (token.equals(ASSIGN)){
                String name = tokenList.remove(0);
                action = new Assignment(name, expressionHelper());
            }
            else if (token.equals(PRINT)) {
                action = new Print(expressionHelper());
            }
            else {
                // An illegal action token (not printing or assigning), was encountered
                Errors.report(Errors.Type.ILLEGAL_ACTION, token);
            }
            programActions.add(action);
        }
    }

    /**
     * Displays the entire ARB program of ActionNode's to standard
     * output using emit().
     */
    public void displayProgram() {
        System.out.println("(ARB) infix...");
        programActions.forEach(t -> {
            t.emit();
            System.out.println();
        });
    }

    /**
     * Execute the ARB program of ActionNode's to standard output using execute().
     * In order to execute the ActioNodes, a local SymbolTable must be created here
     * for use.
     */
    public void interpretProgram() {
        System.out.println("(ARB) interpreting program...");
        // create a new, empty symbolTable
        SymbolTable symTbl = new SymbolTable();
        programActions.forEach(t -> t.execute(symTbl));

        System.out.println("(ARB) Symbol table:");
        System.out.print(symTbl);
    }

    /**
     * Compile the ARB program using ActionNode's compile() into the
     * temporary MAQ instruction file.
     *
     * @throws IOException if there are issues working with the temp file
     */
    public void compileProgram() throws IOException {
        System.out.println("(ARB) compiling program to " + TMP_MAQ_FILE + "...");
        PrintWriter out = new PrintWriter(TMP_MAQ_FILE);

        programActions.forEach(t-> t.compile(out));
        out.close();
    }

    /**
     * Takes the generated MAQ instruction file and assembles/executes
     * it using the Maquina machine.
     *
     * @throws FileNotFoundException if the MAQ file cannot be found.
     */
    public void executeProgram() throws FileNotFoundException {
        //open new file for scanning
        File file = new File(TMP_MAQ_FILE);
        Scanner maqInstruction = new Scanner(file);

        Maquina machine = new Maquina();
        machine.assemble(maqInstruction, false);
        machine.execute();
    }

    /**
     * The main program runs either with no input (ARB program entered through standard
     * input), or with a file name that represents the ARB program.
     *
     * @param args command line arguments
     * @throws IOException if there are issues working with the ARB/MAQ files.
     */
    public static void main(String[] args) throws IOException {
        // determine ARB input source
        Scanner arbIn = null;
        boolean stdin = false;
        if (args.length == 0) {
            arbIn = new Scanner(System.in);
            stdin = true;
        } else if (args.length == 1) {
            arbIn = new Scanner(new File(args[0]));
        } else {
            System.out.println("Usage: java Arbelos filename.arb");
            System.exit(1);
        }

        // step 1: read ARB program into token list
        Arboles interpreter = new Arboles(arbIn, stdin);

        // step 2: parse and build the program from the token list
        interpreter.buildProgram();

        // step 3: display the program in infix
        interpreter.displayProgram();

        // step 4: interpret program
        interpreter.interpretProgram();

        // step 5: compile the program
        interpreter.compileProgram();

        // step 6: have machine execute compiled program
        interpreter.executeProgram();
    }
}
