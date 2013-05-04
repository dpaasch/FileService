package fileManagement;

import java.util.List;

/**
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public interface FormatStrategy<T, E> {

    public abstract T decodeData(E data);

    public abstract String encodeData(T data);
}