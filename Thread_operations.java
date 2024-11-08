package com.org.bank;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	public class Thread_operations {

	    static class UserReaderThread implements Runnable {
	        @Override
	        public void run() {
	            readUsers();
	        }
	    }

	
	    public static void readUsers() {
	        String sql = "SELECT * FROM student";
	        
	        try (Connection connection = Data_Manager.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                String name = resultSet.getString("name");
	                String address = resultSet.getString("address");
	                String dept = resultSet.getString("dept");

	                

	                System.out.println( " Name: " + name + " Address: " + address + "dept :" + dept);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error reading user data: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	        Thread userReaderThread = new Thread(new UserReaderThread());
	        userReaderThread.start();

	        try {
	            userReaderThread.join();
	        } catch (InterruptedException e) {
	            System.out.println("Thread interrupted: " + e.getMessage());
	        }

	        System.out.println("User data read operation completed.");
	    }
	}