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
                
                String query = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
            ps = conn.prepareStatement(query);
            ps.setInt(1, termid);
            ps.setString(2, subjectid);
            ps.setString(3, num);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();

            JsonArray jsonArray = new JsonArray();
while (rs.next()) {
    JsonObject jsonObject = new JsonObject();
    int numColumns = rsmd.getColumnCount();
    for (int i = 1; i <= numColumns; i++) {
        String columnName = rsmd.getColumnName(i);
        String columnValue = rs.getString(i);
        jsonObject.put(columnName, columnValue);
    }
    jsonArray.add(jsonObject);
}
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