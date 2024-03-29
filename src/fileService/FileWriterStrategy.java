package fileService;

import java.io.IOException;

/**
 * The FileWriterStrategy is an interface, providing full abstraction. It is
 * responsible for providing common file writer methods for the low-level
 * classes to utilize (code reuse).
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FileWriterStrategy<T> {

    /**
     * Writes the given data to a text file. Using a list because it is a
     * variable-length argument that allows for a variable number of arguments.
     * The LinkedHashMap allows for a predictable iteration order, the order in
     * which the data was inserted into the table. T is the type of data being
     * written
     *
     * @return data : The data to be written into the file
     * @throws NullPointerException : Thrown if there is no data
     * @throws IOException
     */
    public abstract void writeToFile(T data)
            throws NullPointerException, IOException;

    /**
     * Returns the value of the file name received in the form of the private
     * variable.
     *
     * @return filename : The value of the private variable that identifies the
     * file name.
     */
    public abstract String getFileName();

    /**
     * Returns the value of the formatter received in the form of the private
     * variable.
     *
     * @return formatter : The value of the private variable that identifies the
     * format strategy being used.
     */
    public abstract FormatStrategy getFormatter();

    /**
     * Sets the value of the private variable for the file name.
     *
     * @param fileName : The file name expressed as a String. Defaults to null
     * if no value is passed in.
     * @throws NullPointerException : if fileName parameter = null
     */
    public abstract void setFileName(String fileName) throws NullPointerException;

    /**
     * Sets the value of the private variable for the formatter.
     *
     * @param formatter : The value of the private variable that identifies the
     * format strategy being used.
     * @throws NullPointerException : if formatter parameter = null
     */
    public abstract void setFormatter(FormatStrategy formatter) throws NullPointerException;
}