package fileService;

/**
 * The ConsoleOutput class provides a means of displaying the output data using
 * System.out.print to display the data. It implements the OutputStrategy.
 *
 * @author dawn bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 * @version 1.01 Added exception handling and validation.
 * Removed magic numbers
 */
public class ConsoleOutput implements OutputStrategy {

    /**
     * Returns the output data
     *
     * @param data : Can be any type of data that needs to be output (receipt,
     * report, etc.)
     * @throws NullPointerException : if data parameter = null
     */
    @Override
    public final void outputData(String data) throws NullPointerException{
        try {
            if (data != null || data.length() != INT_ZERO) {
                System.out.println(data);
            }
        } catch (NullPointerException npe) {
            System.out.println(OUTPUT_DATA + NULL_VALUE);
        }
    }

    /**
     * Returns the output message
     *
     * @param msg : Can be any informational messages or error messages
     * @throws NullPointerException : if data parameter = null
     */
    @Override
    public final void outputMessage(String msg) throws NullPointerException {
        try {
            if (msg != null || msg.length() != INT_ZERO) {
                System.out.println(msg);
            }
        } catch (NullPointerException npe) {
            System.out.println(OUTPUT_MESSAGE + NULL_VALUE);
        }
    }

    /**
     * Returns a string that represents the state of the object
     *
     * @return String 
     */
    @Override
    public String toString() {
        return "ConsoleOutput{" + '}';
    }
    /**
     * Main method used for testing the ConsoleOutput Class
     *
     * @param args
     */
//    public static void main(String[] args) {
//        ConsoleOutput c = new ConsoleOutput();
//        c.outputData(null);
//        c.outputMessage(null);
//    }
}
