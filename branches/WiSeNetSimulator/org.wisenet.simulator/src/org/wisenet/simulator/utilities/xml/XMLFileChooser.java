package org.wisenet.simulator.utilities.xml;

import javax.swing.JFileChooser;

/**
 * file chooser for xml files.
 *
 * @author WD
 */
public class XMLFileChooser {

    /**
     * the base file chooser.
     */
    private static JFileChooser filechooser;// = new JFileChooser(AppFrame.getDefaultDirectory());
    /**
     * XML file filter.
     */
    private static XMLFileFilter xmlfilefilter;

    /**
     * Constructor to open XML files only.
     * @param title : String
     * @param fileTypes
     * @param description
     * @return
     */
    public static JFileChooser getJFileChooser(String title, String[] fileTypes, String description) {
        xmlfilefilter = new XMLFileFilter(fileTypes, description);
        filechooser.setSize(300, 300);
        filechooser.setDialogTitle(title);
        filechooser.addChoosableFileFilter(xmlfilefilter);
        return filechooser;
    }

    /**
     *
     * @param title
     * @param defaultDirectory
     * @return
     */
    public static JFileChooser getJFileChooser(String title, String defaultDirectory) {
        xmlfilefilter = new XMLFileFilter("xml");
        filechooser = new JFileChooser(defaultDirectory);
        filechooser.setSize(300, 300);
        filechooser.setDialogTitle(title);
        filechooser.addChoosableFileFilter(xmlfilefilter);
        return filechooser;
    }
}
