/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import com.intrashare.server.ui.MainFrameServer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class DeleteServer {

    public static void main(String[] args) {

        try {
            ServerSocket sS = new ServerSocket(1616);

            System.out.println("Delete Server Ready");
            new DeleteThreadRead(sS);
        } catch (Exception e) {
            System.out.println("Exception in ServerSocet (DeleteServer)");
        }

    }

}

class DeleteThreadRead implements Runnable {

    Socket serverEnd;
    ServerSocket sS;

    DeleteThreadRead(ServerSocket s) {

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
                acceptClient();

                System.out.println("Received Login Info");
                class T6 extends Thread {

                    Socket serverEnd;

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
                                fileName = fromClient.readLine();
                            } catch (IOException ex) {
                                Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            boolean status = checkStatus();

                            DeleteThreadSend t = new DeleteThreadSend(serverEnd);//To change body of generated methods, choose Tools | Templates.
                            t.setStatus(status);
                            t.setUserName(userName);
                            t.setFileName(fileName);
                        }
                    }
                }
                T6 t6 = new T6();
                t6.setServer(serverEnd);
                t6.start();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception in ServerLogin class : " + e.getMessage(), "ServerLoginFile", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static boolean checkStatus() {
        try {
            return Files.deleteIfExists(Paths.get(MainFrameServer.getPath() + "\\" + userName + "\\" + fileName));
        } catch (IOException ex) {
            Logger.getLogger(DeleteThreadRead.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    static String userName = "", fileName = "";
}

class DeleteThreadSend implements Runnable {

    Socket serverEnd;
    boolean status;
    String userName, fileName;

    
    private void socketClose() throws IOException{
     
        serverEnd.close();
        
    }
    
    
    public DeleteThreadSend(Socket serverEnd) {
        this.serverEnd = serverEnd;
        this.status = status;
        new Thread(this).start();
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {

        try {
//            ObjectOutputStream out = new ObjectOutputStream(serverEnd.getOutputStream());
            PrintWriter toClient = new PrintWriter(new BufferedWriter(new OutputStreamWriter(serverEnd.getOutputStream())));

            String stat = status + "";
            toClient.println(stat + "");
            toClient.flush();

        } catch (Exception e) {
            System.out.println("catch exception");
        }
    }
}
