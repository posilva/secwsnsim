/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RoutingInfoPanel.java
 *
 * Created on 7/Jul/2010, 0:53:33
 */
package org.mei.securesim.platform.ui.panels;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.mei.securesim.components.ApplicationOutput;
import org.mei.securesim.components.IOutputDisplay;

/**
 *
 * @author CIAdmin
 */
public class ApplicationOutputPanel extends OutputPanel implements IOutputDisplay {

    private JTextArea txtAreaOutput = new JTextArea();
    private final JScrollPane jScrollPane1 = new JScrollPane();

    /** Creates new form RoutingInfoPanel */
    public ApplicationOutputPanel() {
        super();
        ApplicationOutput.getInstance().subscribe(this);
        getTitleArea().setText(" Application Layer Output");
        txtAreaOutput.setEditable(false);
        getContentArea().setLayout(new BorderLayout());
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setViewportView(txtAreaOutput);
        getContentArea().add(jScrollPane1, BorderLayout.CENTER);

    }

    public static ApplicationOutputPanel getInstance() {
        if (instance == null) {
            instance = new ApplicationOutputPanel();
        }
        return (ApplicationOutputPanel) instance;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void showOutput(String text) {
        txtAreaOutput.append(text + "\n");
    }
}
