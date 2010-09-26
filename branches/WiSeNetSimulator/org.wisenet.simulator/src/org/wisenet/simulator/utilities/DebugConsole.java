/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import java.io.*;

/**
 * http://tanksoftware.com/juk/developer/src/com/
 *     tanksoftware/util/RedirectedFrame.java
 * A Java Swing class that captures output to the command line
 ** (eg, System.out.println)
 * RedirectedFrame
 * <p>
 * This class was downloaded from:
 * Java CodeGuru (http://codeguru.earthweb.com/java/articles/382.shtml) <br>
 * The origional author was Real Gagnon (real.gagnon@tactika.com);
 * William Denniss has edited the code, improving its customizability
 *
 * In breif, this class captures all output to the system and prints it in
 * a frame. You can choose weither or not you want to catch errors, log
 * them to a file and more.
 * For more details, read the constructor method description
 */
public class DebugConsole extends JFrame implements WindowListener {

    protected static DebugConsole instance;

    protected PrintStream sysOut;
    protected PrintStream sysErr;

    public static DebugConsole getInstance() {
        if (instance == null) {
            instance = new DebugConsole(true, false, null, 700, 600, JFrame.DISPOSE_ON_CLOSE);
        }
        return instance;
    }

    public DebugConsole(boolean catchErrors, boolean logFile, String fileName, int width,
            int height, int closeOperation) {

        DebugConsolePanel p = new DebugConsolePanel(catchErrors, logFile, fileName);
        setDefaultCloseOperation(closeOperation);

        setLayout(new BorderLayout());
        add(p, BorderLayout.CENTER);

        setSize(width, height);
        pack();
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        System.setErr(sysErr);
        System.setOut(sysOut);

    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public class DebugConsolePanel extends JPanel {

        private boolean catchErrors;
        private boolean logFile;
        private String fileName;
        TextArea aTextArea = new TextArea();
        TextArea aTextAreaErr = new TextArea();
        PrintStream aPrintStream =
                new PrintStream(
                new BufferedOutputStream(
                new FilteredStream(
                new ByteArrayOutputStream())));
        
        PrintStream aPrintStreamErr =
                new PrintStream(
                new BufferedOutputStream(
                new FilteredStreamErr(
                new ByteArrayOutputStream())));

        /** Creates a new RedirectFrame.
         *  From the moment it is created,
         *  all System.out messages and error messages (if requested)
         *  are diverted to this frame and appended to the log file
         *  (if requested)
         *
         * for example:
         *  RedirectedFrame outputFrame =
         *       new RedirectedFrame
        (false, false, null, 700, 600, JFrame.DO_NOTHING_ON_CLOSE);
         * this will create a new RedirectedFrame that doesn't catch errors,
         * nor logs to the file, with the dimentions 700x600 and it doesn't
         * close this frame can be toggled to visible, hidden by a controlling
         * class by(using the example) outputFrame.setVisible(true|false)
         *  @param catchErrors set this to true if you want the errors to
         *         also be caught
         *  @param logFile set this to true if you want the output logged
         *  @param fileName the name of the file it is to be logged to
         *  @param width the width of the frame
         *  @param height the height of the frame
         *  @param closeOperation the default close operation
         *        (this must be one of the WindowConstants)
         */
        public DebugConsolePanel(boolean catchErrors, boolean logFile, String fileName) {
            sysOut=System.out;
            sysErr=System.err;

            this.catchErrors = catchErrors;
            this.logFile = logFile;
            this.fileName = fileName;

            Container c = this;

            c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
            c.add(aTextArea);
            aTextArea.setEditable(false);
            c.add(aTextAreaErr);
            aTextAreaErr.setEditable(false);

            displayLog();

            this.logFile = logFile;

            System.setOut(aPrintStream); // catches System.out messages
            if (catchErrors) {
                System.setErr(aPrintStreamErr); // catches error messages
            }
            // set the default closing operation to the one given

//        Toolkit tk = Toolkit.getDefaultToolkit();
//        Image im = tk.getImage("myicon.gif");
//        setIconImage(im);
        }

        class FilteredStream extends FilterOutputStream {

            public FilteredStream(OutputStream aStream) {
                super(aStream);
            }

            @Override
            public void write(byte b[]) throws IOException {
                String aString = new String(b);
                aTextArea.append(aString);
            }

            @Override
            public void write(byte b[], int off, int len) throws IOException {
                String aString = new String(b, off, len);
                aTextArea.append(aString);
                if (logFile) {
                    FileWriter aWriter = new FileWriter(fileName, true);
                    aWriter.write(aString);
                    aWriter.close();
                }
            }
        }

        class FilteredStreamErr extends FilterOutputStream {

            public FilteredStreamErr(OutputStream aStream) {
                super(aStream);
            }

            @Override
            public void write(byte b[]) throws IOException {
                String aString = new String(b);
                aTextAreaErr.append(aString);
            }

            @Override
            public void write(byte b[], int off, int len) throws IOException {
                String aString = new String(b, off, len);
                aTextAreaErr.append(aString);
                if (logFile) {
                    FileWriter aWriter = new FileWriter(fileName + ".err", true);
                    aWriter.write(aString);
                    aWriter.close();
                }
            }
        }

        private void displayLog() {
            Dimension dim = getToolkit().getScreenSize();
            Rectangle abounds = getBounds();
            Dimension dd = getSize();
            setLocation((dim.width - abounds.width) / 2,
                    (dim.height - abounds.height) / 2);
            setVisible(true);
            requestFocus();
        }
    }

    public static void main(String[] args) {
        DebugConsole.getInstance().setVisible(true);
        System.out.println("teste");

    }
}


