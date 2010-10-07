/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */

/*
 * TestBuilderPanel.java
 *
 * Created on Oct 4, 2010, 12:07:49 AM
 */
package org.wisenet.platform.gui.panels;

import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import org.wisenet.platform.PlatformView;
import org.wisenet.platform.common.ui.PlatformDialog;
import org.wisenet.platform.common.ui.PlatformFrame;
import org.wisenet.platform.common.ui.PlatformPanel;
import org.wisenet.platform.utils.GUI_Utils;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.evaluation.tests.DefaultTest;
import org.wisenet.simulator.components.evaluation.tests.TestInputParameters;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksEntry;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class TestBuilderPanel extends PlatformPanel {

    protected boolean addToSim;
    protected Simulation simulation = null;
    AbstractTest test;
    TestInputParameters inputParameters = new TestInputParameters();

    /** Creates new form TestBuilderPanel */
    public TestBuilderPanel() {
        initComponents();
    }

    private int INT(JTextField text) throws NumberFormatException {
        return Integer.parseInt(text.getText());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNrMessagesPerNode = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtIntervalBetweenMessages = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNrRetransmissions = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        cboAttacks = new javax.swing.JComboBox();
        cmdLoadFromFile = new javax.swing.JButton();
        cmdSaveToFile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtNrAttackNodes = new javax.swing.JFormattedTextField();
        chkReceiverNodesSinknodes = new javax.swing.JCheckBox();
        chkSenderNodesPercent = new javax.swing.JCheckBox();
        txtNrSenderNodes = new javax.swing.JFormattedTextField();
        chkSenderNodesStable = new javax.swing.JCheckBox();
        chkReceiverNodesPercent = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        txtNrReceiverNodes = new javax.swing.JFormattedTextField();
        chkAttackNodesPercent = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        chkAttackNodesStable = new javax.swing.JCheckBox();
        txtTestName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTestDescription = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        activateNow = new javax.swing.JCheckBox();

        jLabel1.setText("Test Name:");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Test Description:");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel5.setText("No. of messages sent:");
        jLabel5.setName("jLabel5"); // NOI18N

        txtNrMessagesPerNode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNrMessagesPerNode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNrMessagesPerNode.setText("1");
        txtNrMessagesPerNode.setName("txtNrMessagesPerNode"); // NOI18N
        txtNrMessagesPerNode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateValuerOnFocusLost(evt);
            }
        });

        jLabel6.setText("Interval between message:");
        jLabel6.setName("jLabel6"); // NOI18N

        txtIntervalBetweenMessages.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIntervalBetweenMessages.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIntervalBetweenMessages.setText("10");
        txtIntervalBetweenMessages.setName("txtIntervalBetweenMessages"); // NOI18N
        txtIntervalBetweenMessages.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateValuerOnFocusLost(evt);
            }
        });

        jLabel7.setText("No. of retransmissions:");
        jLabel7.setName("jLabel7"); // NOI18N

        txtNrRetransmissions.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNrRetransmissions.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNrRetransmissions.setText("1");
        txtNrRetransmissions.setName("txtNrRetransmissions"); // NOI18N
        txtNrRetransmissions.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateValuerOnFocusLost(evt);
            }
        });

        jLabel8.setText("Selected Attack:");
        jLabel8.setName("jLabel8"); // NOI18N

        cboAttacks.setName("cboAttacks"); // NOI18N

        cmdLoadFromFile.setText("Load from file...");
        cmdLoadFromFile.setName("cmdLoadFromFile"); // NOI18N
        cmdLoadFromFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoadFromFileActionPerformed(evt);
            }
        });

        cmdSaveToFile.setText("Save to file...");
        cmdSaveToFile.setName("cmdSaveToFile"); // NOI18N
        cmdSaveToFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveToFileActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        txtNrAttackNodes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNrAttackNodes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNrAttackNodes.setText("0");
        txtNrAttackNodes.setName("txtNrAttackNodes"); // NOI18N
        txtNrAttackNodes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateValuerOnFocusLost(evt);
            }
        });

        chkReceiverNodesSinknodes.setSelected(true);
        chkReceiverNodesSinknodes.setText("Only SinkNodes");
        chkReceiverNodesSinknodes.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkReceiverNodesSinknodes.setName("chkReceiverNodesSinknodes"); // NOI18N
        chkReceiverNodesSinknodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkReceiverNodesSinknodesActionPerformed(evt);
            }
        });

        chkSenderNodesPercent.setSelected(true);
        chkSenderNodesPercent.setText("Percentage");
        chkSenderNodesPercent.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkSenderNodesPercent.setName("chkSenderNodesPercent"); // NOI18N
        chkSenderNodesPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSenderNodesPercentActionPerformed(evt);
            }
        });

        txtNrSenderNodes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNrSenderNodes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNrSenderNodes.setText("0");
        txtNrSenderNodes.setName("txtNrSenderNodes"); // NOI18N
        txtNrSenderNodes.setSelectionEnd(txtNrSenderNodes.getText().length());
        txtNrSenderNodes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateValuerOnFocusLost(evt);
            }
        });

        chkSenderNodesStable.setSelected(true);
        chkSenderNodesStable.setText("Only Stable");
        chkSenderNodesStable.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkSenderNodesStable.setName("chkSenderNodesStable"); // NOI18N
        chkSenderNodesStable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSenderNodesStableActionPerformed(evt);
            }
        });

        chkReceiverNodesPercent.setSelected(true);
        chkReceiverNodesPercent.setText("Percentage");
        chkReceiverNodesPercent.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkReceiverNodesPercent.setName("chkReceiverNodesPercent"); // NOI18N
        chkReceiverNodesPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkReceiverNodesPercentActionPerformed(evt);
            }
        });

        jLabel9.setText("No. of attacked nodes:");
        jLabel9.setName("jLabel9"); // NOI18N

        txtNrReceiverNodes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNrReceiverNodes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNrReceiverNodes.setText("0");
        txtNrReceiverNodes.setName("txtNrReceiverNodes"); // NOI18N
        txtNrReceiverNodes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateValuerOnFocusLost(evt);
            }
        });

        chkAttackNodesPercent.setSelected(true);
        chkAttackNodesPercent.setText("Percentage");
        chkAttackNodesPercent.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkAttackNodesPercent.setName("chkAttackNodesPercent"); // NOI18N
        chkAttackNodesPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAttackNodesPercentActionPerformed(evt);
            }
        });

        jLabel4.setText("No. of receiver nodes:");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel3.setText("No. of sender nodes:");
        jLabel3.setName("jLabel3"); // NOI18N

        chkAttackNodesStable.setSelected(true);
        chkAttackNodesStable.setText("Only Stable");
        chkAttackNodesStable.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkAttackNodesStable.setName("chkAttackNodesStable"); // NOI18N
        chkAttackNodesStable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAttackNodesStableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNrSenderNodes, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(txtNrAttackNodes, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(txtNrReceiverNodes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(chkSenderNodesPercent)
                        .addComponent(chkReceiverNodesPercent))
                    .addComponent(chkAttackNodesPercent))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkSenderNodesStable)
                    .addComponent(chkReceiverNodesSinknodes)
                    .addComponent(chkAttackNodesStable))
                .addGap(55, 55, 55))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(txtNrSenderNodes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(chkSenderNodesPercent)
                    .addComponent(chkSenderNodesStable))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(chkReceiverNodesPercent)
                        .addComponent(txtNrReceiverNodes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chkReceiverNodesSinknodes))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(txtNrAttackNodes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(chkAttackNodesPercent))
                    .addComponent(chkAttackNodesStable))
                .addGap(16, 16, 16))
        );

        txtTestName.setName("txtTestName"); // NOI18N
        txtTestName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTestNameActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtTestDescription.setColumns(20);
        txtTestDescription.setRows(5);
        txtTestDescription.setName("txtTestDescription"); // NOI18N
        jScrollPane1.setViewportView(txtTestDescription);

        jSeparator1.setName("jSeparator1"); // NOI18N

        activateNow.setSelected(true);
        activateNow.setText("Activate Now");
        activateNow.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        activateNow.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        activateNow.setName("activateNow"); // NOI18N
        activateNow.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                activateNowStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmdLoadFromFile, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdSaveToFile, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                                .addComponent(activateNow, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(41, 41, 41))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNrRetransmissions, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIntervalBetweenMessages, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNrMessagesPerNode, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboAttacks, 0, 358, Short.MAX_VALUE)))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                                    .addComponent(txtTestName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))))))
                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtIntervalBetweenMessages, txtNrMessagesPerNode, txtNrRetransmissions});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtTestName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNrMessagesPerNode, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIntervalBetweenMessages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNrRetransmissions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboAttacks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(8, 8, 8)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdLoadFromFile)
                    .addComponent(cmdSaveToFile)
                    .addComponent(activateNow))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTestNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTestNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTestNameActionPerformed

    private void chkSenderNodesPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSenderNodesPercentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSenderNodesPercentActionPerformed

    private void chkSenderNodesStableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSenderNodesStableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSenderNodesStableActionPerformed

    private void chkReceiverNodesPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkReceiverNodesPercentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkReceiverNodesPercentActionPerformed

    private void chkReceiverNodesSinknodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkReceiverNodesSinknodesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkReceiverNodesSinknodesActionPerformed

    private void chkAttackNodesPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAttackNodesPercentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkAttackNodesPercentActionPerformed

    private void chkAttackNodesStableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAttackNodesStableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkAttackNodesStableActionPerformed

    private void validateValuerOnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_validateValuerOnFocusLost
        JFormattedTextField source = (JFormattedTextField) evt.getSource();

        if (source.getName().equals("")) {
            setValueWhenEmpty(source, "0");
        }

    }//GEN-LAST:event_validateValuerOnFocusLost

    private void cmdSaveToFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveToFileActionPerformed
        saveTestToFile();
    }//GEN-LAST:event_cmdSaveToFileActionPerformed

    private void activateNowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_activateNowStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_activateNowStateChanged

    private void cmdLoadFromFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoadFromFileActionPerformed
        loadFromFile();
    }//GEN-LAST:event_cmdLoadFromFileActionPerformed

    @Override
    public boolean onCancel() {
        return true;
    }

    @Override
    public boolean onOK() {
        if (isDataValid()) {

            result = test;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onApply() {
        return true;
    }

    @Override
    public void beforeStart() {
        super.beforeStart();
        if (simulation == null) {
            GUI_Utils.showWarningMessage("No existing simulation!\n Close Window!");
            return;
        }
        loadSimulationAttacks();

    }

    @Override
    protected boolean isDataValid() {
        if (simulation == null) {
            GUI_Utils.showWarningMessage("No existing simulation!");
            return false;
        }
        if (textBoxIsEmpty(txtTestName, "Test Name is required")) {
            return false;
        }
        if (textBoxIsEmpty(txtNrSenderNodes, "Nr. of sender nodes is required")) {
            return false;
        }
        if (textBoxIsEmpty(txtNrReceiverNodes, "Nr. of receiver nodes is required")) {
            return false;
        }
        if (textBoxIsEmpty(txtNrAttackNodes, "Nr. of attack nodes is required")) {
            return false;
        }
        if (textBoxIsEmpty(txtNrMessagesPerNode, "Nr of messages is required")) {
            return false;
        }
        if (textBoxIsEmpty(txtIntervalBetweenMessages, "Interval is required")) {
            return false;
        }
        if (textBoxIsEmpty(txtNrRetransmissions, "Nr. of retransmissions is required")) {
            return false;
        }


        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activateNow;
    private javax.swing.JComboBox cboAttacks;
    private javax.swing.JCheckBox chkAttackNodesPercent;
    private javax.swing.JCheckBox chkAttackNodesStable;
    private javax.swing.JCheckBox chkReceiverNodesPercent;
    private javax.swing.JCheckBox chkReceiverNodesSinknodes;
    private javax.swing.JCheckBox chkSenderNodesPercent;
    private javax.swing.JCheckBox chkSenderNodesStable;
    private javax.swing.JButton cmdLoadFromFile;
    private javax.swing.JButton cmdSaveToFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JFormattedTextField txtIntervalBetweenMessages;
    private javax.swing.JFormattedTextField txtNrAttackNodes;
    private javax.swing.JFormattedTextField txtNrMessagesPerNode;
    private javax.swing.JFormattedTextField txtNrReceiverNodes;
    private javax.swing.JFormattedTextField txtNrRetransmissions;
    private javax.swing.JFormattedTextField txtNrSenderNodes;
    private javax.swing.JTextArea txtTestDescription;
    private javax.swing.JTextField txtTestName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void beforeClose() {
    }

    private void setValueWhenEmpty(JFormattedTextField text, String defaultValue) {
        if (text.getText().length() == 0) {
            text.setText(defaultValue);
        }
    }

    public static void main(String[] args) {
        PlatformView.applyLookAndFeel();
        PlatformDialog.display(new TestBuilderPanel(), "Test Builder", PlatformFrame.NOACTIONS_MODE);
    }

    private boolean textBoxIsEmpty(JTextField textField, String msg) {
        boolean ret = textField.getText().trim().length() == 0;
        if (ret) {
            GUI_Utils.showWarningMessage(msg);
            GUI_Utils.setFocus(textField);
        } else {
            return false;
        }

        return true;
    }

    private void saveTestToFile() {
        if (isDataValid()) {
            try {
                inputParameters.setNumberOfSenderNodes(INT(txtNrSenderNodes));
                inputParameters.setNumberOfReceiverNodes(INT(txtNrReceiverNodes));
                inputParameters.setNumberOfAttackNodes(INT(txtNrAttackNodes));
                inputParameters.setPercentOfSenderNodes(chkSenderNodesPercent.isSelected());
                inputParameters.setPercentOfReceiverNodes(chkReceiverNodesPercent.isSelected());
                inputParameters.setPercentOfAttackNodes(chkAttackNodesPercent.isSelected());
                inputParameters.setOnlyConsiderToSenderStableNodes(chkSenderNodesStable.isSelected());
                inputParameters.setOnlyConsiderToAttackStableNodes(chkAttackNodesStable.isSelected());
                inputParameters.setOnlyConsiderToReceiverSinkNodes(chkReceiverNodesSinknodes.isSelected());
                inputParameters.setPercentOfSenderNodes(chkSenderNodesPercent.isSelected());
                //
                inputParameters.setNumberOfMessagesPerNode(INT(txtNrMessagesPerNode));
                inputParameters.setIntervalBetweenMessagesSent(INT(txtIntervalBetweenMessages));
                inputParameters.setNumberOfRetransmissions(INT(txtNrRetransmissions));
                if (cboAttacks.getSelectedItem().equals("None")) {
                    inputParameters.setAttackSelected(null);
                } else {
                    inputParameters.setAttackSelected(cboAttacks.getSelectedItem().toString());
                }
                test = new DefaultTest(inputParameters);
                test.setName(txtTestName.getText());
                test.setDescription(txtTestDescription.getText());

                String f = GUI_Utils.showSavePersistentObjectDialog("Save test");
                if (f != null) {
                    test.saveToXML(f);
                     GUI_Utils.showinfoMessage("Test " + txtTestName.getText() + " saved");
                }
            } catch (Exception ex) {
                GUI_Utils.showException(ex);
            }
        }
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    private void loadSimulationAttacks() {
        if (simulation != null) {
            Set registeredAttacks = simulation.getRoutingLayerController().getRegisteredAttacks();
            if (registeredAttacks.size() > 0) {
                cboAttacks.removeAll();
                cboAttacks.addItem("None");
                for (Object object : registeredAttacks) {
                    AttacksEntry a = (AttacksEntry) object;
                    cboAttacks.addItem(a.getLabel());
                }
            }
        }
    }

    public JCheckBox getActivateNow() {
        return activateNow;
    }

    private void loadFromFile() {
        try {
            String f = GUI_Utils.showOpenPersistentObjectDialog("Open test file");
            test = new DefaultTest();
            test.loadFromXML(f);
            txtTestName.setText(test.getName());
            txtTestDescription.setText(test.getDescription());
            inputParameters = test.getInputParameters();

            showInputParameters(inputParameters);


        } catch (Exception ex) {
            GUI_Utils.showException(ex);
        }

    }

    private void showInputParameters(TestInputParameters inputParameters) {
        txtNrSenderNodes.setText("" + inputParameters.getNumberOfSenderNodes());
        chkSenderNodesPercent.setSelected(inputParameters.isPercentOfSenderNodes());
        chkSenderNodesStable.setSelected(inputParameters.isOnlyConsiderToSenderStableNodes());

        txtNrReceiverNodes.setText("" + inputParameters.getNumberOfReceiverNodes());
        chkReceiverNodesPercent.setSelected(inputParameters.isPercentOfReceiverNodes());
        chkReceiverNodesSinknodes.setSelected(inputParameters.isOnlyConsiderToReceiverSinkNodes());

        txtNrAttackNodes.setText("" + inputParameters.getNumberOfAttackNodes());
        chkAttackNodesPercent.setSelected(inputParameters.isPercentOfAttackNodes());
        chkAttackNodesStable.setSelected(inputParameters.isOnlyConsiderToAttackStableNodes());

        txtNrMessagesPerNode.setText("" + inputParameters.getIntervalBetweenMessagesSent());
        txtNrRetransmissions.setText("" + inputParameters.getNumberOfRetransmissions());
        txtIntervalBetweenMessages.setText("" + inputParameters.getNumberOfMessagesPerNode());


        selectCboValue(cboAttacks, inputParameters.getAttackSelected());
    }

    private void selectCboValue(JComboBox cboAttacks, String attackSelected) {
        for (int i = 0; i < cboAttacks.getModel().getSize(); i++) {
            Object object = cboAttacks.getModel().getElementAt(i);
            if (object.equals(attackSelected)) {
                cboAttacks.getModel().setSelectedItem(object);
                return;
            }
        }
        GUI_Utils.showWarningMessage("Load attack is not available in current simulation");

    }
}
