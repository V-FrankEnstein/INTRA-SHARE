/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

    public static void main(String[] args) {
        try {
            ServerSocket sS = new ServerSocket(1612);
            System.out.println("Login Server Ready");
            new LoginThreadRead(sS);
        } catch (Exception e) {
            System.out.println("Exception on Server Socket..>>!!");
        }

    }

}

class LoginThreadRead implements Runnable {

    Socket serverEnd;
    ServerSocket sS;

    /**
     *
     * @param s
     */
    LoginThreadRead(ServerSocket s) {

        conn = MySqlConnect.connectDB();
        this.sS = s;
        new Thread(this).start();
    }

    private void acceptClient() {
        try {
            serverEnd = sS.accept();
            fromClient = new ObjectInputStream(serverEnd.getInputStream());
            toClient = new ObjectOutputStream(serverEnd.getOutputStream());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "exception in acceptiong client.....!!!!!");
        }
    }

    ObjectInputStream fromClient = null;
    ObjectOutputStream toClient = null;

    @Override
    public void run() {
        while (true) {
            try {
                acceptClient();
                class T1 extends Thread implements Serializable {

                    Socket serverEnd;
                    HashMap<String, String> mapObj = new HashMap<>();

                    public void setServer(Socket s) {
                        serverEnd = s;
                    }

                    @Override
                    public void run() {

//                        while (true) {
                            try {
                                userName = (String) fromClient.readObject();
                                password = (String) fromClient.readObject();
                                System.out.println("Data aagaya userNamer = " + userName + " Password = " + password);
                            } catch (IOException ex) {
                                Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            boolean status = checkStatus();

                            LoginThreadSend t = new LoginThreadSend(serverEnd);
                            t.setStatus(status);
                            t.setToClient(toClient);
                            t.setUserName(userName);
//                        }
                    }
                }
                T1 t1 = new T1();
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

    /**
     *
     * @param s
     */
    LoginThreadSend(Socket s) {
        System.out.println("inside Send waala");
        this.serverEnd = s;
        this.status = status;
        new Thread(this).start();
    }

    /**
     *
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     *
     * @param toClient
     */
    public void setToClient(ObjectOutputStream toClient) {
        this.toClient = toClient;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void run() {
        try {
            System.out.println("before sending status =======" + status);
            String stat = status + "";
            toClient.writeObject(stat);
            System.out.println("status walasss data bhej diya server se client ko");
            
            if (stat.equals("true")) {
                System.out.println("inside trueeeeeeeeeeeeeeeeeeeeeeeeeeee");
                HashMap<String, HashMap<String, HashMap<Long, Long>>> hm = new ObjectMap().createHashMap(userName);
                System.out.println("got the HM");
                toClient.writeObject(hm);
                
            }
            toClient.flush();
        } catch (Exception e) {
            System.out.println("catch exception" + e);
        }
    }

    //Variable decleration
    String RegistrationAllData = "";
    Statement statement;
    Connection conn = null;
    ResultSet rs;
    ObjectOutputStream toClient = null;
    String userName = "";
}
