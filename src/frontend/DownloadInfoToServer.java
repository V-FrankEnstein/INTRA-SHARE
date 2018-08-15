/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class DownloadInfoToServer {

    public DownloadInfoToServer() {
        this.al = new ArrayList<Byte>();
        establishConnection();
    }

    private void socketClose() throws IOException{
     
        fromServer.close();
        toServer.close();
        clientEndDownload.close();
        
    
    }
    
    
    private void establishConnection() {

        try {
            clientEndDownload = new Socket("localhost", 1615);
            //getting Streams...!!
            fromServer = new DataInputStream(clientEndDownload.getInputStream());
            toServer = clientEndDownload.getOutputStream();

            inDownload = new BufferedReader(new InputStreamReader(clientEndDownload.getInputStream()));
            outDownload = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientEndDownload.getOutputStream())));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception while Creating Socket " + e.getMessage(), "ClientToServer", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean sendFileInfo(String userName, String fileName) throws IOException {
        System.out.println("data sent to Download Server");
        outDownload.println(userName);
        outDownload.flush();

        outDownload.println(fileName);
        outDownload.flush();

        System.out.println("receiving data from server...!!");
//        int i;
//        while ((i = fromServer.read()) != -1) {
//            al.add((byte) i);
//        }
//        System.out.println("After While loop");
//        byte[] idk = new byte[al.size()];
//
//        for (int o = 0; o < al.size(); o++) {
//            idk[o] = (byte) al.get(o);
//        }
        int length = fromServer.readInt();
        if (length > 0) {
            byte[] message = new byte[length];
            fromServer.readFully(message, 0, message.length);
            createFile(message, fileName, userName);
        }

        

        return true;
    }

    public static void createFile(byte[] content, String fileName, String userName) {
        System.out.println("File name in UserRegistrationFolder = " + userName);
        try {
            //byte[] content = fileData.getBytes();
            //("F:\\INTRA-SHARE\\User Folders\\" +  userName.trim()   + "\\" + fileName.trim())
            String currentDir = System.getProperty("user.dir");
            try (FileOutputStream out = new FileOutputStream(/*"F:\\INTRA-SHARE\\DownloadedData\\admin"currentDir*/System.getProperty("user.home") + "\\Desktop" + "\\" + fileName.trim())) {

                //if(content != null)
                out.write(content);
            }
        } catch (IOException e) {

        }
    }

    //variable Declerations...!!
    DataInputStream fromServer;
    OutputStream toServer;
    BufferedReader inDownload;
    PrintWriter outDownload;
    Socket clientEndDownload;
    ArrayList<Byte> al;

}
