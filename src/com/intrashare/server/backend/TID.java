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
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("System tray icon demo");
            try {
                tray.add(trayIcon);
            } catch (AWTException ex) {
                Logger.getLogger(TID.class.getName()).log(Level.SEVERE, null, ex);
            }
                trayIcon.displayMessage(title, message, MessageType.INFO);

        } else {
            System.err.println("System tray not supported!");
            JOptionPane.showMessageDialog(null, "System Notification is Not Supported...!!", "TID", JOptionPane.ERROR);
        }
    }

}
