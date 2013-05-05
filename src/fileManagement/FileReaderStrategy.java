package fileManagement;

import java.io.IOException;

/**
 * The FileReaderStrategy is an interface, providing full abstraction.  It is
 * responsible for providing common file reader methods for the low-level
 * classes to utilize (code reuse).
 * 
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FileReaderStrategy<T> {

    /**
     * Gets the path of the data file that will be read from
     *
     * @return filePath : path of the file to be read
     */
    public abstract String getFilePath();

    /**
     * Gets the name of the format strategy to be used
     *
     * @return formatter : the formatter that is being used
     */
    public abstract FormatStrategy getFormatter();
    
    /**
     * Reads the data file given via the getDataFilePath() method. Using a list
     * because it is a variable-length argument that allows for a variable
     * number of arguments. The LinkedHashMap allows for a predictable iteration
     * order, the order in which the data was inserted into the table. T is the
     * type of data being read in - this means no set type has to be used
     *
     * @return data : formatted data from the file being read
     * @throws IOException
     */
    public abstract T readFile() throws IOException;

    /**
     * Sets the path of the data file that will be read from in the form of a
     * private variable
     *
     * @param filePath : path of the file to be read
     */
    public abstract void setFilePath(String filePath);

    /**
     * Sets the format strategy that will be used to read the file in the form
     * of a private variable
     *
     * @param formatter : the formatter that is being used
     */
    public abstract void setFormatter(FormatStrategy formatter);
}