/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClassParametersPanel.java
 *
 * Created on 13/Jul/2010, 12:30:14
 */
package org.mei.securesim.platform.ui.panels;

import java.awt.BorderLayout;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.application.Action;
import org.mei.securesim.core.layers.routing.RoutingLayerProperties;
import org.mei.securesim.platform.utils.gui.GUI_Utils;
import org.mei.securesim.utils.AnnotationUtils;
import org.mei.securesim.utils.annotation.Annotated;
import org.mei.securesim.utils.annotation.Parameter;

/**
 *
 * @author CIAdmin
 */
public class ClassParametersPanel extends javax.swing.JPanel {

    Annotated propertiesClass;
    Object[][] properties = {{"Teste", "0"}};
    private Vector<Field> parametersFields;

    /** Creates new form ClassParametersPanel */
    public ClassParametersPanel(Annotated propertiesClass) {
        initComponents();
        this.propertiesClass = propertiesClass;
        loadTableProperties(this.propertiesClass);

    }

    public Annotated getPropertiesClass() {
        return propertiesClass;
    }

    public void setPropertiesClass(Annotated propertiesClass) {
        this.propertiesClass = propertiesClass;
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
        buttonsPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(ClassParametersPanel.class);
        jLabel1.setBackground(resourceMap.getColor("jLabel1.background")); // NOI18N
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel1.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 40));
        add(jLabel1, java.awt.BorderLayout.PAGE_START);

        buttonsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonsPanel.setName("buttonsPanel"); // NOI18N
        buttonsPanel.setPreferredSize(new java.awt.Dimension(400, 50));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getActionMap(ClassParametersPanel.class, this);
        jButton1.setAction(actionMap.get("ApplyProperties")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(327, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        add(buttonsPanel, java.awt.BorderLayout.PAGE_END);

        contentPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        contentPanel.setName("contentPanel"); // NOI18N
        contentPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        contentPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(contentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private void loadTableProperties(Annotated instance) {
        parseClass(instance);
        DefaultTableModel model = createTableModel(properties);
        jTable1.setModel(model);
    }

    DefaultTableModel createTableModel(Object[][] properties) {
        return new javax.swing.table.DefaultTableModel(
                properties,
                new String[]{
                    "Property", "Value"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return true;
            }
        };
    }

    private void parseClass(Annotated props) {
        ArrayList arrayProperties = new ArrayList();
        try {
            Object instanceValue = null;
            Object defaultValue = null;
            parametersFields = AnnotationUtils.readParametersFields(props, Parameter.class);
            if (parametersFields.size() > 0) {
                for (Field f : parametersFields) {
                    f.setAccessible(true);
                    Parameter p = f.getAnnotation(Parameter.class);
                    instanceValue = getInstancedValue(f, props);
                    defaultValue = getAnnotationValue(Class.forName(p.className()), p.value());
                    if (!instanceValue.equals(defaultValue)) {
                        arrayProperties.add(new Object[]{p.label(), instanceValue});
                    } else {
                        arrayProperties.add(new Object[]{p.label(), defaultValue});
                    }
                }
            }
            properties = new Object[arrayProperties.size()][];
            for (int i = 0; i < arrayProperties.size(); i++) {
                properties[i] = (Object[]) arrayProperties.get(i);
            }
        } catch (Exception ex) {
            GUI_Utils.showException(ex);

        }

    }

    private static Object getInstancedValue(Field f, Object instance) throws Exception {
        return f.get(instance);
    }

    private static Object getAnnotationValue(Class className, Object value) throws Exception {
        if (className.equals(Double.class)) {
            return Double.parseDouble(value.toString());
        } else if (className.equals(Integer.class)) {
            return Integer.parseInt(value.toString());
        } else if (className.equals(Integer.class)) {
            return Short.parseShort(value.toString());
        } else if (className.equals(Float.class)) {
            return Float.parseFloat(value.toString());
        } else if (className.equals(Long.class)) {
            return Long.parseLong(value.toString());
        } else if (className.equals(String.class)) {
            return String.valueOf(value);
        } else {
            Constructor c;
            c = className.getConstructor(new Class[]{String.class});
            return c.newInstance(value);
        }
    }

    @Action
    public void ApplyProperties() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                Field field = parametersFields.get(i);
                Parameter p = field.getAnnotation(Parameter.class);
                Object v = getAnnotationValue(Class.forName(p.className()), model.getValueAt(i, 1));
                field.setAccessible(true);
                field.set(propertiesClass, v);
            } catch (Exception ex) {
                GUI_Utils.showException(ex);
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Properties");
        f.setLayout(new BorderLayout());
        RoutingLayerProperties props = new RoutingLayerProperties();
        f.add(new ClassParametersPanel(props), BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }
}
