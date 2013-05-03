package fileManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public int writeToFile(List<LinkedHashMap<String, String>> data) {
        int count = 0;  // count the # data rows to be written
        try {
            dataFile = new File(File.separatorChar + "NetBeansTemp"
                    + File.separatorChar + fileName);
            // create a new writer object.
            writer = new PrintWriter(
                    new BufferedWriter(
                    new FileWriter(dataFile, append)));
            List<String> inputData = formatter.encodeData(data);
            for (String s : inputData) {
                writer.print(s + "\n");
                count++;
                System.out.println("Write successful.");
            }
        } catch (IOException ex) {
            Logger.getLogger(TextFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
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
    public void setFormatter(FormatStrategy formatter) {
        try {
            if (formatter != null) {
                this.formatter = formatter;
            } else {
                throw new NullPointerException();


            }
        } catch (NullPointerException npe) {
            System.out.println(TextFileReader.class
                    + "\nFormatter" + NULL_POINTER);
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> writeStrings = new ArrayList<String>();
        writeStrings.add("Pam,Tillis,418 Westfield Way,Pewaukee,WI,53072");
        writeStrings.add("Jerry,Reed,419 Westfield Way,Pewaukee,WI,53072");
        writeStrings.add("Clay,Walker,420 Westfield Way,Pewaukee,WI,53072");
        writeStrings.add("Patsy,Cline,421 Westfield Way,Pewaukee,WI,53072");

        String fn = "ContactList.csv";
        CsvCommaFormat formatter = new CsvCommaFormat(false);
        List<LinkedHashMap<String, String>> linkedHashMap =
                formatter.decodeData(writeStrings);
        for (LinkedHashMap data : linkedHashMap) {
            System.out.println(data);
        }

        TextFileWriter writer = new TextFileWriter(fn, formatter);
        writer.writeToFile(linkedHashMap);
    }
}