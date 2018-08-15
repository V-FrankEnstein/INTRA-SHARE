/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class LoginInfoToServer implements Serializable {

    public LoginInfoToServer() {
        hm = new HashMap<>();
        System.out.println("constructor of " + getClass());
        establishConnection();
    }

    public LoginInfoToServer(String args) {
        hm = new HashMap<>();
    }

    
    public void socketClose() throws IOException{
//        toServer.writeUTF("terminate");
        toServer.close();
        fromServer.close();
        clientEndRegistration.close();
        
    }
    
    private boolean establishConnection() {
        try {
            clientEndRegistration = new Socket("localhost", 1612);
            toServer = new ObjectOutputStream(clientEndRegistration.getOutputStream());
            fromServer = new ObjectInputStream(clientEndRegistration.getInputStream());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception while Creating Socket " + e.getMessage(), "ClientToServer", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean sendLoginInfo(String logXml) throws IOException, ClassNotFoundException {

        System.out.println("sending to Server");
        String[] data = logXml.split(" ");
        System.out.println("sending data from sendLoginInfo = " + data[0] + " " + data[1]);
        toServer.writeObject(data[0]);
        toServer.writeObject(data[1]);
        System.out.println("before  flushing");
        toServer.flush();
        System.out.println("data sent");

        System.out.println("before reading");
        String sou = (String) fromServer.readObject();
        System.out.println("Aagaya data dekh le = " + sou);
        if (sou.equals("true")) {
            hm = (HashMap<String, HashMap<String,HashMap<Long, Long>>>) fromServer.readObject();
            setHm(hm);
            return true;
        }
        return false;
    }

    public static HashMap<String, HashMap<String,HashMap<Long, Long>>> getHm() {

        return hm;
    }

    public void setHm(HashMap<String, HashMap<String, HashMap<Long, Long>>> hm) {
        this.hm = hm;
    }

//    Variable Declaration
    Socket clientEndRegistration;
    ObjectInputStream fromServer;
    ObjectOutputStream toServer;
    PrintWriter outRegister;
    static HashMap<String, HashMap<String,HashMap<Long, Long>>> hm;
    boolean receiveStatus = false;
}
