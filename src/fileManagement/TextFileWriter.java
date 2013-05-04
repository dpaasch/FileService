package fileManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Dawn Bykowski
 * @version 1.00
 */
public class TextFileWriter implements
        FileWriterStrategy<List<LinkedHashMap<String, String>>> {

    /* TextFileWriter components */
    private File dataFile;
    private String fileName;  // File name of the file being written to
    private PrintWriter writer;  // The type of writer being used
    private FormatStrategy formatter;   // Formatter being used
    /* TextFileWriter variables */
    private boolean append;     // Append data (true) or overwrite (false)
    private boolean hasHeader;  // There is a header row
        /* TextFileWriterr message variables */
    private final String NULL_POINTER = " Error: Value cannot be null";

    /**
     * Constructor instantiates the class by setting the file name and the
     * FormatStrategy.
     *
     * @param fileName
     * @param formatter
     */
    public TextFileWriter(String fileName, FormatStrategy formatter) {
        setFileName(fileName);
        setFormatter(formatter);
    }

    @Override
    public void writeToFile(List<LinkedHashMap<String, String>> data) {
        try {
            dataFile = new File(File.separatorChar + "NetBeansTemp"
                    + File.separatorChar + fileName);
            validateDataFile();
            // create a new writer object.
            writer = new PrintWriter(
                    new BufferedWriter(
                    new FileWriter(dataFile, append)));
            String encodedData = formatter.encodeData(data);
            writer.println(encodedData);
            System.out.println("Write successful.");
            writer.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public FormatStrategy getFormatter() {
        return formatter;
    }

    @Override
    public void setFileName(String fileName) {
        try {
            if (fileName != null || fileName.length() != 0) {
                this.fileName = fileName;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            System.out.println(TextFileWriter.class + "\nDataFileName"
                    + NULL_POINTER);
        }
    }

    @Override
    public void setFormatter(FormatStrategy formatter) throws NullPointerException {
//        try {
        if (formatter != null) {
            this.formatter = formatter;
        } else {
            throw new NullPointerException(TextFileReader.class
                    + "\nFormatter" + NULL_POINTER);
        }
//        } catch (NullPointerException npe) {
//            System.out.println(TextFileReader.class + "\nFormatter" 
//                    + NULL_POINTER);
//        }
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    @Override
    public String toString() {
        return "TextFileWriter{" + "dataFile=" + dataFile + ", "
                + "fileName=" + fileName + ", writer=" + writer + ", "
                + "formatter=" + formatter + ", append=" + append + ", "
                + "hasHeader=" + hasHeader + '}';
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

    public static void main(String[] args) throws IOException {
        String fn = "ContactList.csv";
        CsvCommaFormat formatter = new CsvCommaFormat(false);
        TextFileWriter writer = new TextFileWriter(fn, formatter);
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