package fileManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 */
public class CsvCommaFormat implements FormatStrategy<List<LinkedHashMap<String, String>>,List<String>> {
    private static final String CRLF = "\n";
    private static final String COMMA = ",";
    
    private boolean hasHeader;
    
    /**
     * Custom constructor
     * @param hasHeader - indicates if CSV has a header row or not
     */
    public CsvCommaFormat(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * Encodes data supplied by a program into the CSV format.
     * 
     * @param dataFromSrc - the raw data from a source, where the
     * key is a real or artificial field name and the value is the data to
     * be stored.
     * @return the formatted data.
     */
    public String encodeData(List<LinkedHashMap<String, String>> dataFromSrc) {
        StringBuilder formattedData = new StringBuilder();

        boolean headerNotSet = true;
        Set<String> fieldNames = null;
        if (hasHeader) {
            fieldNames = dataFromSrc.get(0).keySet();
        }

        for (int recordNo = 0; recordNo < dataFromSrc.size(); recordNo++) {
            if (fieldNames != null && headerNotSet) {
                for (String fieldName : fieldNames) {
                    // using quoted values to eliminate problems with
                    // embedded commas in a data value
                    formattedData.append(fieldName);
                    formattedData.append(COMMA);
                }
                // remove trailing comma
                formattedData.deleteCharAt(formattedData.length() - 1);
                formattedData.append(CRLF);
                addDataValues(dataFromSrc, recordNo, formattedData);
                headerNotSet = false;

            } else {
                addDataValues(dataFromSrc, recordNo, formattedData);
            }
        }

        // Here's the CSV formatted data as a single String that can be
        // saved to a file.
        return formattedData.toString();
    }
    
    /**
     * Decodes CSV data supplied by a source and stores keys and values in
     * a generic data structure that can be used by most any program.
     * 
     * @param dataFromSrc - the raw data from a source, where each entry
     * represents one row in the CSV file.
     * @return the generic data structure used for transport.
     */
    @Override
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
            
            String[] fields = dataFromSrc.get(recordNo).split(COMMA);
            if (hasHeader && (recordNo == 0)) { // first record may be header
                headerFields = fields;
//                continue;
            }

            LinkedHashMap<String, String> record =
                    new LinkedHashMap<String, String>();
            for (int i = 1; i < fields.length; i++) {
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
            if (recordNo != 0) {
                decodedData.add(record);
            }
        }

        return decodedData;
    }
    

    private void addDataValues(List<LinkedHashMap<String, String>> dataFromFile, int recordNo, StringBuilder formattedData) {
        // write a normal data row
        Collection<String> valueCol = dataFromFile.get(recordNo).values();
        for (String value : valueCol) {
            // using quoted values to eliminate problems with
            // embedded commas in a data value
            formattedData.append(value);
            formattedData.append(COMMA);
        }
        // remove trailing comma
        formattedData.deleteCharAt(formattedData.length() - 1);
        formattedData.append(CRLF);
    }

    @Override
    public String toString() {
        return "Csv Comma Formatter";
    }

}