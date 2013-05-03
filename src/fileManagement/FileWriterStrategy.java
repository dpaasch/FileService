package fileManagement;

import java.io.File;


/**
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FileWriterStrategy<T> {
    
    public abstract int writeToFile(T data);
    
    public abstract String getFileName();
    
    public abstract FormatStrategy getFormatter();

    public abstract void setFileName(String fileName);
    
    public abstract void setFormatter(FormatStrategy formatter);
    
    
    
    
    
}
