/*
 * SimulationWizardDialog.java
 *
 * Created on Mar 7, 2010, 5:04:38 PM
 */
package org.wisenet.platform.ui.frames;

import java.awt.Color;

import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JTextField;
import org.jdesktop.application.Action;
import org.wisenet.platform.conf.ClassConfigReader.ClassDefinitions;
import org.wisenet.platform.utils.GUI_Utils;

import org.wisenet.platform.conf.ConfigurationUtils;
import org.wisenet.platform.core.instruments.energy.ui.EnergyModelDialog;
import org.wisenet.simulator.components.simulation.SimulationFactory;
import org.wisenet.simulator.core.energy.EnergyModel;

/**
 *
 * @author posilva
 */
public class SimulationWizardDialog extends JDialog {

    private boolean ok;
    private FocusListener focusListener;
    private SimulationFactory sf;
    private EnergyModel energyModel;
    private boolean energyModelConfig = false;

    /** Creates new form SimulationWizardDialog */
    public SimulationWizardDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        focusListener = new FocusListener() {

            public void focusGained(FocusEvent fe) {
                JComponent source = ((JComponent) fe.getSource());
                source.setBackground(Color.yellow);
                if (source instanceof JTextField) {
                    ((JTextField) source).selectAll();
                }
            }

            public void focusLost(FocusEvent fe) {
                ((JComponent) fe.getSource()).setBackground(Color.WHITE);
            }
        };

        configClasses();

        cboNodeClass.addFocusListener(focusListener);
        cboRadioModelClass.addFocusListener(focusListener);
        cboSimulatorClass.addFocusListener(focusListener);
        txtSimulationName.addFocusListener(focusListener);
        txtSimulationDescription.addFocusListener(focusListener);

        setTitle("Simulation Wizard");
        GUI_Utils.centerOnScreen((Window) this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboSimulatorClass = new javax.swing.JComboBox();
        cboRadioModelClass = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboNodeClass = new javax.swing.JComboBox();
        cmdEnergyModel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        nodeRangeSlider = new javax.swing.JSlider();
        lblNodeRangeValue = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblSimName = new javax.swing.JLabel();
        txtSimulationName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSimulationDescription = new javax.swing.JTextField();
        titleArea = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cmdOK = new javax.swing.JButton();
        cmdCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.wisenet.platform.PlatformApp.class).getContext().getResourceMap(SimulationWizardDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setModal(true);
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel4.setName("jPanel4"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setFont(lblSimName.getFont());
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        cboSimulatorClass.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSimulatorClass.setName("cboSimulatorClass"); // NOI18N

        cboRadioModelClass.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboRadioModelClass.setName("cboRadioModelClass"); // NOI18N

        jLabel4.setFont(lblSimName.getFont());
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, lblSimName.getFont(), resourceMap.getColor("jPanel3.border.titleColor"))); // NOI18N
        jPanel3.setFont(jPanel3.getFont().deriveFont(jPanel3.getFont().getStyle() | java.awt.Font.BOLD, jPanel3.getFont().getSize()-1));
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel2.setFont(lblSimName.getFont());
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        cboNodeClass.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNodeClass.setName("cboNodeClass"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.wisenet.platform.PlatformApp.class).getContext().getActionMap(SimulationWizardDialog.class, this);
        cmdEnergyModel.setAction(actionMap.get("configureEnergyModel")); // NOI18N
        cmdEnergyModel.setText(resourceMap.getString("cmdEnergyModel.text")); // NOI18N
        cmdEnergyModel.setToolTipText(resourceMap.getString("cmdEnergyModel.toolTipText")); // NOI18N
        cmdEnergyModel.setName("cmdEnergyModel"); // NOI18N

        jLabel3.setFont(lblSimName.getFont());
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        nodeRangeSlider.setMaximum(300);
        nodeRangeSlider.setMinimum(30);
        nodeRangeSlider.setMinorTickSpacing(100);
        nodeRangeSlider.setPaintLabels(true);
        nodeRangeSlider.setPaintTicks(true);
        nodeRangeSlider.setSnapToTicks(true);
        nodeRangeSlider.setToolTipText(resourceMap.getString("nodeRangeSlider.toolTipText")); // NOI18N
        nodeRangeSlider.setValue(130);
        nodeRangeSlider.setName("nodeRangeSlider"); // NOI18N
        nodeRangeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                nodeRangeSliderStateChanged(evt);
            }
        });

        lblNodeRangeValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNodeRangeValue.setText(resourceMap.getString("lblNodeRangeValue.text")); // NOI18N
        lblNodeRangeValue.setName("lblNodeRangeValue"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cboNodeClass, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmdEnergyModel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(nodeRangeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNodeRangeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboNodeClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdEnergyModel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(nodeRangeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNodeRangeValue))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboRadioModelClass, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboSimulatorClass, javax.swing.GroupLayout.Alignment.TRAILING, 0, 339, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboSimulatorClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboRadioModelClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2"); // NOI18N

        lblSimName.setFont(resourceMap.getFont("lblSimName.font")); // NOI18N
        lblSimName.setText(resourceMap.getString("lblSimName.text")); // NOI18N
        lblSimName.setName("lblSimName"); // NOI18N

        txtSimulationName.setBackground(resourceMap.getColor("txtSimulationName.background")); // NOI18N
        txtSimulationName.setText(resourceMap.getString("txtSimulationName.text")); // NOI18N
        txtSimulationName.setName("txtSimulationName"); // NOI18N
        txtSimulationName.setOpaque(false);

        jLabel6.setFont(lblSimName.getFont());
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        txtSimulationDescription.setText(resourceMap.getString("txtSimulationDescription.text")); // NOI18N
        txtSimulationDescription.setName("txtSimulationDescription"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSimName, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSimulationDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .addComponent(txtSimulationName, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSimName)
                    .addComponent(txtSimulationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSimulationDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        titleArea.setBackground(resourceMap.getColor("titleArea.background")); // NOI18N
        titleArea.setFont(resourceMap.getFont("titleArea.font")); // NOI18N
        titleArea.setText(resourceMap.getString("titleArea.text")); // NOI18N
        titleArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        titleArea.setMaximumSize(new java.awt.Dimension(200, 30));
        titleArea.setMinimumSize(new java.awt.Dimension(200, 30));
        titleArea.setName("titleArea"); // NOI18N
        titleArea.setOpaque(true);
        titleArea.setPreferredSize(new java.awt.Dimension(200, 40));
        getContentPane().add(titleArea, java.awt.BorderLayout.PAGE_START);

        jPanel5.setName("jPanel5"); // NOI18N

        cmdOK.setText(resourceMap.getString("cmdOK.text")); // NOI18N
        cmdOK.setMinimumSize(new java.awt.Dimension(25, 50));
        cmdOK.setName("cmdOK"); // NOI18N
        cmdOK.setPreferredSize(new java.awt.Dimension(50, 24));
        cmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOKActionPerformed(evt);
            }
        });

        cmdCancel.setText(resourceMap.getString("cmdCancel.text")); // NOI18N
        cmdCancel.setName("cmdCancel"); // NOI18N
        cmdCancel.setPreferredSize(new java.awt.Dimension(50, 24));
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(386, Short.MAX_VALUE)
                .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel5, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOKActionPerformed
        if (validateData()) {
            ok = true;
            setVisible(false);
        } else {
            ok = false;
        }
}//GEN-LAST:event_cmdOKActionPerformed

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        ok = false;
        setVisible(false);
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void nodeRangeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_nodeRangeSliderStateChanged
        // TODO add your handling code here:
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            int value = (int) source.getValue();
            lblNodeRangeValue.setText(value + "");
        }
    }//GEN-LAST:event_nodeRangeSliderStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboNodeClass;
    private javax.swing.JComboBox cboRadioModelClass;
    private javax.swing.JComboBox cboSimulatorClass;
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdEnergyModel;
    private javax.swing.JButton cmdOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblNodeRangeValue;
    private javax.swing.JLabel lblSimName;
    private javax.swing.JSlider nodeRangeSlider;
    private javax.swing.JLabel titleArea;
    private javax.swing.JTextField txtSimulationDescription;
    private javax.swing.JTextField txtSimulationName;
    // End of variables declaration//GEN-END:variables

    private boolean validateData() {
        try {
            sf = new SimulationFactory();
            sf.setSimulatorClass(getClassInstance(((ClassDefinitions) cboSimulatorClass.getSelectedItem()).className));
            sf.setRadioModelClass(getClassInstance(((ClassDefinitions) cboRadioModelClass.getSelectedItem()).className));
            sf.setNodeFactoryClass(getClassInstance(((ClassDefinitions) cboNodeClass.getSelectedItem()).className));
            sf.setNodeRange(nodeRangeSlider.getValue());
            if (!energyModelConfig) {
                energyModel = EnergyModel.getDefaultInstance();
            }
            sf.setEnergyModel(energyModel);

            return true;
        } catch (InstantiationException ex) {
            GUI_Utils.showException(ex);
        } catch (IllegalAccessException ex) {
            GUI_Utils.showException(ex);
        }
        sf = null;
        return false;

    }

    Class getClassInstance(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SimulationWizardDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isOk() {
        return ok;
    }

    public SimulationFactory getSimulationFactory() {

        return sf;
    }

    private void configClasses() {
        ConfigurationUtils.loadComboWithClasses(cboSimulatorClass, ConfigurationUtils.loadConfigurationClasses(ConfigurationUtils.CONF_SIMULATOR_CLASSES_PROPERTIES));
        ConfigurationUtils.loadComboWithClasses(cboRadioModelClass, ConfigurationUtils.loadConfigurationClasses(ConfigurationUtils.CONF_RADIOMODEL_CLASSES_PROPERTIES));
        ConfigurationUtils.loadComboWithClasses(cboNodeClass, ConfigurationUtils.loadConfigurationClasses(ConfigurationUtils.CONF_NODE_CLASSES_PROPERTIES));
    }

    @Action
    public void configureEnergyModel() {

        EnergyModelDialog emd = new EnergyModelDialog(null, true);
        emd.setVisible(true);
        if (emd.isOk()) {
            energyModel = emd.getEnergyModel();
            energyModelConfig = true;
        }
        emd.dispose();
    }
}