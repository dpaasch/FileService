package fileManagement;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FileWriterStrategy {

    public abstract String getDataFileName();

    public abstract void setDataFileName(String fileName);

    /**
     * Writes the provided data to the file specified by the user in the
     * getFileName() method called at instantiation time. The path for this file
     * is currently C:/NetBeansTemp and cannot be changed unless this class is
     * modified in the dataFile variable setting. This method, calls the
     * validateDataFile() method to determine if the file name provided by the
     * user already exists. If it does not, it will create the file
     * automatically. Type specifies the type of list to be used.
     *
     * @param inputData : The data to be written expressed as a String.
     */
    //    @Override
    public abstract String writeToFile(List<LinkedHashMap<String, String>> data) throws IOException;
    
}
