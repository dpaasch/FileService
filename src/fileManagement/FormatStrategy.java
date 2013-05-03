package fileManagement;

/**
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FormatStrategy<T, E> {

    public abstract T decodeData(E dataFromFile);

    public abstract String encodeData(T dataFromFile);
}
