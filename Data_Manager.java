package com.org.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Data_Manager {

	private static final String URL="jdbc:mysql://localhost:3306/bankingdb";
 private static final String USERNAME = "root";
 private static final String PASSWORD ="Archana@123";
   public static Connection getConnection() {
	   Connection connection = null;
	   try { 
		   connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
				   System.out.println("Database connected successfully");
	   }catch (SQLException e) {
		   e.printStackTrace();
	   }
	return connection;
	   
   } 
   public static void main(String[] args) {
		Data_Manager.getConnection();
	}
}


