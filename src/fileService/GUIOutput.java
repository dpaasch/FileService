package fileService;

import javax.swing.JOptionPane;

/**
 * The GUIOutput class provides a means of displaying the output data using
 * JOptionPane to display the data. It implements the OutputStrategy.
 *
 * @author dawn bykowski, dpaasch@my.wctc.edu
 * @version 1.01 Added exception handling and validation.
 * Added toString()
 * Removed magic numbers
 */
public class GUIOutput implements OutputStrategy {

    /**
     * Returns the output data
     *
     * @param data : Can be any type of data that needs to be output (receipt,
     * report, etc.)
     * @throws NullPointerException : if data parameter = null
     */
    @Override
    public final void outputData(String data) throws NullPointerException {
        try {
            if (data != null || data.length() != INT_ZERO) {
                JOptionPane.showMessageDialog(null, data);
            }
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, OUTPUT_DATA
                    + NULL_VALUE);
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
                JOptionPane.showMessageDialog(null, msg);
            }
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, OUTPUT_MESSAGE
                    + NULL_VALUE);
        }
    }

    /**
     * Returns a string that represents the state of the object
     *
     * @return String
     */
    @Override
    public String toString() {
        return "GUIOutput{" + '}';
    }

    /**
     * Main method used for testing the GUIOutput Class
     *
     * @param args
     */
    public static void main(String[] args) {
        GUIOutput g = new GUIOutput();
        g.outputData(null);
        g.outputMessage(null);
        System.out.println(g.toString());
    }
}