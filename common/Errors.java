package common;

/**
 * Shared class for dealing with errors encountered by the machine or
 * interpreter when running.
 *
 * @author RIT CS
 */
public class Errors {
    /** Indicates the type of error encountered */
    public enum Type {
        /** divide by zero */
        DIVIDE_BY_ZERO("divide by zero"), // no info needed
        /** square root of negative number */
        NEGATIVE_SQUARE_ROOT("square root of negative number"), // no info needed
        /** premature end of statement */
        PREMATURE_END("premature end of statement" ), // no info needed
        /** illegal action */
        ILLEGAL_ACTION("illegal action encountered in source"), // info=token
        /** illegal instruction */
        ILLEGAL_INSTRUCTION("illegal instruction"), // info=token
        /** illegal operator */
        ILLEGAL_OPERATOR("illegal operator in expression"), // info=token
        /** unitialized variable */
        UNINITIALIZED( "uninitialized variable"); // info=token

        /** the message associated with the error */
        private final String message;

        /**
         * Create the error.
         *
         * @param message error message
         */
        Type(String message) {
            this.message = message;
        }
    }

    /**
     * Report an error and stop the program.   All output goes to standard error.
     * @param type The kind of error, printed first
     */
    public static void report(Type type) {
        report(type, null);
    }

    /**
     * Report an error and stop the program. All output goes to standard error.
     *
     * @param type The kind of error, printed first
     * @param info if not null, an additional value to be printed after a colon
     */
    public static void report(Type type, Object info) {
        System.err.print(type.message);
        if ( info != null ) {
            System.err.println(": " + info);
        }
        System.err.println();
        System.exit(-1);
    }


}
