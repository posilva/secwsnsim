/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.platform.utils;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Window;

/**
 *
 * @author posilva
 */
public class GUI_Utils {

    public static void centerOnScreen(Window f) throws HeadlessException {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        f.setLocation(screenWidth / 4, screenHeight / 4);
    }

//    public static String InputBox(String title) {
//        return JOptionPane.showInputDialog(title)
//    }
//
//    public static void MessageBox(String title, String message) {
//        JOptionPane.showInputDialog(title)
//    }
//
//    public static boolean AskBox(String title, String message) {
//        return JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog(null,title,);
//    }
}
