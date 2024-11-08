package com.org.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class thread_java {
    // Method to read and display user data
    public static void readUsers() {
        String sql = "SELECT * FROM student";
        try (Connection connection = Data_Manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String dept = resultSet.getString("dept");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                System.out.println("Name: " + name + " Address: " + address + " Dept: " + dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert a new user
    public static void insertUser(String name, String address, String dept) {
        String sql = "INSERT INTO user (name, address, dept) VALUES (?, ?, ?)";
        try (Connection connection = Data_Manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, dept);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a user's information
    public static void updateUser(String newName, String newAddress, String newEmail, String newDept) {
        String sql = "UPDATE user SET name = ?, address = ?, dept = ? WHERE email = ?";
        try (Connection connection = Data_Manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newName);
            statement.setString(2, newAddress);
            statement.setString(3, newDept);
            statement.setString(4, newEmail);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User with email " + newEmail + " was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a user by name
    public static void deleteUser(String name) {
        String sql = "DELETE FROM student WHERE name = ?";
        try (Connection connection = Data_Manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User with name " + name + " was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main method to demonstrate the use of threads with insert, update, delete, and read operations
    public static void main(String[] args) {
        // Thread for reading user data
        Thread readThread = new Thread(thread_java::readUsers);

        // Thread for inserting a user
        Thread insertThread = new Thread(() -> insertUser("John Doe", "New York", "CSE"));

        // Thread for updating a user
        Thread updateThread = new Thread(() -> updateUser("Jane Doe", "Los Angeles", "janedoe@example.com", "CSE"));

        // Thread for deleting a user
        Thread deleteThread = new Thread(() -> deleteUser("Frank Blue"));

        // Start threads
        readThread.start();
        insertThread.start();
        updateThread.start();
        deleteThread.start();

        try {
            // Join threads to ensure they complete before the main method exits
            readThread.join();
            insertThread.join();
            updateThread.join();
            deleteThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Database operations completed.");
    }
}
