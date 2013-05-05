package fileManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dawn Bykowski
 * @version 1.00
 */
public class TextFileWriter1  {

    /* TextFileWriter components */
//    private File dataFile;
    private String fileName;  // File name of the file being written to
//    private PrintWriter writer;  // The type of writer being used
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
    public TextFileWriter1(FormatStrategy formatter) {
        this.fileName = fileName;
        this.formatter = formatter;
//        setFileName(fileName);
//        setFormatter(formatter);
    }


    public void writeToFile(List<LinkedHashMap<String, String>> data, String filePath) {
//        int count = 0;  // count the # data rows to be written
        try {
            //File dataFile = new File(filePath);
//            validateDataFile();
            // create a new writer object.
//            writer = new PrintWriter(
//                    new BufferedWriter(
//                    new FileWriter(dataFile, append)));
//            writer = new PrintWriter(new BufferedWriter(new FileWriter(dataFile, append)));
            FileWriter fileW = new FileWriter(filePath);
            PrintWriter writer = new PrintWriter(fileW);
                   //new FileWriter(dataFile, false));
//            System.out.println("Encoding Data");
            String encodedData = formatter.encodeData(data);
//            System.out.println(data);
            System.out.println(encodedData);
//            List<String> d = new ArrayList<String>();
//            for (String s : d) {
            writer.print(encodedData);
            writer.close();
//                writer.print(encodedData);
//                count++;
            System.out.println("Write successful.");
//            }
        } catch (IOException ex) {
            Logger.getLogger(TextFileWriter1.class.getName()).log(Level.SEVERE, null, ex);
        }
//        return formatter.encodeData(data);
    }


    public String getFileName() {
        return fileName;
    }


    public FormatStrategy getFormatter() {
        return formatter;
    }

    public void setFileName(String fileName) {
        try {
            if (fileName != null || fileName.length() != 0) {
                this.fileName = fileName;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            System.out.println(TextFileWriter1.class + "\nDataFileName"
                    + NULL_POINTER);
        }
    }


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

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

//    @Override
//    public String toString() {
//        return "TextFileWriter{" + "dataFile=" + dataFile + ", "
//                + "fileName=" + fileName + ", writer=" + writer + ", "
//                + "formatter=" + formatter + ", append=" + append + ", "
//                + "hasHeader=" + hasHeader + '}';
//    }

    /**
     * Validates that the dataFile being requested with the getFileName() method
     * already exists. If it doesn't, it will be created automatically.
     *
     * @throws IOException
     */
//    public void validateDataFile() throws IOException {
//        if (!dataFile.exists()) {
//            System.out.println("Creating file: " + dataFile.getCanonicalPath());
//            dataFile.createNewFile();
//        }
//    }

    public static void main(String[] args) throws IOException {
//        List<String> writeStrings = new ArrayList<String>();
//        writeStrings.add("Pam,Tillis,418 Westfield Way,Pewaukee,WI,53072");
//        writeStrings.add("Jerry,Reed,419 Westfield Way,Pewaukee,WI,53072");
//        writeStrings.add("Clay,Walker,420 Westfield Way,Pewaukee,WI,53072");
//        writeStrings.add("Patsy,Cline,421 Westfield Way,Pewaukee,WI,53072");

        String filePath = File.separatorChar + "NetBeansTemp"
                + File.separatorChar + "ContactListNew.txt";



        CsvCommaFormat formatter = new CsvCommaFormat(false);
        TextFileWriter1 writer = new TextFileWriter1(formatter);
        

        LinkedHashMap<String, String> c1Map =
                new LinkedHashMap<String, String>();

        c1Map.put("0", "Jones");
        c1Map.put("1", "James");

        LinkedHashMap<String, String> c2Map =
                new LinkedHashMap<String, String>();

        c2Map.put("0", "Jones1");
        c2Map.put("1", "James1");

        List<LinkedHashMap<String, String>> data = new ArrayList<LinkedHashMap<String, String>>();
        data.add(c1Map);
        data.add(c2Map);

        System.out.println("before" + data);
        writer.writeToFile(data, filePath);
    }
}