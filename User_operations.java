package com.org.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_operations {

    public static void readUsers() {
        String sql = "SELECT * FROM student";
        
        try (Connection connection = Data_Manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String dept= resultSet.getString("dept");
                String address = resultSet.getString("address");

                System.out.println( "Name: " + name + ", Address: " + address + ", Department: " + dept);
            }
        } catch (SQLException e) {
            System.out.println("Error reading user data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        readUsers();
    }
}