
/*
 * EnergyModelPanel.java
 *
 * Created on Mar 30, 2010, 12:22:04 AM
 */
package org.mei.securesim.platform.instruments.energy.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.mei.securesim.utils.annotation.EnergyModelParameter;
import org.mei.securesim.core.energy.EnergyModel;
import org.mei.securesim.utils.AnnotationUtils;

/**
 *
 * @author posilva
 */
public class EnergyModelPanel extends javax.swing.JPanel {

    protected EnergyModel energyModel = new EnergyModel();

    /** Creates new form EnergyModelPanel */
    public EnergyModelPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        energyModeltable = new javax.swing.JTable();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        energyModeltable.setModel(createTableModel());
        energyModeltable.setName("energyModeltable"); // NOI18N
        jScrollPane1.setViewportView(energyModeltable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable energyModeltable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public EnergyModel createEnergyModelInstance() {
        for (int i = 0; i < energyModeltable.getModel().getRowCount(); i++) {
            try {
                Double v = (Double) energyModeltable.getModel().getValueAt(i, 1);
                Field field = (Field) energyModeltable.getModel().getValueAt(i, 2);

                field.setAccessible(true);
                field.set(energyModel, v);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(EnergyModelPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(EnergyModelPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return energyModel;
    }

// End of variables declaration
    class EnergyTableModel extends DefaultTableModel {

        List<Field> parametersFields = new ArrayList<Field>();
        private Class[] types = new Class[]{String.class, Double.class};

        public EnergyTableModel() {
            columnIdentifiers = new Vector();
            columnIdentifiers.add("Parâmetro");
            columnIdentifiers.add("Valor");
            parametersFields = AnnotationUtils.readEnergyModelParametersFields(energyModel);
            dataVector = new Vector();
            for (Field f : parametersFields) {
                Vector row = new Vector();
                EnergyModelParameter p = f.getAnnotation(EnergyModelParameter.class);
                row.add(p.label());
                row.add(p.value());
                row.add(f);
                dataVector.add(row);
            }
        }
        boolean[] canEdit = new boolean[]{
            false, true
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    }

    private DefaultTableModel createTableModel() {
        return new EnergyTableModel();
    }
}


