/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class DeleteInfoToServer {

    public DeleteInfoToServer() {
        establishConnection();
    }

    private boolean establishConnection() {

        try {
            clientEndDelete = new Socket("localhost", 1616);
            //getting Streams...!!
            inDelete = new BufferedReader(new InputStreamReader(clientEndDelete.getInputStream()));
            outDelete = new PrintWriter(clientEndDelete.getOutputStream(), true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception while Creating Socket " + e.getMessage(), "ClientToServer", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean sendFileInfo(String userName, String fileName) throws IOException {
        System.out.println("data sent to Delete Server");
        outDelete.println(userName);
        outDelete.println(fileName);
        System.out.println("data sent");
        
        String sou = inDelete.readLine();
        System.out.println("Aagaya data dekh le = " +  sou);
        if(sou.equals("true")){
            
            System.out.println("inside if of lits");
            return true;
        }
        

        return false;
    }

    //variable Declerations...!!
    Socket clientEndDelete;
    BufferedReader inDelete;
    PrintWriter outDelete;

}
