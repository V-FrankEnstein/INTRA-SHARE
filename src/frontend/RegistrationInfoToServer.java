/*
 * To change this license header, choose License Headers inRegister Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template inRegister the editor. 
 * close the socket when logout 
 */
package frontend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class RegistrationInfoToServer {

    public RegistrationInfoToServer() {
        establishConnection();
    }

    public RegistrationInfoToServer(String userName) {
        RegistrationInfoToServer.userName = userName;
        establishConnection();
    }

    private void socketClose() throws IOException{
        
        inRegister.close();
        outRegister.close();
        clientEndRegistration.close();
        
        
    }
    
    
    private boolean establishConnection() {
        try {
            clientEndRegistration = new Socket("localhost", 1611);
            //getting Streams...!!
            fromServer = clientEndRegistration.getInputStream();
            toServer = clientEndRegistration.getOutputStream();
            inRegister = new BufferedReader(new InputStreamReader(clientEndRegistration.getInputStream()));
            outRegister = new PrintWriter(clientEndRegistration.getOutputStream(), true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception while Creating Socket " + e.getMessage(), "ClientToServer", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean sendRegistrationInfo(String regXml) throws IOException {
        String[] data = regXml.split(" ");

        outRegister.println(data[0]);
        outRegister.println(data[1]);
        outRegister.println(data[2]);
        outRegister.println(data[3]);
        outRegister.println(data[4]);

        String sou = inRegister.readLine();
        System.out.println("Aagaya data dekh le = " + sou);
        if (sou.equals("true")) {
            System.out.println("inside if of lits");
            return true;
        }
        return false;
    }

    public void uploadFileToServer() throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File sourceFile = fileChooser.getSelectedFile();

        if (sourceFile != null) {
            filePath = sourceFile.getAbsolutePath();
            fileName = sourceFile.getName();
            byte[] fileData = readFileContents(filePath);

            //sending data to server...!!
            toServer.write(getFileName().getBytes());
            toServer.write(getUserName().getBytes());
            toServer.write(fileData);
        }

    }

    private byte[] readFileContents(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    //getters
    public static String getFileName() {
        return fileName;
    }

    public static String getUserName() {
        return userName;
    }

    //Variable Declaration
    Socket clientEndRegistration;
    InputStream fromServer;
    OutputStream toServer;
    BufferedReader inRegister;
    PrintWriter outRegister;
    private String regXml = "";
    private String filePath = null;
    private static String fileName = null;
    private String fileData = null;
    private static String userName = null;

}
