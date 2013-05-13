package fileService;

/**
 * The OutputStrategy is an interface, providing full abstraction. It is
 * responsible for providing common output methods for the low-level classes to
 * utilize (code reuse).
 *
 * @author dawn bykowski, dpaasch@my.wctc.edu
 * @version 1.01 Added exception handling and validation.
 * Removed magic numbers
 */
public interface OutputStrategy {
    
    //OutputStrategy message variables common to lower-level classes
    static final String NULL_VALUE = " Error: \nValue cannot be null "
            + "or empty";
    // OutputStrategy variables common to low-level classes
    static final int INT_ZERO = 0; // Replacement for magic numbers
    static final String OUTPUT_DATA = "Output Data", // Replacement for magic numbers
            OUTPUT_MESSAGE = "Output Message"; // Replacement for magic numbers

    /**
     * Returns the output data
     *
     * @param data : Can be any type of data that needs to be output (receipt,
     * report, etc.)
     * @throws NullPointerException : if data parameter = null
     */
    public abstract void outputData(String data) throws NullPointerException;

    /**
     * Returns the output message
     *
     * @param msg : Can be any informational messages or error messages
     * @throws NullPointerException : if data parameter = null
     */
    public abstract void outputMessage(String msg) throws NullPointerException;
}