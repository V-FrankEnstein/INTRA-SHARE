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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */

public class LoginServer {

    public static void main(String[] args) throws Exception {
        ServerSocket sS = new ServerSocket(1612);
        
        System.out.println("Login Server Ready");
        new LoginThreadRead(sS);
        
    }
//        new LoginThreadSend(serverEnd);

}

class LoginThreadRead implements Runnable {

    Socket serverEnd;
    ServerSocket sS;

    LoginThreadRead(ServerSocket s) {

        conn = MySqlConnect.connectDB();
        this.sS = s;
        new Thread(this).start();
    }

    private void acceptClient() {
        try {
            serverEnd = sS.accept();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "exception in acceptiong client.....!!!!!");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                acceptClient();//mere num pe ca
                
                
                System.out.println("Received Login Info");
                class T1 extends Thread{
                    Socket serverEnd;
                    HashMap<String, String> mapObj = new HashMap<>();

                    public void setServer(Socket s) {
                        serverEnd = s;
                    }
                    
                    @Override
                    public void run() {

                        BufferedReader fromClient = null;
                        try {
                            fromClient = new BufferedReader(new InputStreamReader(serverEnd.getInputStream()));
                        } catch (IOException ex) {
                            Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        while (true) {
                            try {
                                userName = fromClient.readLine();
                                password = fromClient.readLine();
                            } catch (IOException ex) {
                                Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            boolean status = checkStatus();
//                            if(status == true){
//                                mapObj = ObjectMap.createHashMap(userName);
//                            }
                                
                            LoginThreadSend t = new LoginThreadSend(serverEnd);//To change body of generated methods, choose Tools | Templates.
                            t.setStatus(status);
                        }
                    }
                }
                T1 t1 =new T1();
                t1.setServer(serverEnd);
                t1.start();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception in ServerLogin class : " + e.getMessage(), "ServerLoginFile", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static boolean checkStatus() {
        return DatabaseOperations.ifClientExists(userName, password);
    }

    //Variable decleration
    String RegistrationAllData = "";
    Statement statement;
    Connection conn = null;
    ResultSet rs;
    static String userName = "", password = "";

    
}

class LoginThreadSend implements Runnable {

    Socket serverEnd;
    boolean status;

    LoginThreadSend(Socket s) {
        System.out.println("inside Send waala");
        this.serverEnd = s;
        this.status = status;
        new Thread(this).start();
    }

    
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void run() {
//        System.out.println("inside run");
//        while (true) {
        try {
//            System.out.println("inside try of send");
//            OutputStream in = serverEnd.getOutputStream();
            PrintWriter toClient = new PrintWriter(serverEnd.getOutputStream(),true );
         
//            System.out.println("before sending status =======" + status);
            String stat = status + "";
            toClient.println(stat + "");
//            System.out.println("data sent to client");
//                    stat = "";
//                    if(stat.equals(""))
//                        break;
        } catch (Exception e) {
            System.out.println("catch exception");
//            JOptionPane.showMessageDialog(null, "Exception in ServerLogin class : " + e.getMessage(), "ServerLoginFile", JOptionPane.ERROR_MESSAGE);
        }
//        }
    }

    //Variable decleration
    String RegistrationAllData = "";
    Statement statement;
    Connection conn = null;
    ResultSet rs;
}
