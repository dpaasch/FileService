package fileManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * TxtFileReader is a low-level class that uses the FileReaderStrategy
 * interface to provide a standardized file read method for reading
 * data from a text file.  This class reads a file from any directory
 * specified.
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

    /**
     * Constructor instantiates the class by setting the dataFilePath and 
     * the appropriate FormatStrategy.
     *
     * @param dataFilePath : path of the file to be read
     * @param formatter : the formatter that is being used
     */
    public TextFileReader(String filePath, 
            FormatStrategy<List<LinkedHashMap<String, String>>,List<String>> formatter) {
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
        } catch (IOException e) {
            e.getCause().getMessage();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return decodeData(data);
//        return formatter.decodeData(data);
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
    public final void setFilePath(String filePath) throws NullPointerException{
        try {
            if (filePath != null || filePath.length() != 0) {
                this.filePath = filePath;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            System.out.println(TextFileReader.class + "\nDataFilePath" + NULL_POINTER);
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
    public final void setFormatter(FormatStrategy<List<LinkedHashMap<String, 
            String>>,List<String>> formatter) throws NullPointerException {
        try {
            if (formatter != null) {
                this.formatter = formatter;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            System.out.println(TextFileReader.class + "\nFormatter" + NULL_POINTER);
        }
    }
    
     public List<LinkedHashMap<String, String>> decodeData(List<String> dataFromSrc) {
        /*
         * This data structure will be used to store the decoded data
         * in a neutral format that can be used by any program. It's main
         * advantage is that each Map is a record structure with key values
         * that represent field names if present. If not present, artificail
         * field names will be created based on a simple counter.
         */
        List<LinkedHashMap<String, String>> decodedData =
                new ArrayList<LinkedHashMap<String, String>>();

        String[] headerFields = null;
        for (int recordNo = 0; recordNo < dataFromSrc.size(); recordNo++) {
            String firstRow = dataFromSrc.get(recordNo);
            
            String[] fields = dataFromSrc.get(recordNo).split(",");
            if (hasHeader && (recordNo == 0)) { // first record may be header
                headerFields = fields;
                continue;
            }

            LinkedHashMap<String, String> record =
                    new LinkedHashMap<String, String>();
            for (int i = 0; i < fields.length; i++) {
                if (hasHeader && (recordNo == 0)) { // zero is first record, could be header
                    break; // not a record so skip following code
                    // because it's a header but has no data values

                    // if header included, we store header info as key and data value
                } else if (hasHeader) {
                    record.put(headerFields[i], fields[i]);

                    // if no header we create an artificial key from a counter and add value
                } else {
                    record.put("" + i, fields[i]);
                }
            }

            // Only add the record if it's not the first row (header)
            if (!hasHeader) {
                if (recordNo == 0){
                decodedData.add(record);
                }
            }
        }

        return decodedData;
    }

    /**
     * Main - used for testing purposes only
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        TextFileReader reader =
                new TextFileReader("C:/NetBeansTemp/ContactList.csv",
                new CsvCommaFormat(false));
        List<LinkedHashMap<String, String>> returnData =
                reader.readFile();
        System.out.println("Reading file " + reader.getFilePath()
                + "\n\n" + returnData);
    }
}