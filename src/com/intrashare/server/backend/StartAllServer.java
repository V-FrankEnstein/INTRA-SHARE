/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patro
 */
public class StartAllServer {
    public static void main(String args[]){
        try {
            
            LoginServer.main(null);
            RegistrationServer.main(null);
            DownloadServer.main(null);
            FileServer.main(null);
            
            
        } catch (Exception ex) {  
            Logger.getLogger(StartAllServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
