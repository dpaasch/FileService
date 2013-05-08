package fileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * TxtFileReader is a low-level class that uses the FileReaderStrategy interface
 * to provide a standardized file read method for reading data from a text file.
 * This class reads a file from any directory specified.
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public class TextFileReader implements
        FileReaderStrategy<List<LinkedHashMap<String, String>>> {

    /* TextFileReader components */
    private FormatStrategy<List<LinkedHashMap<String, String>>, List<String>> formatter;
    private BufferedReader reader;
    /* TextFileReader variables */
    private String filePath;// The path of the file being read from
    private String line = null;
    private boolean hasHeader;
    /* TextFileReader message variables */
    private final String NULL_POINTER = " Error: Value cannot be null";
    private static final String FILE_OPEN = " Error: File Unavailable for reading. "
            + "\nIt is currently open and being used by another process";

    /**
     * Constructor instantiates the class by setting the dataFilePath and the
     * appropriate FormatStrategy.
     *
     * @param dataFilePath : path of the file to be read
     * @param formatter : the formatter that is being used
     */
    public TextFileReader(String filePath,
            FormatStrategy<List<LinkedHashMap<String, String>>, List<String>> formatter) {
        setFilePath(filePath);
        setFormatter(formatter);
    }

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
    @Override
    public List<LinkedHashMap<String, String>> readFile() throws IOException {
        // create an array to hold the data from the file
        List<String> data = new ArrayList<String>();

        try {
            reader = new BufferedReader(
                    new java.io.FileReader(filePath));
            line = reader.readLine();  // A line of data from the file
            while (line != null) {
                data.add(line);
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            System.out.println("Data file" + FILE_OPEN);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
//        return decodeData(data);
        return formatter.decodeData(data);
    }

    /**
     * Gets the path of the data file that will be read from
     *
     * @return filePath : path of the file to be read
     */
    @Override
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the path of the data file that will be read from in the form of a
     * private variable
     *
     * @param filePath : path of the file to be read
     */
    @Override
    public final void setFilePath(String filePath) throws NullPointerException {
        try {
            if (filePath != null || filePath.length() != 0) {
                this.filePath = filePath;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            System.out.println("\nDataFilePath" + NULL_POINTER);
        }
    }

    /**
     * Gets the name of the format strategy to be used
     *
     * @return formatter : the formatter that is being used
     */
    @Override
    public FormatStrategy getFormatter() {
        return formatter;
    }

    /**
     * Sets the format strategy that will be used to read the file in the form
     * of a private variable
     *
     * @param formatter : the formatter that is being used
     */
    @Override
    public final void setFormatter(FormatStrategy<List<LinkedHashMap<String, String>>, List<String>> formatter) throws NullPointerException {
        try {
            if (formatter != null) {
                this.formatter = formatter;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            System.out.println("\nFormatter" + NULL_POINTER);
        }
    }

    /**
     * The toString method represents the state of an object
     *
     * @return information about the object
     */
    @Override
    public String toString() {
        return "TextFileReader{" + "formatter=" + formatter + ", "
                + "reader=" + reader + ", filePath=" + filePath + ", line="
                + line + ", hasHeader=" + hasHeader + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.filePath);
        hash = 53 * hash + Objects.hashCode(this.line);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TextFileReader other = (TextFileReader) obj;
        if (!Objects.equals(this.filePath, other.filePath)) {
            return false;
        }
        if (!Objects.equals(this.line, other.line)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        // Create a new file reader object
        TextFileReader reader =
                // set the strategy object, loading the file to be read from
                new TextFileReader("src/Thrifty.txt",
                // set the format strategy object, setting hasHeader value
                new CsvCommaFormat(false));
        // return the file data as a List<LinkedHashMap<String,String>> from 
        // a String
        List<LinkedHashMap<String, String>> returnData =
                reader.readFile();
        System.out.println("Reading file " + reader.getFilePath()
                + "\n\n" + returnData);
    }
}
