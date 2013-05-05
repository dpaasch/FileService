package fileManagement;

/**
 * This class is the startup class for the program. It creates a new
 * Reader & Writer object
 *
 * @author dawn bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public class FileStartup {
    public static void main(String[] args) {
        
    
    // Create the new reader
    TextFileReader reader = 
            // give the reader the name of the path to the file to be read
            new TextFileReader("C:/NetBeansTemp/ContactList.csv", 
            // give the reader the formatter to use and if it has a header record
            new CsvCommaFormat(false));
    
    // Create the new writer
   TextFileWriter writer = 
           // give the writer the file name to be written to
           new TextFileWriter("ContactList2.csv", 
           // give the writer the formatter to use and if it has a header record
           // finally give it the append status
           new CsvCommaFormat(false), true);
    }
}
