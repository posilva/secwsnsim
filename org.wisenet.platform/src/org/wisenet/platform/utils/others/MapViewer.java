/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */

/*
 * MapViewer.java
 *
 * Created on Jan 24, 2010, 2:47:12 PM
 */
package org.wisenet.platform.utils.others;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.wisenet.platform.PlatformConfiguration;
import org.wisenet.platform.utils.GUI_Utils;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class MapViewer extends javax.swing.JDialog {

    public double MOVE_STEP = 5.0E-5;
    JPanel p = new ImageCanvas();
    private BufferedImage image = null;
    private boolean applyOk = false;

    public void applyIMageSettings() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            GoogleMaps.setApiKey(PlatformConfiguration.CFG_GOOGLE_MAPS_KEY_KEY_DEFAULT);
            //   double[] lanLng = GoogleMaps.geocodeAddress("Academia da Força Aérea, Portugal");
            image = (BufferedImage) toBufferedImage(GoogleMaps.retrieveStaticImage((Integer) (this.w.getValue()), (Integer) this.h.getValue(), Double.valueOf(this.x.getText()), Double.valueOf(this.y.getText()), (Integer) (this.zoom.getModel().getValue()), "jpeg32"));
            refreshUI();
        } catch (Exception ex) {
            Logger.getLogger(MapViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCursor(Cursor.getDefaultCursor());
    }

    private void refreshUI() {
        this.repaint();
        this.invalidate();
        p.repaint();
        p.updateUI();
    }

    /** Creates new form MapViewer */
    public MapViewer(Image image) {
        initComponents();

        p.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.add(p);
        jScrollPane1.setViewportView(p);
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            public void adjustmentValueChanged(AdjustmentEvent arg0) {
                refreshUI();
            }
        });
        jScrollPane1.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            public void adjustmentValueChanged(AdjustmentEvent arg0) {
                refreshUI();
            }
        });
        int _w = 800;
        int _h = 600;

        if (image == null) {
        } else {
            this.image = toBufferedImage(image);
            _w = image.getWidth(this);
            _h = image.getHeight(this) + 20;
        }
        this.setSize(new Dimension(_w, _h));
        p.setSize(new Dimension(_w, _h));
        p.setPreferredSize(p.getSize());
        p.setMaximumSize(p.getSize());
        p.setMinimumSize(p.getSize());
        GUI_Utils.centerOnScreen(this);
    }

    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
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
            return (BufferedImage) image;
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

        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        x = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        y = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        h = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        w = new javax.swing.JSpinner();
        zoom = new javax.swing.JSlider();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnApply = new javax.swing.JButton();
        btnUpdate1 = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        offset = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        up = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        left = new javax.swing.JButton();
        down = new javax.swing.JButton();
        rigth = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setMinimumSize(new java.awt.Dimension(640, 120));
        jPanel5.setPreferredSize(new java.awt.Dimension(640, 120));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel3.setText("Latitude:");

        x.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        x.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        x.setText("38,835780");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel6.setText("Longitude:");

        y.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        y.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        y.setText("-9,334334");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel7.setText("Height:");

        h.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        h.setModel(new javax.swing.SpinnerNumberModel(1024, 256, 1024, 32));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel8.setText("Width:");

        w.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        w.setModel(new javax.swing.SpinnerNumberModel(1024, 256, 1024, 32));
        w.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                wStateChanged(evt);
            }
        });

        zoom.setFont(new java.awt.Font("Arial", 1, 8)); // NOI18N
        zoom.setMaximum(20);
        zoom.setSnapToTicks(true);
        zoom.setToolTipText("Zoom");
        zoom.setValue(19);
        zoom.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        zoom.setExtent(1);
        zoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zoomStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel9.setText("Zoom:");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnApply.setText("OK");
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });
        jPanel1.add(btnApply, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 90, -1));

        btnUpdate1.setText("Preview");
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, -1));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 90, -1));

        offset.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        offset.setModel(new javax.swing.SpinnerNumberModel(5.0E-6d, 5.0E-6d, 1.0d, 1.0E-6d));
        offset.setEditor(new javax.swing.JSpinner.NumberEditor(offset, "####0.###############"));
        offset.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                offsetStateChanged(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new java.awt.GridLayout(2, 3, 2, 2));
        jPanel2.add(jPanel4);

        up.setFont(new java.awt.Font("Courier 10 Pitch", 0, 10));
        up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/utils/others/up-icon.png"))); // NOI18N
        up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upActionPerformed(evt);
            }
        });
        jPanel2.add(up);
        jPanel2.add(jPanel3);

        left.setFont(new java.awt.Font("Courier 10 Pitch", 0, 10));
        left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/utils/others/back-icon.png"))); // NOI18N
        left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftActionPerformed(evt);
            }
        });
        jPanel2.add(left);

        down.setFont(new java.awt.Font("Courier 10 Pitch", 0, 10));
        down.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/utils/others/down-icon.png"))); // NOI18N
        down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downActionPerformed(evt);
            }
        });
        jPanel2.add(down);

        rigth.setFont(new java.awt.Font("Courier 10 Pitch", 0, 10)); // NOI18N
        rigth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/utils/others/forward-icon.png"))); // NOI18N
        rigth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rigthActionPerformed(evt);
            }
        });
        jPanel2.add(rigth);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, 0, 0, Short.MAX_VALUE)
                    .addComponent(offset, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(offset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(x, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(w, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(y, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(zoom, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(w, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(zoom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel5, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private double D(String v) {
        return Double.valueOf(v);
    }
    private void upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upActionPerformed
        x.setText("" + (D(x.getText()) + MOVE_STEP));
        applyIMageSettings();
    }//GEN-LAST:event_upActionPerformed

    private void downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downActionPerformed
        x.setText("" + (D(x.getText()) - MOVE_STEP));
        applyIMageSettings();
        // TODO add your handling code here:
    }//GEN-LAST:event_downActionPerformed

    private void rigthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rigthActionPerformed
        y.setText("" + (D(y.getText()) + MOVE_STEP));
        applyIMageSettings();
    }//GEN-LAST:event_rigthActionPerformed

    private void leftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftActionPerformed
        y.setText("" + (D(y.getText()) - MOVE_STEP));
        applyIMageSettings();
        // TODO add your handling code here:
    }//GEN-LAST:event_leftActionPerformed

    private void offsetStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_offsetStateChanged
        MOVE_STEP = D("" + offset.getModel().getValue());
    }//GEN-LAST:event_offsetStateChanged

    private void zoomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomStateChanged
        applyIMageSettings();
    }//GEN-LAST:event_zoomStateChanged

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
        applyIMageSettings();
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void wStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_wStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_wStateChanged

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed

        applyOk = true;
        dispose();
    }//GEN-LAST:event_btnApplyActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    public BufferedImage getImage() {
        return image;
    }

    class ImageCanvas extends JPanel {

        @Override
        public void paint(Graphics grphcs) {
            if (image != null) {
                grphcs.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            GoogleMaps.setApiKey(PlatformConfiguration.CFG_GOOGLE_MAPS_KEY_KEY_DEFAULT);
            //   double[] lanLng = GoogleMaps.geocodeAddress("Academia da Força Aérea, Portugal");

            Image image = GoogleMaps.retrieveStaticImage(2024, 2024, 38.835780, -9.334334, 20, "jpeg32");
            MapViewer f = new MapViewer(image);
            f.repaint();
            f.setVisible(true);
        } catch (Exception ex) {
            System.out.println("ex:" + ex.getMessage());
        }


    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.JButton down;
    private javax.swing.JSpinner h;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton left;
    private javax.swing.JSpinner offset;
    private javax.swing.JButton rigth;
    private javax.swing.JButton up;
    private javax.swing.JSpinner w;
    private javax.swing.JTextField x;
    private javax.swing.JTextField y;
    private javax.swing.JSlider zoom;
    // End of variables declaration//GEN-END:variables

    public boolean isApplyOk() {
        return applyOk;
    }
}
