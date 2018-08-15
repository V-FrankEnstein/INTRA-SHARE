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
    public static void main(String args[]) {
            DS ds = new DS();
            FS fs = new FS();
            RS rs = new RS();
            LS ls = new LS();
            DES des = new DES();      
    }
}
class DS extends Thread{
    DS(){
        this.start();
    }
    
    @Override
    public void run(){
        try {
            DownloadServer.main(null);
        } catch (Exception ex) {
            Logger.getLogger(DS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
class FS extends Thread{

    public FS() {
        this.start();
    }
    
    @Override
    public void run(){
        try {
            FileServer.main(null);
        } catch (Exception ex) {
            Logger.getLogger(FS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
class RS extends Thread{

    public RS() {
        this.start();
    }
    
    @Override
    public void run(){
        try {
            RegistrationServer.main(null);
        } catch (Exception ex) {
            Logger.getLogger(RS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class LS extends Thread{

    public LS() {
        this.start();
    }
    
    @Override
    public void run(){
        try {
            LoginServer.main(null);
        } catch (Exception ex) {
            Logger.getLogger(LS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
class DES extends Thread{

    public DES() {
        this.start();
    }
    
    @Override
    public void run(){
        try {
            DeleteServer.main(null);
        } catch (Exception ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}