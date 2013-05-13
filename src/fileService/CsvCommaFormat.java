package fileService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * This class is a
 * <code>FormatStrategy</code> implementation. It's responsible for encoding and
 * decoding to/from the CSV format where commas are delimiters and data values
 * are not quoted.
 *
 * @author Jim Lombardo, jlombardo@wctc.edu (version 1.00)
 * @author Dawn Bykowski, dpaasch@my.wctc.edu
 * @version 1.01 Added additional method to check for embedded commas, along
 * with adding additional documentation
 */
public class CsvCommaFormat implements
        FormatStrategy<List<LinkedHashMap<String, String>>, List<String>> {

    /* CsvCommaFormat variables */
    private boolean hasHeader,
            embeddedComma;     // Comma embedded in data
    // Replacements for magic numbers
    private static final String CRLF = "\n",
            DELIMITER = ",",
            QUOTES = "\"",
            COLON = ": ",
            EMBEDDED_COMMA = "Embedded Comma",
            DATA = "Data",
            HAS_HEADER = "Has Header";
    private static final int INT_ZERO = 0;
    /* CsvCommaFormat message variables */
    private static final String NULL_POINTER = " Error: Value cannot be null",
            ILLEGAL_ARGUMENT = " Error: Value cannot be 0 or less than 0",
            INVALID_DELIMITER = " Error: Delimiter must be a comma (,) value";

    /**
     * Custom constructor
     *
     * @param hasHeader - indicates if CSV has a header row or not
     */
    public CsvCommaFormat(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * Encodes data supplied by a program into the CSV format.
     *
     * @param data - the raw data from a source, where the key is a real or
     * artificial field name and the value is the data to be stored.
     * @return the formatted data.
     * @throws NullPointerException : if data parameter = null
     */
    @Override
    public String encodeData(List<LinkedHashMap<String, String>> data)
            throws NullPointerException {
        if (data == null || data.isEmpty()) {
            throw new NullPointerException(DATA + NULL_POINTER);
        }
        StringBuilder formattedData = new StringBuilder();
        boolean headerNotSet = true;
        Set<String> fieldNames = null;
        if (hasHeader) {
            fieldNames = data.get(INT_ZERO).keySet();
        }
        for (int recordNo = INT_ZERO; recordNo < data.size(); recordNo++) {
            if (fieldNames != null && headerNotSet) {
                // get a field count, by retrieving the size
                int endField = fieldNames.size();
                // count the fields
                int fieldCount = 1;
                for (String fieldName : fieldNames) {
                    // check for embedded commas
                    embeddedComma = findEmbeddedComma(fieldName);
                    if (embeddedComma) {
                        // insert quotes around the field with the comma
                        // so that this field is not misinterpreted for being
                        // its own field
                        formattedData.append(QUOTES);
                        formattedData.append(fieldName);
                        formattedData.append(QUOTES);
                    }
                    // add a delimiter if it's not the end field
                    if (fieldCount < endField) {
                        formattedData.append(fieldName);
                        formattedData.append(DELIMITER);
                    }
                    fieldCount++;
                }
                if (fieldCount < endField) {
                    // remove trailing comma
//                formattedData.deleteCharAt(formattedData.length() - 1);                
                    formattedData.append(CRLF);
                    addDataValues(data, recordNo, formattedData);
                    headerNotSet = false;
                }
            } else {
                addDataValues(data, recordNo, formattedData);
            }
        }
        // Here's the CSV formatted data as a single String that can be
        // saved to a file.
        return formattedData.toString();
    }

    /**
     * Decodes CSV data supplied by a source and stores keys and values in a
     * generic data structure that can be used by most any program.
     *
     * @param data - the raw data from a source, where each entry represents one
     * row in the CSV file.
     * @return the generic data structure used for transport.
     */
    @Override
    public List<LinkedHashMap<String, String>> decodeData(List<String> data) {
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
        for (int recordNo = INT_ZERO; recordNo < data.size(); recordNo++) {
            String firstRow = data.get(recordNo);

            String[] fields = data.get(recordNo).split(",");
            if (hasHeader && (recordNo == INT_ZERO)) { // first record may be header
                headerFields = fields;
                continue;
            }
            LinkedHashMap<String, String> record =
                    new LinkedHashMap<String, String>();
            for (int i = INT_ZERO; i < fields.length; i++) {
                // if there is a header row we are going to continue processing
                if (hasHeader && (recordNo == INT_ZERO)) {
                    continue; // we took care of this above, thus we now continue
                    // if header included, we store header info as key and data value
                } else if (hasHeader) {
                    record.put(headerFields[i], fields[i]);
                    // if no header we create an artificial key from a counter and add value
                } else {
                    if (!fields[i].isEmpty()) {
                        record.put("" + i, fields[i]);
                    }
                }
            }
            // Add the record if there is not a header row
            if (hasHeader && recordNo == 1) {
            } else {
                // we decode the data and add the record!
                decodedData.add(record);
            }
        }
        return decodedData;
    }

    /**
     * Adds the data after the encode process
     *
     * @param data : The data to be written to the file
     * @param recordNo : A number for each record for tracking purposes
     * @param formattedData : The data after it has been formatted into a String
     */
    private void addDataValues(List<LinkedHashMap<String, String>> data,
            int recordNo, StringBuilder formattedData) {
        // write a normal data row
        Collection<String> valueCol = data.get(recordNo).values();
        for (String value : valueCol) {
            // using quoted values to eliminate problems with
            // embedded commas in a data value
            formattedData.append(value);
            formattedData.append(DELIMITER);
        }
        // remove trailing comma
        formattedData.deleteCharAt(formattedData.length() - 1);
        formattedData.append(CRLF);
    }

    /**
     * Returns the value of the embedded comma status received in the form of
     * the private variable.
     *
     * @param field : The value of the private variable that identifies the
     * field to be checked
     * @return isEmbeddedComma : A true or false state
     * @throws IllegalArgumentException :
     */
    public final boolean findEmbeddedComma(String field)
            throws IllegalArgumentException {
        boolean isEmbeddedComma = false;
        String[] fields = field.split(DELIMITER);
        if (fields.length > 1) {
            isEmbeddedComma = true;
        } else if (fields.length <= 0) {
            throw new IllegalArgumentException(EMBEDDED_COMMA
                    + ILLEGAL_ARGUMENT);
        }
        return isEmbeddedComma;
    }

    /**
     * Returns the value of the hasHeader status received in the form of the
     * private variable.
     *
     * @return hasHeader : The value of the private variable that identifies if
     * the file includes a header row of data.
     */
    public boolean isHasHeader() {
        return hasHeader;
    }

    /**
     * Sets the value of the private variable for the hasHeader status
     *
     * @param hasHeader : The value of the private variable that identifies if
     * the file includes a header row of data.
     */
    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * Returns the value of the embeddedComma status received in the form of the
     * private variable.
     *
     * @return embeddedComma : The value of the private variable that identifies
     * if there is an embedded comma in the data being sent for encoding.
     */
    public boolean isEmbeddedComma() {
        return embeddedComma;
    }

    /**
     * Sets the value of the private variable for the embedded comma status.
     *
     * @param embeddedComma : The value of the private variable that identifies
     * if there is an embedded comma in the data being sent for encoding.
     */
    public void setEmbeddedComma(boolean embeddedComma) {
        this.embeddedComma = embeddedComma;
    }

    /**
     * The toString method represents the state of an object
     *
     * @return information about the object
     */
    @Override
    public String toString() {
        return "CsvCommaFormat{" + HAS_HEADER + COLON + hasHeader + CRLF
                + EMBEDDED_COMMA + embeddedComma + '}';
    }

    /**
     * Returns the hash code value for the object on which this method is
     * invoked.
     *
     * @return hashCode : for hasHeader and embeddedComma
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.hasHeader ? 1 : 0);
        hash = 23 * hash + (this.embeddedComma ? 1 : 0);
        return hash;
    }

    /**
     * Checks if some other object passed to it as an argument is equal to the
     * object on which this method is invoked
     *
     * @param obj
     * @return equals : for hasHeader and embeddedComma
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CsvCommaFormat other = (CsvCommaFormat) obj;
        if (this.hasHeader != other.hasHeader) {
            return false;
        }
        if (this.embeddedComma != other.embeddedComma) {
            return false;
        }
        return true;
    }
    /**
     * Main method used for testing the Car Class
     *
     * @param args
     */
//    public static void main(String[] args) {
//        FormatStrategy formatter = new CsvCommaFormat(false);
//        // Need to create a linkedHashMap for each row of data to be written
//        LinkedHashMap<String, String> map0 =
//                new LinkedHashMap<String, String>();
//        map0.put("0", "2.00,2.00");
//        LinkedHashMap<String, String> map1 =
//                new LinkedHashMap<String, String>();
//        map1.put("1", "4.00,5.75");
//        // Pass the map into the list of LinkedHashMap
//        List<LinkedHashMap<String, String>> data =
//                new ArrayList<LinkedHashMap<String, String>>();
//        data.add(map0);
//        data.add(map1);
//
//        String encodedData = formatter.encodeData(data);
//        System.out.println(encodedData);
//    }
}