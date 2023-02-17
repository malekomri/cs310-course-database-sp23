/*
 * Author: Malek 
 * CS 310
 * Project 2 
 */

package edu.jsu.mcis.cs310.coursedb;

import edu.jsu.mcis.cs310.coursedb.dao.*;

public class Main {

    public static void main(String[] args) {
        
        DAOFactory daoFactory = new DAOFactory("coursedb");
        
        if ( !daoFactory.isClosed() ) {
            System.out.println("Connected Successfully!");
        }
        
    }
    
}