package com.wbandara.enterprise.dataproviders;

import com.wbandara.enterprise.constants.FrameworkConstants;
import com.wbandara.enterprise.exceptions.FrameworkException;
import com.wbandara.enterprise.utils.LoggerUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading test data from CSV files.
 * Uses Apache Commons CSV for robust parsing.
 */
public final class CsvDataReader {

    private CsvDataReader() {
        throw new UnsupportedOperationException("CsvDataReader is a utility class and cannot be instantiated");
    }

    /**
     * Read all records from a CSV file as a list of maps.
     *
     * @param filePath path to the CSV file
     * @return list of maps where each map represents a row with header-value pairs
     */
    public static List<Map<String, String>> readCsvData(String filePath) {
        List<Map<String, String>> records = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setTrim(true)
                     .setIgnoreEmptyLines(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Map<String, String> record = new HashMap<>();
                csvRecord.toMap().forEach(record::put);
                records.add(record);
            }

            LoggerUtils.info(CsvDataReader.class,
                    "Read " + records.size() + " records from CSV: " + filePath);

        } catch (IOException e) {
            throw new FrameworkException("Failed to read CSV file: " + filePath, e);
        }

        return records;
    }

    /**
     * Read CSV data from the default users CSV file.
     */
    public static List<Map<String, String>> readUsersData() {
        return readCsvData(FrameworkConstants.USERS_CSV);
    }

    /**
     * Get a specific row by testCase column value.
     *
     * @param testCaseName the value of the testCase column
     * @return map of column name to value for the matching row
     */
    public static Map<String, String> getUserByTestCase(String testCaseName) {
        List<Map<String, String>> allUsers = readUsersData();
        return allUsers.stream()
                .filter(row -> testCaseName.equals(row.get("testCase")))
                .findFirst()
                .orElseThrow(() -> new FrameworkException(
                        "Test case not found in CSV: " + testCaseName));
    }
}

