/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patro
 */
public class DownloadServer {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(1615);
            System.out.println("Download Server Ready");
            while (true) {
                Socket serverEnd = serverSocket.accept();
                new DownloadThread(serverEnd);
            }
        } catch (Exception e) {
            System.out.println("Ecxeption in ServerSocket (DownloadServer)");
        }

    }
}

class DownloadThread implements Runnable {

    Socket serverEnd;

    public DownloadThread(Socket s) {
        this.serverEnd = s;
        new Thread(this).start();
    }

    @Override
    public void run() {
        BufferedReader fromClient = null;
        DataOutputStream toClient;
        try {
            fromClient = new BufferedReader(new InputStreamReader(serverEnd.getInputStream()));

        } catch (IOException ex) {
            Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            try {
                String userName = fromClient.readLine();
                String fileName = fromClient.readLine();
                byte[] fileContents = readFileContents("F:\\INTRA-SHARE\\User Folders\\" + userName + "\\" + fileName);
                System.out.println("userName =" + userName + "   FleName ===== " + fileName);
                toClient = new DataOutputStream(serverEnd.getOutputStream());
                toClient.writeInt(fileContents.length);
                toClient.write(fileContents);
                System.out.println("data sending completed (file k contents)");

            } catch (IOException ex) {
                Logger.getLogger(LoginThreadRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private byte[] readFileContents(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

}
