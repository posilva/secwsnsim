/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.platform.utils;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.utils.gui.ErrorHandler;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
 * @author posilva
 */
public class GUI_Utils {

    public static int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
    public static int INFO_MESSAGE = JOptionPane.INFORMATION_MESSAGE;
    public static int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
    public static int SIMPLE_MESSAGE = JOptionPane.PLAIN_MESSAGE;
    public static int ASK_MESSAGE = JOptionPane.QUESTION_MESSAGE;

    public static void mouseWait(Component c) {
        Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
        c.setCursor(hourglassCursor);
    }

    public static void mouseDefault(Component c) {
        Cursor hourglassCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        c.setCursor(hourglassCursor);
    }

    public static void centerOnScreen(Window f) throws HeadlessException {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        f.setLocation(screenWidth / 4, screenHeight / 4);
    }

    public static void showMessage(String message, int type) {

        JOptionPane.showMessageDialog(null, message, "Message", type);

    }

    public static boolean confirm(String message) {
        Object[] o = {"Yes", "No"};

        return JOptionPane.showOptionDialog(null, message, "Confirm...", -1, ASK_MESSAGE, null, o, o[1]) == JOptionPane.YES_OPTION;
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

    /**
     * If any 0 is the default file filter
     * @param filters
     * @param title
     * @return
     */
    public static String showSaveDialog(FileFilter[] filters, String title) {
        JFileChooser jf = new JFileChooser();
        if (filters != null) {
            jf.setAcceptAllFileFilterUsed(false);
            for (int i = 0; i < filters.length; i++) {
                jf.addChoosableFileFilter(filters[i]);
            }
            jf.setFileFilter(filters[0]);
        }
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jf.setMultiSelectionEnabled(false);
        int result = jf.showSaveDialog(PlatformManager.getInstance().getPlatformView().getFrame());
        if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ERROR_OPTION) {
            return null;
        }
        return jf.getSelectedFile().getAbsolutePath();
    }

    /**
     * 
     * @param ex
     */
    public static void showException(Exception ex) {
//
//        JOptionPane.showMessageDialog(null, Utilities.getStackTrace(ex), "Error occured", ERROR_MESSAGE);
        ErrorHandler.displayThrowable(ex, "Platform Error ", null, null);
    }

    public static String showOpenDialog(FileFilter[] filters, String title) {
        JFileChooser jf = new JFileChooser();
        if (filters != null) {
            jf.setAcceptAllFileFilterUsed(false);
            for (int i = 0; i < filters.length; i++) {
                jf.addChoosableFileFilter(filters[i]);
            }
            jf.setFileFilter(filters[0]);
        }
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jf.setMultiSelectionEnabled(false);
        int result = jf.showOpenDialog(PlatformManager.getInstance().getPlatformView().getFrame());
        if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ERROR_OPTION) {
            return null;
        }
        return jf.getSelectedFile().getAbsolutePath();
    }

    public static void showWarningMessage(String msg) {
        showMessage(msg, JOptionPane.WARNING_MESSAGE);
    }
    public static void showMessage(String msg) {
        showMessage(msg, JOptionPane.PLAIN_MESSAGE);
    }
}
