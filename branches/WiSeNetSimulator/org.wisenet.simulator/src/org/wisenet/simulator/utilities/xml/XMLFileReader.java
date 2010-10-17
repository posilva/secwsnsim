/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class XMLFileReader {

    String filename;
    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder;
    Document doc;
    private boolean opened = false;
    Node rootNode;

    /**
     *
     * @param filename
     */
    public XMLFileReader(String filename) {
        this.filename = filename;
    }

    /**
     *
     */
    public XMLFileReader() {
    }

    /**
     *
     * @return
     */
    public boolean open() {
        opened = false;
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(new File(this.filename));
            doc.getDocumentElement().normalize();
            rootNode = doc.getDocumentElement();
        } catch (Exception ex) {
            Utilities.handleException(ex);
            return false;
        }
        opened = true;
        return true;
    }

    /**
     *
     * @param filename
     * @return
     */
    public boolean open(String filename) {
        this.filename = filename;
        return open();
    }

    /**
     *
     * @return
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     *
     * @return
     */
    public Node getRootNode() {
        if (isOpened()) {
            return rootNode;
        } else {
            return null;
        }
    }

    /**
     *
     * @param tagName
     * @return
     */
    public NodeList getList(String tagName) {
        if (isOpened()) {
            return doc.getElementsByTagName(tagName);
        } else {
            return null;
        }
    }

    private void close() {
        opened = false;
        // do nothing
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        XMLFileReader reader = new XMLFileReader();
        reader.open("simulation.sim");
        NodeList nList = reader.getList("Simulation");
        System.out.println("LIST Len: " + nList.getLength());
        Node item =nList.item(0);
        if (item.hasChildNodes())
            System.out.println("Childs: "+item.getChildNodes().getLength());
        reader.close();
    }
}
