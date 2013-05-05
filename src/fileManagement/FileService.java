package fileManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * File Service is used to manage the reader and writer strategies
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.00
 */
public class FileService {

    public static void main(String[] args) throws IOException {
        // Create a new file writer object
        FileWriterStrategy writer =
                // set the strategy object, loading the file to be written to
                new TextFileWriter("ContactList.csv",
                // set the format strategy object, setting hasHeader value, and
                // following with append status
                new CsvCommaFormat(false), true);
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

        // Create a new file reader object
        TextFileReader reader =
                // set the strategy object, loading the file to be read from
                new TextFileReader("C:/NetBeansTemp/ContactList.csv",
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
