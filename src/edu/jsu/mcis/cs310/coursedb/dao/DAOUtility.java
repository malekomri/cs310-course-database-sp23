/*
 * Author: Malek 
 * CS 310
 * Project 2
 */

package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;

public class DAOUtility {
    
    public static final int TERMID_SP23 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {

               
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        JsonObject record = new JsonObject();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            Object columnValue = rs.getObject(i);
                            record.put(columnName, columnValue);
                        }
                        records.add(record);
                    }
                }
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
