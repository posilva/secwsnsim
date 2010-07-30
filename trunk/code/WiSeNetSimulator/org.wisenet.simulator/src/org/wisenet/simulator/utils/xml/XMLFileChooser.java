package org.wisenet.simulator.utils.xml;


import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


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
	 */
    public static JFileChooser getJFileChooser(String title, String[] fileTypes, String description) {
		xmlfilefilter = new XMLFileFilter(fileTypes, description);
		filechooser.setSize(300, 300);
		filechooser.setDialogTitle(title);
		filechooser.addChoosableFileFilter(xmlfilefilter);
        return filechooser;
    }

    public static JFileChooser getJFileChooser(String title, String defaultDirectory){
        xmlfilefilter = new XMLFileFilter("xml");
        filechooser=new JFileChooser(defaultDirectory);
		filechooser.setSize(300, 300);
		filechooser.setDialogTitle(title);
		filechooser.addChoosableFileFilter(xmlfilefilter);
        return filechooser;
    }

}