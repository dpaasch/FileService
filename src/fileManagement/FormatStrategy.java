package fileManagement;

/**
 * The FormatStrategy is an interface, providing full abstraction.  It is
 * responsible for providing common file reader methods for the low-level
 * classes to utilize (code reuse).
 * 
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FormatStrategy<T, E> {

    /**
     * Decodes CSV data supplied by a source and stores keys and values in a
     * generic data structure that can be used by most any program.
     *
     * @param data - the raw data from a source, where each entry represents one
     * row in the CSV file.
     * @return the generic data structure used for transport.
     */
    public abstract T decodeData(E data);

    /**
     * Encodes data supplied by a program into the CSV format.
     *
     * @param data - the raw data from a source, where the key is a real or
     * artificial field name and the value is the data to be stored.
     * @return the formatted data.
     */
    public abstract String encodeData(T data);
}