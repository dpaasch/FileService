package fileManagement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 */
public class CsvCommaFormat implements
        FormatStrategy<List<LinkedHashMap<String, String>>, List<String>> {

    /* CsvCommaFormat variables */
    private boolean hasHeader;
    /*CsvCommaFormat CONSTANT variables */
    private static final String LF = "\n";
    private static final String DELIMITER = ",";

    /**
     * Constructor instantiates a header variable
     *
     * @param hasHeader : Used to signify whether a file has a header row or not
     */
    public CsvCommaFormat(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    @Override
    public List<LinkedHashMap<String, String>> decodeData(List<String> dataFromFile) {
        // Create the array to hold the data being split
        List<LinkedHashMap<String, String>> decodedData =
                new ArrayList<LinkedHashMap<String, String>>();
        String[] header = null;
        for (int recordNum = 0; recordNum < dataFromFile.size(); recordNum++) {
            String firstDataRow = dataFromFile.get(recordNum);
            String[] dataFields = dataFromFile.get(recordNum).split(DELIMITER);
            // this "if" is for the header, if there is one it would be 
            // found here (record #0 = row #1)
            if (hasHeader && (recordNum == 0)) {
                // put the dataFields value into the header variable
                header = dataFields;
                continue;   // continue processing through file 
            }
            // create an array to hold the record data
            LinkedHashMap<String,String> record = 
                    new LinkedHashMap<String,String>();
            for (int i = 0; i < dataFields.length; i++) {
                // if there is a header, we store the header data into the Map
                if (hasHeader && (recordNum == 0)) {
                    break;
                    // now we store the header data into the Map
                } else if (hasHeader) {
                    record.put(header[i], dataFields[i]);
                } else {
                    // here we generate a unique key and store the data
                    record.put("" + i, dataFields[i]);
                }
            }
             //add the record only if it is not the header
        if (!hasHeader) {
            decodedData.add(record);
        }
    }
    return decodedData ;
}
@Override
        public String encodeData(List<LinkedHashMap<String, String>> dataFromFile) {
        StringBuilder dataFormatted = new StringBuilder();

        LinkedHashMap<String, String> record = dataFromFile.get(0);
        for (int i = 0; i < record.size(); i++) {
            dataFormatted.append(record.get(i)).append(LF);
        }
        return dataFormatted.toString();
    }

    @Override
        public String toString() {
        return "CsvCommaFormat{" + "hasHeader=" + hasHeader + '}';
    }
}
