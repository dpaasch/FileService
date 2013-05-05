package fileManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * The TextFileWriter class is a low-level class that uses the
 * FileWriterStrategy interface to provide a standardized file write method.
 * This class writes to C:/NetBeansTemp with flexibility ONLY in the name 
 * of the file created/written to, entered as a parameter at instantiation
 * of the TextFileWriter.
 *
 * @author dawn bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public class TextFileWriter implements
        FileWriterStrategy<List<LinkedHashMap<String, String>>> {

    /* TextFileWriter components */
    private File dataFile;
    private PrintWriter writer;  // The type of writer being used
    private FormatStrategy formatter;   // Formatter being used
    /* TextFileWriter variables */
    private String fileName;  // File name of the file being written to
    private boolean append;     // Append data (true) or overwrite (false)
    /* TextFileWriterr message variables */
    private static final String NULL_POINTER = " Error: Value cannot be null";
    private static final String APPEND = " Warning: Value is set to file overwrite";

    /**
     * Constructor instantiates the class by setting the fileName, formatter,
     * and append private variables.
     *
     * @param fileName : The file name expressed as a String.
     * @param formatter : The file format strategy being used.
     * @param append : The file write status expressed as a boolean. Set to true
     * = append, and set to false = overwrite.
     */
    public TextFileWriter(String fileName, FormatStrategy formatter,
            boolean append) {
        setFileName(fileName);
        setFormatter(formatter);
        setAppend(append);
    }

    /**
     * Constructor instantiates the class by setting the fileName, and formatter
     * private variables.
     *
     * @param fileName : The file name expressed as a String.
     * @param formatter : The file format strategy being used.
     * @param append : The file write status expressed as a boolean.
     */
    public TextFileWriter(String fileName, FormatStrategy formatter) {
        setFileName(fileName);
        setFormatter(formatter);
    }

    /**
     * Writes the given data to a text file. Using a list because it is a 
     * variable-length argument that allows for a variable number of 
     * arguments. The LinkedHashMap allows for a predictable iteration
     * order, the order in which the data was inserted into the table. T 
     * is the type of data being written
     *
     * @return data : The data to be written into the file
     * @throws NullPointerException : Thrown if there is no data
     * @throws IOException
     */
    @Override
    public void writeToFile(List<LinkedHashMap<String, String>> data) {

        try {
            // if there is no data throw an error
            if (data != null) {
                dataFile = new File(File.separatorChar + "NetBeansTemp"
                        + File.separatorChar + fileName);
                validateDataFile();
                // create a new writer object.
                writer = new PrintWriter(
                        new BufferedWriter(
                        new FileWriter(dataFile, append)));
                // encode the data to be written
                String encodedData = formatter.encodeData(data);
                // write the data to the file
                writer.println(encodedData);
                System.out.println("Write successful.");
                writer.close();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            System.out.println("Data" + NULL_POINTER);
        } catch (IOException ioe) {
            System.out.println(ioe.getCause().getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        System.out.println("Wrote to file: " + dataFile.getAbsoluteFile());
    }

    /**
     * Validates that the dataFile being requested with the getFileName() method
     * already exists. If it doesn't, it will be created automatically.
     *
     * @throws IOException
     */
    public void validateDataFile() throws IOException {
        if (!dataFile.exists()) {
            System.out.println("Creating file: " + dataFile.getCanonicalPath());
            dataFile.createNewFile();
        }
    }

    /**
     * Returns the value of the file name received in the form of the private
     * variable.
     *
     * @return filename : The value of the private variable that identifies the
     * file name.
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the value of the formatter received in the form of the private
     * variable.
     *
     * @return formatter : The value of the private variable that identifies the
     * format strategy being used.
     */
    @Override
    public FormatStrategy getFormatter() {
        return formatter;
    }

    /**
     * Sets the value of the private variable for the file name.
     *
     * @param fileName : The file name expressed as a String. Defaults to null
     * if no value is passed in.
     */
    @Override
    public final void setFileName(String fileName) {
        try {
            if (fileName != null) {
                this.fileName = fileName;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            System.out.println("\nFileName" + NULL_POINTER);
        }
    }

    /**
     * Sets the value of the private variable for the formatter.
     *
     * @param formatter : The value of the private variable that identifies the
     * format strategy being used.
     */
    @Override
    public final void setFormatter(FormatStrategy formatter) {
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
     * Returns the value of the append status received in the form of the
     * private variable.
     *
     * @return append : The value of the private variable that identifies if the
     * file should be appended to. The file write status expressed as a boolean.
     * Set to true = append, and set to false = overwrite.
     */
    public boolean isAppend() {
        return append;
    }

    /**
     * Sets the value of the private variable for the append status.
     *
     * @param append : The value of the private variable that identifies if the
     * file should be appended to. The file write status expressed as a boolean.
     * Set to true = append, and set to false = overwrite.
     */
    public final void setAppend(boolean append) {
            this.append = append;
    }

    /**
     * The toString method represents the state of an object
     * 
     * @return information about the object
     */
    @Override
    public String toString() {
        return "TextFileWriter{" + "dataFile=" + dataFile + ", "
                + "writer=" + writer + ", formatter=" + formatter + ", "
                + "fileName=" + fileName + ", append=" + append + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.dataFile);
        hash = 17 * hash + Objects.hashCode(this.fileName);
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
        final TextFileWriter other = (TextFileWriter) obj;
        if (!Objects.equals(this.dataFile, other.dataFile)) {
            return false;
        }
        if (!Objects.equals(this.fileName, other.fileName)) {
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) throws IOException {
        String fn = "ContactList.csv";
        CsvCommaFormat formatter = new CsvCommaFormat(false);
        TextFileWriter writer = new TextFileWriter(fn, formatter, false);
        // Need to create a linkedHashMap for each row of data to be written
        LinkedHashMap<String, String> map0 =
                new LinkedHashMap<String, String>();
        map0.put("0", "Pam,Tillis,418 Westfield Way,Pewaukee,WI,53072");
        LinkedHashMap<String, String> map1 =
                new LinkedHashMap<String, String>();
        map1.put("1", "Jerry,Reed,419 Westfield Way,Pewaukee,WI,53072");
        LinkedHashMap<String, String> map2 =
                new LinkedHashMap<String, String>();
        map2.put("2", "Clay,Walker,420 Westfield Way,Pewaukee,WI,53072");
        LinkedHashMap<String, String> map3 =
                new LinkedHashMap<String, String>();
        map3.put("3", "Patsy,Cline,421 Westfield Way,Pewaukee,WI,53072");
        // Pass the map into the list of LinkedHashMap
        List<LinkedHashMap<String, String>> data =
                new ArrayList<LinkedHashMap<String, String>>();
        data.add(map0);
        data.add(map1);
        data.add(map2);
        data.add(map3);
        writer.writeToFile(data);
    }
}