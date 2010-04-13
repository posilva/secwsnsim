/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NodePropertiesPanel.java
 *
 * Created on Apr 12, 2010, 11:25:58 PM
 */
package org.mei.securesim.platform.ui.frames;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.mei.securesim.core.energy.EnergyModel;
import org.mei.securesim.platform.instruments.energy.ui.resources.EnergyModelDialog;

/**
 *
 * @author posilva
 */
public class NodePropertiesPanel extends javax.swing.JPanel {

    RowEditorModel rowEditorModel;
    private JTable jTable1;

    /** Creates new form NodePropertiesPanel */
    public NodePropertiesPanel() {
        initComponents();
        setSize(600, 400);
        jTable1 = new PropertiesTable();
        jScrollPane1.setViewportView(jTable1);
        setup();

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

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    /**
     * 
     */
    class NodePropertiesTableModel extends DefaultTableModel {

        public NodePropertiesTableModel() {
            super(col_names, prop_names.length);
        }


        @Override
        public Object getValueAt(int row, int col) {

            if (col == 0) {
                return prop_names[row];
            }
            return super.getValueAt(row, col);
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            if (col == 0) {
                return false;
            }
            return true;
        }
    }

    public class RowEditorModel {

        private Hashtable data;

        public RowEditorModel() {
            data = new Hashtable();
        }

        public void addEditorForRow(int row, TableCellEditor e) {
            data.put(new Integer(row), e);
        }

        public void removeEditorForRow(int row) {
            data.remove(new Integer(row));
        }

        public TableCellEditor getEditor(int row) {
            return (TableCellEditor) data.get(new Integer(row));
        }
    }

    public RowEditorModel getRowEditorModel() {
        return rowEditorModel;
    }

    public void setRowEditorModel(RowEditorModel rowEditorModel) {
        this.rowEditorModel = rowEditorModel;
    }

    public TableCellEditor getCellEditor(int row, int col) {
        TableCellEditor tmpEditor = null;
        if (getRowEditorModel() != null) {
            tmpEditor = getRowEditorModel().getEditor(row);
        }
        if (tmpEditor != null) {
            return tmpEditor;
        }
        return jTable1.getCellEditor(row, col);
    }

    class PropertiesTable extends JTable {

        protected RowEditorModel rm = null;

        public PropertiesTable(Object[][] rowData, Object[] columnNames) {
            super(rowData, columnNames);
        }

        public PropertiesTable(Vector rowData, Vector columnNames) {
            super(rowData, columnNames);
        }

        public PropertiesTable(int numRows, int numColumns) {
            super(numRows, numColumns);
        }

        public PropertiesTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
            super(dm, cm, sm);
        }

        public PropertiesTable(TableModel dm, TableColumnModel cm) {
            super(dm, cm);
        }

        public PropertiesTable(TableModel dm) {
            super(dm);
        }

        public PropertiesTable() {
        }

        // new constructor
        public PropertiesTable(TableModel tm, RowEditorModel rm) {
            super(tm, null, null);
            this.rm = rm;
        }

        public void setRowEditorModel(RowEditorModel rm) {
            this.rm = rm;
        }

        public RowEditorModel getRowEditorModel() {
            return rm;
        }

        @Override
        public TableCellEditor getCellEditor(int row, int col) {
            TableCellEditor tmpEditor = null;
            if (rm != null) {
                tmpEditor = rm.getEditor(row);
            }
            if (tmpEditor != null) {
                return tmpEditor;
            }
            return super.getCellEditor(row, col);
        }
    }

    TableCellEditor getMACLayers() {
        JComboBox mac = new JComboBox(MAC_values);
        return  new DefaultCellEditor(mac);
    }

    TableCellEditor getApplications() {
        JComboBox app = new JComboBox(APP_values);
        return  new DefaultCellEditor(app);
    }

    TableCellEditor getRoutingLayers() {
        JComboBox route = new JComboBox(ROUTING_values);
        return  new DefaultCellEditor(route);
    }

    TableCellEditor getSinkNode() {
        JComboBox bool= new JComboBox(new String[]{"True","False"});
        return  new DefaultCellEditor(bool);
    }

//    TableCellEditor getEnergyModel() {
//        return new DefaultCellEditor;
//    }

    TableCellEditor getMaxStrength() {
        return null;
    }

    void setup() {

        DefaultTableModel model = new NodePropertiesTableModel();

        jTable1.setModel(model);
        // tell the JTableX which RowEditorModel we are using

        rowEditorModel = new RowEditorModel();

        rowEditorModel.addEditorForRow(0, getSinkNode());
        rowEditorModel.addEditorForRow(1, new ButtonColumn());
        rowEditorModel.addEditorForRow(3, getApplications());
        rowEditorModel.addEditorForRow(4, getRoutingLayers());
        ((PropertiesTable)jTable1).setRowEditorModel(rowEditorModel);
    }
        String[] col_names = {"Propertie", "Value"};
        String[] MAC_values = {"Mica2MACLayer", "SecureMica2MACLayer"};
        String[] ROUTING_values = {"Ping Pong Routing Layer", "AODV Routing Layer"};
        String[] APP_values = {"Ping Pong", "AODV"};
        public static String[] prop_names = {"Sink Node", "Energy Model", "Max Strenght",
            "Application", "RoutingLayer"};


class ButtonColumn extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener
    {
        public static final String CONFIG_BUTTON = "Click to Configure";
        JButton renderButton;
        JButton editButton;
        String text=CONFIG_BUTTON;
        private EnergyModel energyModel;
        private boolean energyModelConfig;

        public ButtonColumn()
        {
            super();
            
            renderButton = new JButton(CONFIG_BUTTON);

            editButton = new JButton(CONFIG_BUTTON);
            editButton.setFocusPainted( true);
            editButton.addActionListener( this );
        }

        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            if (hasFocus)
            {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            else if (isSelected)
            {
                renderButton.setForeground(table.getSelectionForeground());
                 renderButton.setBackground(table.getSelectionBackground());
            }
            else
            {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
                renderButton.setText(CONFIG_BUTTON);

//            renderButton.setText( (value == null) ? "" : value.toString() );
            return renderButton;
        }

        public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
        {
            text = CONFIG_BUTTON;//(value == null) ? "" : value.toString();
            editButton.setText( text );
            return editButton;
        }

        public Object getCellEditorValue()
        {
            return text;
        }

        public void actionPerformed(ActionEvent e)
        {
            fireEditingStopped();

            EnergyModelDialog emd = new EnergyModelDialog(null, true);
        emd.setVisible(true);
        if (emd.isOk()) //           sw.getSimulationFactory();
        {
            energyModel = emd.getEnergyModel();
            energyModelConfig = true;
        }
        emd.dispose();
        }
    }
}
