/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.platform.gui.panels;

import org.jdesktop.application.Action;
import org.wisenet.platform.common.ui.PlatformPanel;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.simulator.core.Simulator;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationPropertiesPanel extends PlatformPanel {

    /** Creates new form SimulationPropertiesPanel */
    public SimulationPropertiesPanel() {
        initComponents();
        loadSimulationSettings();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        simulationMode = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        tgbFastMode = new javax.swing.JToggleButton();
        tgbRealTimeMode = new javax.swing.JToggleButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Simulation Mode"));
        jPanel2.setName("jPanel2"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(SimulationPropertiesPanel.class, this);
        tgbFastMode.setAction(actionMap.get("SetFastMode")); // NOI18N
        simulationMode.add(tgbFastMode);
        tgbFastMode.setSelected(true);
        tgbFastMode.setText("Fast Mode"); // NOI18N
        tgbFastMode.setName("tgbFastMode"); // NOI18N

        tgbRealTimeMode.setAction(actionMap.get("SetRealtimeMode")); // NOI18N
        simulationMode.add(tgbRealTimeMode);
        tgbRealTimeMode.setText("Real Time Mode"); // NOI18N
        tgbRealTimeMode.setName("tgbRealTimeMode"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tgbFastMode, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(tgbRealTimeMode, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tgbRealTimeMode)
                    .addComponent(tgbFastMode))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void SetFastMode() {
    }

    @Action
    public void SetRealtimeMode() {
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.ButtonGroup simulationMode;
    private javax.swing.JToggleButton tgbFastMode;
    private javax.swing.JToggleButton tgbRealTimeMode;
    // End of variables declaration//GEN-END:variables

    private void loadSimulationSettings() {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            tgbFastMode.setSelected(PlatformManager.getInstance().getActiveSimulation().getSimulator().getMode() == Simulator.FAST);
            tgbRealTimeMode.setSelected(PlatformManager.getInstance().getActiveSimulation().getSimulator().getMode() == Simulator.REAL);
        }
    }

    @Override
    public boolean onCancel() {
        return true;
    }

    @Override
    public boolean onOK() {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            if (tgbFastMode.isSelected()) {
                PlatformManager.getInstance().getActiveSimulation().getSimulator().setMode(Simulator.FAST);
            }

            if (tgbRealTimeMode.isSelected()) {
                PlatformManager.getInstance().getActiveSimulation().getSimulator().setMode(Simulator.REAL);
            }
        }
        return true;
    }

    @Override
    public boolean onApply() {
        return onOK();
    }

    @Override
    protected boolean isDataValid() {
        return true;
    }
    @Override
    public void beforeClose() {
    }
}
