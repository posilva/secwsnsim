/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.platform.utils;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.utils.gui.ErrorHandler;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
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
        f.setLocation((screenWidth - f.getWidth()) / 2, (screenHeight - f.getHeight()) / 2);
    }

    public static void showMessage(String message, int type) {

        JOptionPane.showMessageDialog(null, message, "Message", type);

    }

    public static boolean confirm(String message) {
        Object[] o = {"Yes", "No"};

        return JOptionPane.showOptionDialog(null, message, "Confirm...", -1, ASK_MESSAGE, null, o, o[1]) == JOptionPane.YES_OPTION;
    }

    /**
     * If any 0 is the default file filter
     * @param filters
     * @param title
     * @return
     */
    public static String showSaveDialog(FileFilter[] filters, String title) throws IOException {
        JFileChooser jf = new JFileChooser();
        if (filters != null) {
            jf.setAcceptAllFileFilterUsed(false);
            for (int i = 0; i < filters.length; i++) {
                jf.addChoosableFileFilter(filters[i]);
            }
            jf.setFileFilter(filters[0]);

        }
        File f = new File(new File(".").getCanonicalPath());
        jf.setCurrentDirectory(f);
        jf.setDialogTitle(title);

        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
        ErrorHandler.displayThrowable(ex, "Platform Error ", null, null);
    }

    public static FileFilter XML() {
        return new FileFilter() {

            String[] extensions = new String[]{"xml"};

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                // Ok, itвЂ™s a regular file, so check the extension
                String name = f.getName().toLowerCase();
                for (int i = extensions.length - 1; i >= 0; i--) {
                    if (name.endsWith(extensions[i])) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String getDescription() {
                return "XML file";
            }
        };
    }

    public static String showOpenDialog(FileFilter[] filters, String title) throws IOException {
        JFileChooser jf = new JFileChooser();
        if (filters != null) {
            jf.setAcceptAllFileFilterUsed(false);
            for (int i = 0; i < filters.length; i++) {
                jf.addChoosableFileFilter(filters[i]);
            }
            jf.setFileFilter(filters[0]);
        }
        File f = new File(new File(".").getCanonicalPath());
        jf.setCurrentDirectory(f);
        jf.setDialogTitle(title);
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jf.setMultiSelectionEnabled(false);
        Component c = null;
        if (PlatformManager.getInstance().getPlatformView() != null) {
            c = PlatformManager.getInstance().getPlatformView().getFrame();
        }
        int result = jf.showOpenDialog(c);
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

    public static void setFocus(final JTextField textField) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                textField.requestFocus();
            }
        });
    }

    public static String showSavePersistentObjectDialog(String msg) throws IOException {
        String file = showSaveDialog(new FileFilter[]{XML()}, msg);
        if (!file.toLowerCase().endsWith(".xml")) {
            file = file + ".xml";
        }
        return file;
    }
}
