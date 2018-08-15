/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import com.intrashare.server.ui.MainFrameServer;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Patro
 */
public class ObjectMap {

    public HashMap<String, HashMap<String, HashMap<Long, Long>>> createHashMap(String userName) {
        File dir = new File(MainFrameServer.getPath() + "\\" + userName);
        File[] dirFiles = dir.listFiles();

        HashMap<String, HashMap<String, HashMap<Long, Long>>> objMap = new HashMap<>();

//        for (int i = 0; i < dirFiles.length; i++) {
//            if (dirFiles[i].isFile()) {
//                hashObj.put(dirFiles[i].getName(), dirFiles[i].getPath());
//            }
//        }
        for (int i = 0; i < dirFiles.length; i++) {
            if (dirFiles[i].isFile()) {
                objMap.put(dirFiles[i].getPath(), new HashMap<String, HashMap<Long, Long>>());
                objMap.get(dirFiles[i].getPath()).put(dirFiles[i].getName(), new HashMap<Long, Long>());
                objMap.get(dirFiles[i].getPath()).get(dirFiles[i].getName()).put(dirFiles[i].length(), dirFiles[i].lastModified());
                System.out.println(dirFiles[i].lastModified());
            }
        }

        return objMap;
    }

//    public static void putValueInHashMap(String name, String path, long size, long lastModified) {
//
//    }

}
