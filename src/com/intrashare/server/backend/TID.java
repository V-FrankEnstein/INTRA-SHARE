/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intrashare.server.backend;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Patro
 */
public class TID {

    public void notificationOnWindows(String title, String message) {
        if (SystemTray.isSupported()) {
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            //If the icon is a file
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            //Alternative (if the icon is on the classpath):
            // Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));
            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            //Let the system resizes the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("System tray icon demo");
            try {
                tray.add(trayIcon);
            } catch (AWTException ex) {
                Logger.getLogger(TID.class.getName()).log(Level.SEVERE, null, ex);
            }
//            int i = 0;
//            if (i == 0) {
                trayIcon.displayMessage(title, message, MessageType.INFO);
//            }

        } else {
            System.err.println("System tray not supported!");
            JOptionPane.showMessageDialog(null, "System Notification is Not Supported...!!", "TID", JOptionPane.ERROR);
        }
    }

}
