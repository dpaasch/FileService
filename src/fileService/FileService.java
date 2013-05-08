package fileService;

/**
 * File Service is used to manage the reader and writer strategies
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FileService {

    /* FileService Reader Methods */
    /**
     * Gets the path of the data file that will be read from
     *
     * @return filePath : path of the file to be read
     */
    public abstract String getFilePath();

    /**
     * Sets the path of the data file that will be read from in the form of a
     * private variable
     *
     * @param filePath : path of the file to be read
     */
    public abstract void setFilePath(String filePath);

    /* FileService Writer Methods */
    /**
     * Returns the value of the file name received in the form of the private
     * variable.
     *
     * @return filename : The value of the private variable that identifies the
     * file name.
     */
    public abstract String getFileName();

    /**
     * Sets the value of the private variable for the file name.
     *
     * @param fileName : The file name expressed as a String. Defaults to null
     * if no value is passed in.
     */
    public abstract void setFileName(String fileName);

}