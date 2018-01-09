/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class DeleteServer {

    public static void main(String[] args) throws Exception {
        ServerSocket sS = new ServerSocket(1616);

        System.out.println("Delete Server Ready");
        new DeleteThreadDelete(sS);

    }
}

class DeleteThreadDelete implements Runnable {

    Socket serverEnd = new Socket();
    ServerSocket sS;

    DeleteThreadDelete(ServerSocket s) {
        this.sS = sS;
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

                System.out.println("receiving deletion info");
                class T4 extends Thread {

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

                            DeleteThreadSend t = new DeleteThreadSend(serverEnd);
                            t.setStatus(status);
                        }
                    }
                }
                T4 t4 = new T4();
                t4.setServer(serverEnd);
                t4.start();
            } catch (Exception e) {

            }
        }
    }

    public static boolean checkStatus() {
            File file = new File("F:\\INTRA-SHARE\\DownloadedData\\admin\\" + fileName);
            return file.delete();
    }

    static String userName = "", fileName = "";
}

class DeleteThreadSend implements Runnable {

    Socket serverEnd;
    boolean status;

    DeleteThreadSend(Socket s) {
        this.serverEnd = s;
        this.status = status;
        new Thread(this).start();
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public void run(){
        try {
            PrintWriter toClient = new PrintWriter(serverEnd.getOutputStream(),true );
            String stat = status + "";
            toClient.println(stat + "");
        } catch (Exception e) {
            System.out.println("catch exception in DeleteServer");
        }
    }

    
    
}
