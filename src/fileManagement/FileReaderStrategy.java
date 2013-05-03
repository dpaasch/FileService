package fileManagement;

import java.io.IOException;

/**
 *
 * @author tim
 */
public interface FileReaderStrategy<T> {

    /**
     * gets the path of the data file that will be read from
     *
     * @return dataFilePath : path of the file to be read
     */
    public abstract String getDataFilePath();

    /**
     * gets the name of the format strategy to be used
     * @return formatter : the formatter that is being used
     */
    public abstract FormatStrategy getFormatter();

    /**
     * reads the data file given via the getDataFilePath() method
     * T is the type of data being read in
     * @return dataFromFile : data found within the file
     * @throws IOException : 
     */
    public abstract T readFile() throws IOException;

    /**
     * sets the path of the data file that will be read from
     *
     * @param dataFilePath : path of the file to be read
     */
    public abstract void setDataFilePath(String filePath);

    /**
     * sets the format strategy that will be used to read the file
     * @param formatter : the formatter that is being used 
     */
    public abstract void setFormatter(FormatStrategy formatter);
}
