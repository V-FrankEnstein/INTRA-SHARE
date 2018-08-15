/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class DatabaseOperations implements ConstantsInterface {

    static Statement statement;

    public static boolean insertRecord(String s1, String s2, String s3, String s4, String s5) throws SQLException {
        conn = MySqlConnect.connectDB();
        statement = conn.createStatement();
        try {
            statement.execute("INSERT INTO userdetails (firstname, lastname, username, password, confirmpassword) VALUES("
                    + "'" + s1 + "', " + "'" + s2 + "', "
                    + "'" + s3 + "', " + "'" + s4 + "', "
                    + "'" + s5 + "')");
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception in executing SQL Sattement  =  " + e.getMessage(), "Database Operation", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean ifUserExists(String userName) {
        System.out.println("inside ifUserExists");
        try {
            String sql = "SELECT * FROM userdetails WHERE username = ?";
            conn = MySqlConnect.connectDB();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println("Successfull");
                return true;
            }
            System.out.println("Unsuccessfull");
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean ifClientExists(String userName, String password) {
        System.out.println("inside if client exists");
        try {
            String sql = "SELECT * FROM userdetails WHERE username = ? AND password = ?";
            conn = MySqlConnect.connectDB();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            System.out.println("ServerSide database operation file    UserName = " + userName + " Password =  " + password);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println("Successfull");
                return true;

            } else {
                System.out.println("Unsuccessfull");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Exception");
        }
        return false;
    }

    //Variable and object Declaration...!!
    static PreparedStatement preparedStatement;
    static ResultSet rs;
    static Connection conn = null;
}
