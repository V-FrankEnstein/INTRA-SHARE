/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class FileServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1610);
        System.out.println("File Server Ready");
        while (true) {
            Socket serverEnd = serverSocket.accept();
            new FileThread(serverEnd);
        }
    }
    
}

class FileThread implements Runnable {

    Socket serverEnd;

    FileThread(Socket s) {
        System.out.println("thread created");
        this.al = new ArrayList<Byte>();
        this.serverEnd = s;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(serverEnd.getInputStream()));
//            PrintWriter toClient = new PrintWriter(serverEnd.getOutputStream(), true);
            InputStream fromClient = serverEnd.getInputStream();
            OutputStream toClient = serverEnd.getOutputStream();
            // while (true) {
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

    public static void createFile(byte[] content, String fileName, String userName) {
        System.out.println("File name in UserRegistrationFolder = " + userName);
        try {
            //byte[] content = fileData.getBytes();
            //("F:\\INTRA-SHARE\\User Folders\\" +  userName.trim()   + "\\" + fileName.trim())
            try (FileOutputStream out = new FileOutputStream("F:\\INTRA-SHARE\\User Folders\\" + userName.trim() + "\\" + fileName.trim())) {

                //if(content != null)
                out.write(content);
            }
        } catch (IOException e) {

        }
    }

    //variable declarations...!!
    private String receivedData = "";
    private String fileName = null;
    private String userName = null;
    ArrayList<Byte> al;

}
