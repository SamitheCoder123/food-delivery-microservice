package com.swiggy.app.demo.util;



import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * @author
 **/

@Component
public class LocationServiceUtility {

    private static final Logger logger = LoggerFactory.getLogger(LocationServiceUtility.class);

    // Database connection details (Update these based on your setup)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/food-delivery-app?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Admin@123";

    // SQL query for inserting location data
    private static final String INSERT_LOCATION_SQL = "INSERT INTO location (id, latitude, longitude, address, city, state, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static void loadLocationsToDB() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/locations.json")));

            JSONArray locationsArray;
            try {
                locationsArray = new JSONArray(jsonString);
            } catch (JSONException e) {
                logger.error("Error parsing locations JSON data: " + e.getMessage(), e);
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOCATION_SQL)) {

                    for (int i = 0; i < locationsArray.length(); i++) {
                        try {
                            JSONObject location = locationsArray.getJSONObject(i);
                            preparedStatement.setInt(1, location.getInt("id"));
                            preparedStatement.setDouble(2, location.getDouble("latitude"));
                            preparedStatement.setDouble(3, location.getDouble("longitude"));
                            preparedStatement.setString(4, location.getString("address"));
                            preparedStatement.setString(5, location.getString("city"));
                            preparedStatement.setString(6, location.getString("state"));
                            preparedStatement.setString(7, location.getString("zipCode"));

                            // Log the query execution
                            logger.debug("Executing query for location id: " + location.getInt("id"));

                            int rowsAffected = preparedStatement.executeUpdate();

                            // Log the result of the execution
                            if (rowsAffected > 0) {
                                logger.info("Successfully inserted location id: " + location.getInt("id"));
                            } else {
                                logger.warn("No rows affected for location id: " + location.getInt("id"));
                            }
                        } catch (JSONException e) {
                            logger.error("Error parsing JSON for location at index " + i + ": " + e.getMessage(), e);
                        }
                    }
                    logger.info("Location data saved to the database successfully.");
                }
            }
        } catch (IOException e) {
            logger.error("Error reading locations JSON file: " + e.getMessage(), e);
        } catch (SQLException e) {
            logger.error("Error saving location data to the database: " + e.getMessage(), e);
        }

    }}
