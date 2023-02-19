/*
 * Author: Malek 
 * CS 310
 * Project 2 
 */

package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class RegistrationDAO {
    
    // INSERT YOUR CODE HERE
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;

        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                //Build SQL query to insert record into registration table
            String query = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setInt(1, studentid);
            ps.setInt(2, termid);
            ps.setInt(3, crn);

            //Execute the insert statement and check the number of rows affected
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                result = true;
            }
        }  
            
        }
        
        catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                int rowsAffected = ps.executeUpdate();
                result = rowsAffected > 0;
            }
                       
            
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
    PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                // Prepare the sql query
                String sql = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
            ps = conn.prepareStatement(sql);
                        // Set the values for the prepare statement
            ps.setInt(1, studentid);
            ps.setInt(2, termid);
                        // Execute the statement and check the result
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
            
            }
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        String result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                // Build the SQL query to retrieve the list of registered courses for the specified student and term
                String sql = "SELECT r.studentid, r.termid, r.crn " +
                             "FROM registration r " +
                             "WHERE r.studentid = ? AND r.termid = ? " +
                             "ORDER BY r.crn";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
    
                // Execute the query and process the results
                rs = ps.executeQuery();
    
                // Process the query results and create the JSON array
                JsonArray jsonArray = new JsonArray();
                while (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.put("studentid", rs.getInt("studentid"));
                    jsonObject.put("termid", rs.getInt("termid"));
                    jsonObject.put("crn", rs.getInt("crn"));
                    jsonArray.add(jsonObject);
                }
    
                if (jsonArray.size() > 0) {
                    // If there is at least one registration, set the result to the JSON array
                    result = jsonArray.toString();
                } else {
                    // If there are no registrations, set the result to an empty JSON array
                    result = new JsonArray().toString();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
    
        return result;
    }
    
    
}
