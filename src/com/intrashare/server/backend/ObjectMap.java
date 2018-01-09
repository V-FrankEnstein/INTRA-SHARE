/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Patro
 */
public class ObjectMap {

    public static HashMap<String, String> createHashMap(String userName) {
        File dir = new File("F:\\INTRA-SHARE\\User Folders\\" + userName);
        File[] dirFiles = dir.listFiles();

        HashMap<String, String> hashObj = new HashMap<>();

        for (int i = 0; i < dirFiles.length; i++) {
            if (dirFiles[i].isFile()) {
                hashObj.put(dirFiles[i].getName(), dirFiles[i].getPath());
            }
        }
        
  
    return hashObj;
    }

}
