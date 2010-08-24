/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImageViewer.java
 *
 * Created on Jan 24, 2010, 2:47:12 PM
 */

package org.wisenet.platform.utils.others;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author posilva
 */
public class ImageViewer extends javax.swing.JFrame {

    private BufferedImage image=null;

    /** Creates new form ImageViewer */
    public ImageViewer(Image image) {
        initComponents();
        JPanel p = new ImageCanvas();
        this.image = toBufferedImage(image);
        int w = image.getWidth(this);
        int h =image.getHeight(this)+20;
        this.setSize(new Dimension(w,h));
        p.setSize(new Dimension(w,h));
        p.setPreferredSize(p.getSize());
        p.setMaximumSize(p.getSize());
        p.setMinimumSize(p.getSize());

        add(p,BorderLayout.CENTER                );

    }

    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
         PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
}

public static BufferedImage toBufferedImage(Image image) {
    if (image instanceof BufferedImage) {
        return (BufferedImage)image;
    }

    // This code ensures that all the pixels in the image are loaded
    image = new ImageIcon(image).getImage();

    // Determine if the image has transparent pixels; for this method's
    // implementation, see Determining If an Image Has Transparent Pixels
    boolean hasAlpha = hasAlpha(image);

    // Create a buffered image with a format that's compatible with the screen
    BufferedImage bimage = null;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    try {
        // Determine the type of transparency of the new buffered image
        int transparency = Transparency.OPAQUE;
        if (hasAlpha) {
            transparency = Transparency.BITMASK;
        }

        // Create the buffered image
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        bimage = gc.createCompatibleImage(
            image.getWidth(null), image.getHeight(null), transparency);
    } catch (HeadlessException e) {
        // The system does not have a screen
    }

    if (bimage == null) {
        // Create a buffered image using the default color model
        int type = BufferedImage.TYPE_INT_RGB;
        if (hasAlpha) {
            type = BufferedImage.TYPE_INT_ARGB;
        }
        bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
    }

    // Copy image to buffered image
    Graphics g = bimage.createGraphics();

    // Paint the image onto the buffered image
    g.drawImage(image, 0, 0, null);
    g.dispose();

    return bimage;
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    class ImageCanvas extends JPanel{

        @Override
    public void paint(Graphics grphcs) {
        grphcs.drawImage(image, 0, 0,image.getWidth(this),image.getHeight(this), this);
     }


    }


    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
     
        try {
            GoogleMaps.setApiKey("ABQIAAAAXg0F4Zi2pcwHeCjCvk7LRhSoSGvlWgPKK04fS0Rib--DYJNIihQASC7FQc_5lQHTrrgdlZoWfL-eZg");
         //   double[] lanLng = GoogleMaps.geocodeAddress("Academia da Força Aérea, Portugal");
            Image image =GoogleMaps.retrieveStaticImage(1024,600, 38.835780, -9.334334, 19, "jpeg32");
            ImageViewer f = new ImageViewer(image);
            f.repaint();
            f.setVisible(true);
        } catch (Exception ex) {
            System.out.println("ex:" + ex.getMessage());
        }


    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
