/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class RegistrationServer {

    public static void main(String[] args) throws Exception {
        ServerSocket sS = new ServerSocket(1611);
        System.out.println("Registration Server Ready");
        new RegistrationThreadRead(sS);
    }
}

class RegistrationThreadRead implements Runnable {

    ServerSocket sS;
    Socket serverEnd;
    Connection conn = null;

    RegistrationThreadRead(ServerSocket s) {

        conn = MySqlConnect.connectDB();
        this.sS = s;
        new Thread(this).start();
    }

    private void acceptClient() {
        try {
            serverEnd = sS.accept();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "exception in acceptiong client for Registration.....!!!!!");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                acceptClient();
                System.out.println("Received Registration Info");
                class T2 extends Thread {

                    Socket serverEnd;

                    public void setServer(Socket s) {
                        serverEnd = s;
                    }

                    @Override
                    public void run() {

                        BufferedReader fromClient = null;
                        try {
                            fromClient = new BufferedReader(new InputStreamReader(serverEnd.getInputStream()));
                        } catch (Exception ex) {
                            Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        while (true) {
                            try {
                                firstName = fromClient.readLine();
                                lastName = fromClient.readLine();
                                userName = fromClient.readLine();
                                password = fromClient.readLine();
                                confirmPassword = fromClient.readLine();
                            } catch (IOException ex) {
                                Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("data received \n firstName = " + firstName + " lastName = " + lastName + " userName = " + userName + " Password = " + password + " ConfirmPassword = " + confirmPassword);
                            boolean status = false;
                            try {
                                status = checkStatus();
                            } catch (SQLException ex) {
                                Logger.getLogger(RegistrationThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("status ==== " + status);
                            RegistrationThreadSend t1 = new RegistrationThreadSend(serverEnd);
                            t1.setStatus(status);
                        }
                    }
                }
                T2 t2 = new T2();
                t2.setServer(serverEnd);
                t2.start();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception in ServerRegister class : " + e.getMessage(), "ServerRegistrationFile", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static boolean checkStatus() throws SQLException {
        if (DatabaseOperations.insertRecord(firstName, lastName, userName, password, confirmPassword)) {
            FileFolderOperation.createFolder(userName);
            return true;
        }
        return false;
    }
    
    //Variable Declerastion...!!
    static String firstName = "", lastName = "", userName = "", password = "", confirmPassword = "";
}


class RegistrationThreadSend implements Runnable{
    Socket serverEnd;
    boolean status;
    
    RegistrationThreadSend(Socket s){
        System.out.println("inside Send wala Registration");
        this.serverEnd = s;
        this.status = status;
        new Thread(this).start();
    }
    
    public void setStatus(boolean status){
        this.status = status;
    }
    
    @Override
    public void run(){
        try {
            PrintWriter toClient = new PrintWriter(serverEnd.getOutputStream(), true);
            String stat = status +"";
            toClient.println(stat + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception in ServerLogin class : " + e.getMessage(), "ServerLoginFile", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Variable Decleration
    Connection conn = null;
    
    
}