/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import com.intrashare.server.ui.MainFrameServer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class FileServer {

    /**
     * **
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(1610);
            System.out.println("File Server Ready");
            while (true) {
                Socket serverEnd = serverSocket.accept();
                new FileThread(serverEnd);
            }
        } catch (Exception e) {
            System.out.println("Ecxeption in ServerSocket (FileServer)");
        }

    }

}

class FileThread implements Runnable {

    Socket serverEnd;

    /**
     * ***
     *
     * @param s
     */
    FileThread(Socket s) {
        System.out.println("thread created");
        this.al = new ArrayList<Byte>();
        this.serverEnd = s;
        new Thread(this).start();
    }

    /**
     *
     *
     */
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(serverEnd.getInputStream()));
            InputStream fromClient = serverEnd.getInputStream();
            OutputStream toClient = serverEnd.getOutputStream();

            fileName = in.readLine();
            userName = in.readLine();

            System.out.println("before while");
            int i;
            while ((i = fromClient.read()) != -1) {
                al.add((byte) i);
            }
            System.out.println("aaya kya???");

            byte[] idk = new byte[al.size()];

            for (int o = 0; o < al.size(); o++) {
                idk[o] = (byte) al.get(o);
            }
            
            createFile(idk, fileName, userName);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception in Threaded class : " + e.getMessage(), "ServerFile", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param content
     * @param fileName
     * @param userName
     * 
     */
    public static void createFile(byte[] content, String fileName, String userName) {
        System.out.println("File name in UserRegistrationFolder = " + userName);

            try (FileOutputStream out = new FileOutputStream(MainFrameServer.getPath() + "\\" + userName.trim() + "\\" + fileName.trim())) {
                out.write(content);
            } catch (FileNotFoundException ex) {
            Logger.getLogger(FileThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //variable declarations...!!
    private String fileName = null;
    private String userName = null;
    ArrayList<Byte> al;
    
}
