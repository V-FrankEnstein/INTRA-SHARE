/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import com.intrashare.server.ui.MainFrameServer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;

/**
 * 
 * @author Patro
 */
public class FileFolderOperation {

    public static boolean createServerFolder(String path){
        File f = new File(path);
//        if(!f.exists()){
            f.mkdirs();
            return true;
//        }
//        return false;
    }
    
    
    public static boolean createFolder(String userName) {
        System.out.println("Folder creation");
        File f = new File(MainFrameServer.getPath() + "\\" + userName.toLowerCase());
        if (!f.exists()) {
            return f.mkdir();
        }
        return false;
    }
    
//    public static boolean deleteFile(String fileName, String userName){
//        System.out.println("File Deletion");
//        
//    }
    
    
    public static void createFile(byte[] content, String fileName, String userName) {
        System.out.println("inside createFile()");
        try {
            try (FileOutputStream out = new FileOutputStream(MainFrameServer.getPath() + "\\" + userName.trim() + "\\" + fileName.trim())) {
                System.out.println("Inside writing wsthjkopygfhjigjdxf");
                out.write(content);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IOException in createFile", "FileFolderOperation", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static byte[] readFileContents(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
}
