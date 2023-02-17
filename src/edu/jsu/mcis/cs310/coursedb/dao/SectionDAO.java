/*
 * Author: Malek 
 * CS 310
 * Project 2
 */

 package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class SectionDAO {
    

    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                // SQL query to select all rows from the "section" table where the "termid", "subjectid", and "num" columns match the provided values, sorted by "crn"
             String query = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
            
            // Prepare the statement using the query
             ps = conn.prepareStatement(query);
             // Set the values for the 3 parameters in the prepared statement
            ps.setInt(1, termid);
            ps.setString(2, subjectid);
            ps.setString(3, num);

            // Execute the prepared statement and store the result set in "rs"
            rs = ps.executeQuery();
            // Get the metadata (column names, data types, etc.) for the result set
            rsmd = rs.getMetaData();

            // Json Work 
            // Create a new JSON array to store the query results
            JsonArray jsonArray = new JsonArray();

            // Loop through each row in the result set
while (rs.next()) {
        // Create a new JSON object to store the row data
    JsonObject jsonObject = new JsonObject();
        // Get the number of columns in the result set
    int numColumns = rsmd.getColumnCount();

        // Loop through each column in the current row
    for (int i = 1; i <= numColumns; i++) {
                // Get the name of the current column
        String columnName = rsmd.getColumnName(i);
                // Get the value of the current column as a string
        String columnValue = rs.getString(i);
                // Add the column name and value to the JSON object
        jsonObject.put(columnName, columnValue);
    }
        // Add the JSON object for the current row to the JSON array
    jsonArray.add(jsonObject);
}
// Convert the JSON array to a string and store it in the "result" variable
result = jsonArray.toString();
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    return result;
}
    
}