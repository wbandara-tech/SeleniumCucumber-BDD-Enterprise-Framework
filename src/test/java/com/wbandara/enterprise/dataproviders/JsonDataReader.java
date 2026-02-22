package com.wbandara.enterprise.dataproviders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbandara.enterprise.constants.FrameworkConstants;
import com.wbandara.enterprise.exceptions.FrameworkException;
import com.wbandara.enterprise.utils.LoggerUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading test data from JSON files.
 * Uses Jackson ObjectMapper for deserialization.
 */
public final class JsonDataReader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonDataReader() {
        throw new UnsupportedOperationException("JsonDataReader is a utility class and cannot be instantiated");
    }

    /**
     * Read JSON file as a list of maps.
     *
     * @param filePath path to the JSON file
     * @return list of maps representing JSON objects
     */
    public static List<Map<String, String>> readJsonData(String filePath) {
        try {
            List<Map<String, String>> data = objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<List<Map<String, String>>>() {}
            );
            LoggerUtils.info(JsonDataReader.class,
                    "Read " + data.size() + " records from JSON: " + filePath);
            return data;
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON file: " + filePath, e);
        }
    }

    /**
     * Read the default products JSON file.
     */
    public static List<Map<String, String>> readProductsData() {
        return readJsonData(FrameworkConstants.PRODUCTS_JSON);
    }

    /**
     * Read JSON file and deserialize to a specific type.
     *
     * @param filePath path to the JSON file
     * @param clazz    target class type
     * @param <T>      type parameter
     * @return deserialized object
     */
    public static <T> T readJsonAs(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new FrameworkException("Failed to deserialize JSON file: " + filePath, e);
        }
    }

    /**
     * Read JSON file and deserialize to a list of specific type.
     *
     * @param filePath path to the JSON file
     * @param clazz    target element class type
     * @param <T>      type parameter
     * @return list of deserialized objects
     */
    public static <T> List<T> readJsonAsList(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(
                    new File(filePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (IOException e) {
            throw new FrameworkException("Failed to deserialize JSON list: " + filePath, e);
        }
    }

    /**
     * Get a specific product by name from the products JSON.
     */
    public static Map<String, String> getProductByName(String productName) {
        List<Map<String, String>> products = readProductsData();
        return products.stream()
                .filter(p -> productName.equalsIgnoreCase(p.get("name")))
                .findFirst()
                .orElseThrow(() -> new FrameworkException(
                        "Product not found in JSON: " + productName));
    }
}

