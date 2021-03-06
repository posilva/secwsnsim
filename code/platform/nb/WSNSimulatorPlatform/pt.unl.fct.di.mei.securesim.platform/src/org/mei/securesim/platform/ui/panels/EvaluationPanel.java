/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.platform.ui.panels;

import javax.swing.JOptionPane;
import org.mei.securesim.components.evaluation.EvaluationTestSettings;
import org.jdesktop.application.Action;
import org.mei.securesim.components.evaluation.EvaluationDeployModeEnum;
import org.mei.securesim.components.evaluation.EvaluationTestTypeEnum;
import org.mei.securesim.components.evaluation.IEvaluationDeploy;
import org.mei.securesim.platform.evaluation.PlatformEvaluationFactory;
import org.mei.securesim.platform.evaluation.PlatformEvaluationTest;
import org.mei.securesim.platform.ui.frames.TestFrame;
import org.mei.securesim.platform.utils.gui.GUI_Utils;

/**
 *
 * @author CIAdmin
 */
public class EvaluationPanel extends javax.swing.JPanel {

    private static EvaluationPanel instance;

    public static EvaluationPanel getInstance() {
        if (instance == null) {
            instance = new EvaluationPanel();
        }
        return instance;
    }
    private boolean evaluationDeployed = false;
    private EvaluationTestSettings settings;

    public boolean isEvaluationDeployed() {
        return evaluationDeployed;
    }

    public void setEvaluationDeployed(boolean evaluationDeployed) {
        this.evaluationDeployed = evaluationDeployed;
    }

    /** Creates new form EvaluationPanel */
    public EvaluationPanel() {
        if (instance == null) {
            initComponents();
        } else {
            throw new IllegalStateException("Just one instance allowed");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        titleArea = new javax.swing.JLabel();
        contentArea = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtFieldHeight = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDeployParameter = new javax.swing.JFormattedTextField();
        txtFieldWidth = new javax.swing.JFormattedTextField();
        lblDeplayParam = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        optDeployRandom = new javax.swing.JRadioButton();
        optDeployGrid = new javax.swing.JRadioButton();
        btnDeployEvaluationNetwork = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        chkOnlyStable = new javax.swing.JCheckBox();
        txtReceiverNodesParam = new javax.swing.JFormattedTextField();
        chkOnlySink = new javax.swing.JCheckBox();
        txtSenderNodesParam = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        optReliability = new javax.swing.JRadioButton();
        optCoverage = new javax.swing.JRadioButton();
        optLatency = new javax.swing.JRadioButton();
        optEnergy = new javax.swing.JRadioButton();
        txtTestNrMessages = new javax.swing.JFormattedTextField();
        txtTestTimeInterval = new javax.swing.JFormattedTextField();
        txtTestNrRetransmissions = new javax.swing.JFormattedTextField();
        txtTestStartDelay = new javax.swing.JFormattedTextField();
        cmdEvaluate = new javax.swing.JButton();
        cmdTestApply = new javax.swing.JButton();
        cmdEvaluateGetResults = new javax.swing.JButton();
        chkSenderNodesParamPercent = new javax.swing.JCheckBox();
        chkReceiverNodesParamPercent = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtAttackParam = new javax.swing.JFormattedTextField();
        cmdRunAttack = new javax.swing.JButton();
        buttonArea = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(466, 592));
        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(EvaluationPanel.class);
        titleArea.setBackground(resourceMap.getColor("titleArea.background")); // NOI18N
        titleArea.setFont(resourceMap.getFont("titleArea.font")); // NOI18N
        titleArea.setText(resourceMap.getString("titleArea.text")); // NOI18N
        titleArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        titleArea.setMaximumSize(new java.awt.Dimension(200, 30));
        titleArea.setMinimumSize(new java.awt.Dimension(200, 30));
        titleArea.setName("titleArea"); // NOI18N
        titleArea.setOpaque(true);
        titleArea.setPreferredSize(new java.awt.Dimension(200, 40));
        add(titleArea, java.awt.BorderLayout.PAGE_START);

        contentArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        contentArea.setMaximumSize(new java.awt.Dimension(466, 512));
        contentArea.setMinimumSize(new java.awt.Dimension(466, 512));
        contentArea.setName("contentArea"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        txtFieldHeight.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFieldHeight.setText(resourceMap.getString("txtFieldHeight.text")); // NOI18N
        txtFieldHeight.setName("txtFieldHeight"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        txtDeployParameter.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDeployParameter.setText(resourceMap.getString("txtDeployParameter.text")); // NOI18N
        txtDeployParameter.setName("txtDeployParameter"); // NOI18N

        txtFieldWidth.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFieldWidth.setText(resourceMap.getString("txtFieldWidth.text")); // NOI18N
        txtFieldWidth.setName("txtFieldWidth"); // NOI18N

        lblDeplayParam.setText(resourceMap.getString("lblDeplayParam.text")); // NOI18N
        lblDeplayParam.setName("lblDeplayParam"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        buttonGroup1.add(optDeployRandom);
        optDeployRandom.setSelected(true);
        optDeployRandom.setText(resourceMap.getString("optDeployRandom.text")); // NOI18N
        optDeployRandom.setName("optDeployRandom"); // NOI18N
        optDeployRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optDeployRandomActionPerformed(evt);
            }
        });

        buttonGroup1.add(optDeployGrid);
        optDeployGrid.setText(resourceMap.getString("optDeployGrid.text")); // NOI18N
        optDeployGrid.setName("optDeployGrid"); // NOI18N
        optDeployGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optDeployGridActionPerformed(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getActionMap(EvaluationPanel.class, this);
        btnDeployEvaluationNetwork.setAction(actionMap.get("DeployEvaluationNetworkAction")); // NOI18N
        btnDeployEvaluationNetwork.setText(resourceMap.getString("btnDeployEvaluationNetwork.text")); // NOI18N
        btnDeployEvaluationNetwork.setName("btnDeployEvaluationNetwork"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDeplayParam, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(optDeployRandom, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(optDeployGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                            .addComponent(txtDeployParameter, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtFieldHeight)
                            .addComponent(txtFieldWidth, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(btnDeployEvaluationNetwork)
                        .addGap(19, 19, 19))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(optDeployGrid)
                    .addComponent(optDeployRandom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDeployParameter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDeplayParam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFieldWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtFieldHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeployEvaluationNetwork))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jPanel3.setName("jPanel3"); // NOI18N

        chkOnlyStable.setText(resourceMap.getString("chkOnlyStable.text")); // NOI18N
        chkOnlyStable.setName("chkOnlyStable"); // NOI18N

        txtReceiverNodesParam.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtReceiverNodesParam.setName("txtReceiverNodesParam"); // NOI18N

        chkOnlySink.setText(resourceMap.getString("chkOnlySink.text")); // NOI18N
        chkOnlySink.setName("chkOnlySink"); // NOI18N

        txtSenderNodesParam.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSenderNodesParam.setName("txtSenderNodesParam"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        buttonGroup2.add(optReliability);
        optReliability.setSelected(true);
        optReliability.setText(resourceMap.getString("optReliability.text")); // NOI18N
        optReliability.setName("optReliability"); // NOI18N

        buttonGroup2.add(optCoverage);
        optCoverage.setText(resourceMap.getString("optCoverage.text")); // NOI18N
        optCoverage.setName("optCoverage"); // NOI18N

        buttonGroup2.add(optLatency);
        optLatency.setText(resourceMap.getString("optLatency.text")); // NOI18N
        optLatency.setName("optLatency"); // NOI18N

        buttonGroup2.add(optEnergy);
        optEnergy.setText(resourceMap.getString("optEnergy.text")); // NOI18N
        optEnergy.setName("optEnergy"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(optCoverage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(optReliability, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(optLatency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(optEnergy, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optReliability)
                    .addComponent(optLatency))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optCoverage)
                    .addComponent(optEnergy))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        txtTestNrMessages.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTestNrMessages.setText(resourceMap.getString("txtTestNrMessages.text")); // NOI18N
        txtTestNrMessages.setToolTipText(resourceMap.getString("txtTestNrMessages.toolTipText")); // NOI18N
        txtTestNrMessages.setName("txtTestNrMessages"); // NOI18N

        txtTestTimeInterval.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTestTimeInterval.setText(resourceMap.getString("txtTestTimeInterval.text")); // NOI18N
        txtTestTimeInterval.setToolTipText(resourceMap.getString("txtTestTimeInterval.toolTipText")); // NOI18N
        txtTestTimeInterval.setName("txtTestTimeInterval"); // NOI18N

        txtTestNrRetransmissions.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTestNrRetransmissions.setText(resourceMap.getString("txtTestNrRetransmissions.text")); // NOI18N
        txtTestNrRetransmissions.setToolTipText(resourceMap.getString("txtTestNrRetransmissions.toolTipText")); // NOI18N
        txtTestNrRetransmissions.setName("txtTestNrRetransmissions"); // NOI18N

        txtTestStartDelay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTestStartDelay.setText(resourceMap.getString("txtTestStartDelay.text")); // NOI18N
        txtTestStartDelay.setToolTipText(resourceMap.getString("txtTestStartDelay.toolTipText")); // NOI18N
        txtTestStartDelay.setName("txtTestStartDelay"); // NOI18N

        cmdEvaluate.setAction(actionMap.get("EvaluateAction")); // NOI18N
        cmdEvaluate.setText(resourceMap.getString("cmdEvaluate.text")); // NOI18N
        cmdEvaluate.setName("cmdEvaluate"); // NOI18N

        cmdTestApply.setAction(actionMap.get("ApplyEvaluationTestSettings")); // NOI18N
        cmdTestApply.setText(resourceMap.getString("cmdTestApply.text")); // NOI18N
        cmdTestApply.setName("cmdTestApply"); // NOI18N

        cmdEvaluateGetResults.setText(resourceMap.getString("cmdEvaluateGetResults.text")); // NOI18N
        cmdEvaluateGetResults.setName("cmdEvaluateGetResults"); // NOI18N

        chkSenderNodesParamPercent.setText(resourceMap.getString("chkSenderNodesParamPercent.text")); // NOI18N
        chkSenderNodesParamPercent.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkSenderNodesParamPercent.setName("chkSenderNodesParamPercent"); // NOI18N

        chkReceiverNodesParamPercent.setText(resourceMap.getString("chkReceiverNodesParamPercent.text")); // NOI18N
        chkReceiverNodesParamPercent.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkReceiverNodesParamPercent.setName("chkReceiverNodesParamPercent"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmdEvaluate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdEvaluateGetResults, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTestNrMessages, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTestNrRetransmissions, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTestTimeInterval, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTestStartDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(cmdTestApply))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkSenderNodesParamPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkReceiverNodesParamPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSenderNodesParam, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReceiverNodesParam, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkOnlySink, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(chkOnlyStable, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSenderNodesParam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkOnlyStable)
                    .addComponent(chkSenderNodesParamPercent))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(chkOnlySink)
                    .addComponent(chkReceiverNodesParamPercent)
                    .addComponent(txtReceiverNodesParam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTestNrMessages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTestNrRetransmissions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTestTimeInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTestStartDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdTestApply))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cmdEvaluate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdEvaluateGetResults))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel5.border.title"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        txtAttackParam.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAttackParam.setName("txtAttackParam"); // NOI18N

        cmdRunAttack.setText(resourceMap.getString("cmdRunAttack.text")); // NOI18N
        cmdRunAttack.setName("cmdRunAttack"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(txtAttackParam, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(cmdRunAttack)
                .addGap(21, 21, 21))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdRunAttack)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtAttackParam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout contentAreaLayout = new javax.swing.GroupLayout(contentArea);
        contentArea.setLayout(contentAreaLayout);
        contentAreaLayout.setHorizontalGroup(
            contentAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentAreaLayout.setVerticalGroup(
            contentAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(255, 255, 255))
        );

        add(contentArea, java.awt.BorderLayout.CENTER);

        buttonArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonArea.setMaximumSize(new java.awt.Dimension(32767, 40));
        buttonArea.setMinimumSize(new java.awt.Dimension(14, 40));
        buttonArea.setName("buttonArea"); // NOI18N
        buttonArea.setPreferredSize(new java.awt.Dimension(100, 40));
        buttonArea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        buttonArea.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        buttonArea.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, -1));

        add(buttonArea, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void optDeployGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optDeployGridActionPerformed
        applyDeployParamLabel();

    }//GEN-LAST:event_optDeployGridActionPerformed

    private void optDeployRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optDeployRandomActionPerformed
        applyDeployParamLabel();
    }//GEN-LAST:event_optDeployRandomActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeployEvaluationNetwork;
    private javax.swing.JPanel buttonArea;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox chkOnlySink;
    private javax.swing.JCheckBox chkOnlyStable;
    private javax.swing.JCheckBox chkReceiverNodesParamPercent;
    private javax.swing.JCheckBox chkSenderNodesParamPercent;
    private javax.swing.JButton cmdEvaluate;
    private javax.swing.JButton cmdEvaluateGetResults;
    private javax.swing.JButton cmdRunAttack;
    private javax.swing.JButton cmdTestApply;
    private javax.swing.JPanel contentArea;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblDeplayParam;
    private javax.swing.JRadioButton optCoverage;
    private javax.swing.JRadioButton optDeployGrid;
    private javax.swing.JRadioButton optDeployRandom;
    private javax.swing.JRadioButton optEnergy;
    private javax.swing.JRadioButton optLatency;
    private javax.swing.JRadioButton optReliability;
    private javax.swing.JLabel titleArea;
    private javax.swing.JFormattedTextField txtAttackParam;
    private javax.swing.JFormattedTextField txtDeployParameter;
    private javax.swing.JFormattedTextField txtFieldHeight;
    private javax.swing.JFormattedTextField txtFieldWidth;
    private javax.swing.JFormattedTextField txtReceiverNodesParam;
    private javax.swing.JFormattedTextField txtSenderNodesParam;
    private javax.swing.JFormattedTextField txtTestNrMessages;
    private javax.swing.JFormattedTextField txtTestNrRetransmissions;
    private javax.swing.JFormattedTextField txtTestStartDelay;
    private javax.swing.JFormattedTextField txtTestTimeInterval;
    // End of variables declaration//GEN-END:variables

    @Action
    public void DeployEvaluationNetworkAction() {

        if (validDeploySettings()) {
            EvaluationDeployModeEnum mode = optDeployGrid.isSelected() ? EvaluationDeployModeEnum.GRID : EvaluationDeployModeEnum.RANDOM;
            int param = Integer.valueOf(txtDeployParameter.getText());
            int w = Integer.valueOf(txtFieldWidth.getText());
            int h = Integer.valueOf(txtFieldHeight.getText());
            IEvaluationDeploy evaluationDeploy = PlatformEvaluationFactory.getInstance().create(mode, param, w, h);
            evaluationDeploy.deploy();
            setEvaluationDeployed(true);
        }

    }

    private boolean validDeploySettings() {
        try {
            int param = Integer.valueOf(txtDeployParameter.getText());
            int w = Integer.valueOf(txtFieldWidth.getText());
            int h = Integer.valueOf(txtFieldHeight.getText());
            if (w > 0 && h > 0) {
                if (param > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            GUI_Utils.showException(e);
        }
        return false;
    }

    private void applyDeployParamLabel() {
        if (optDeployGrid.isSelected()) {
            lblDeplayParam.setText("Distance between nodes:");
        } else {
            lblDeplayParam.setText("Number of nodes:");
        }
    }

    public static void main(String[] args) {
        TestFrame.getInstance("Evaluation Panel").addContentPanel(EvaluationPanel.getInstance());
        TestFrame.getInstance().test();

    }

    @Action
    public void ApplyEvaluationTestSettings() {

        settings = new EvaluationTestSettings();
        settings.setDelay(Integer.valueOf(txtTestStartDelay.getText()));
        settings.setInterval(Integer.valueOf(txtTestTimeInterval.getText()));
        settings.setNrMessages(Integer.valueOf(txtTestNrMessages.getText()));
        settings.setNrRetransmissions(Integer.valueOf(txtTestNrRetransmissions.getText()));

        settings.setNrSenderNodes(Integer.valueOf(txtSenderNodesParam.getText()));
        settings.setNrReceiverNodes(Integer.valueOf(txtReceiverNodesParam.getText()));
        settings.setOnlySinkNodes(chkOnlyStable.isSelected());
        settings.setOnlySinkNodes(chkOnlySink.isSelected());
        settings.setSenderNodesPercent(chkSenderNodesParamPercent.isSelected());
        settings.setReceiverNodesPercent(chkReceiverNodesParamPercent.isSelected());



    }

    @Action
    public void EvaluateAction() {
        if (settings != null) {
            if (optReliability.isSelected()) {
                PlatformEvaluationTest evaluationTest = new PlatformEvaluationTest(EvaluationTestTypeEnum.RELIABILITY, settings);
                evaluationTest.test();
                GUI_Utils.showMessage((String) evaluationTest.getResult().toString(), JOptionPane.INFORMATION_MESSAGE);
            } else if (optCoverage.isSelected()) {
            } else if (optLatency.isSelected()) {
            } else if (optEnergy.isSelected()) {
            }
        }
    }
}
