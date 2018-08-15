/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Patro
 */
public class FileToServer {

    public FileToServer(String userName) {
         this.userName = userName;
        //Establishes connecttion to Server...!!
        establishConnection();
    }

    private boolean establishConnection() {
        try {
            clientEnd = new Socket("localhost", 1610);
            System.out.println("Connected to localhost at port 1610");
            //getting Streams...!!
            fromServer = clientEnd.getInputStream();
            toServer = clientEnd.getOutputStream();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception while Creating Socket " + e.getMessage(), "ClientToServer", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean uploadFileToServer(String fileName, byte[] fileData) throws Exception {
        
        System.out.println("ClientDataToServer Execution Started...!!");
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(toServer)));
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.showOpenDialog(null);
//        File sourceFile = fileChooser.getSelectedFile();

//        if (sourceFile != null) {
//            this.filePath = sourceFile.getAbsolutePath();
//            FileToServer.fileName = sourceFile.getName();
//            byte[] fileData = readFileContents(this.filePath);
//            System.out.println("File name [Upload] = " + getFileName());
//            System.out.println("user name [Upload] = " + getUserName());
            //UserRegistrationFolder.createFile(fileData, fileName, userName);
//            System.out.println("File Data ===============  " + Arrays.toString(fileData));
            out.println(fileName);
            out.flush();
            out.println(userName);
            out.flush();
            toServer.write(fileData);
            System.out.println("Data is sent to server...!!");
            clientEnd.close();
            return true;

    }
    
    private byte[] readFileContents(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public static String getFileName() {
        return fileName;
    }

    public static String getUserName() {
        return userName;
    }

    
    
    
    //Variable Declaration
     Socket clientEnd;
    InputStream fromServer;
    OutputStream toServer;
    DefaultTableModel dtm ;
    private String filePath = null;
    private static String fileName = null;
    private static String userName = null;
}
