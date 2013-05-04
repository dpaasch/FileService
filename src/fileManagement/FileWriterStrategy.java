package fileManagement;

/**
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FileWriterStrategy<T> {
    
    public abstract void writeToFile(T data);
    
    public abstract String getFileName();
    
    public abstract FormatStrategy getFormatter();

    public abstract void setFileName(String fileName);
    
    public abstract void setFormatter(FormatStrategy formatter) throws NullPointerException;  
    
}