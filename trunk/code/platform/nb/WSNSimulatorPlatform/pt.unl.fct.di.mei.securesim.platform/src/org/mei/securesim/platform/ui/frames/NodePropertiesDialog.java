/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.platform.ui.frames;

import com.jidesoft.dialog.BannerPanel;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.ButtonResources;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.plaf.UIDefaultsLookup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util. Vector;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.gui.GraphicNode;
import org.mei.securesim.platform.utils.gui.GUI_Utils;

/**
 *
 * @author posilva
 */
public class NodePropertiesDialog extends StandardDialog {

      Vector<GraphicNode> selectedNodes;


    public NodePropertiesDialog(Dialog dialog, String string, boolean bln, GraphicsConfiguration gc) throws HeadlessException {
        super(dialog, string, bln, gc);
        init();
    }

    public NodePropertiesDialog(Dialog dialog, String string, boolean bln) throws HeadlessException {
        super(dialog, string, bln);
        init();
    }

    public NodePropertiesDialog(Dialog dialog, String string) throws HeadlessException {
        super(dialog, string);
        init();
    }

    public NodePropertiesDialog(Dialog dialog, boolean bln) throws HeadlessException {
        super(dialog, bln);
        init();
    }

    public NodePropertiesDialog(Frame frame, String string, boolean bln) throws HeadlessException {
        super(frame, string, bln);
        init();
    }

    public NodePropertiesDialog(Frame frame, String string) throws HeadlessException {
        super(frame, string);
        init();
    }

    public NodePropertiesDialog(Frame frame, boolean bln) throws HeadlessException {
        super(frame, bln);
        init();
    }

    public NodePropertiesDialog(Frame frame) throws HeadlessException {
        super(frame);
        init();
    }

    public NodePropertiesDialog() throws HeadlessException {
        init();
    }


    void init() {
        setSize(600, 400);
        
        pack();

    }

    @Override
    public JComponent createBannerPanel() {
        BannerPanel headerPanel1 = new BannerPanel("Node properties", "In this window you can set de node properties", new ImageIcon());
        headerPanel1.setFont(new Font("Tahoma", Font.PLAIN, 9));
        headerPanel1.setBackground(Color.WHITE);
        headerPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        return headerPanel1;
    }

    @Override
    public JComponent createContentPanel() {
        return new NodePropertiesPanel();
    }

    @Override
    public ButtonPanel createButtonPanel() {
        ButtonPanel buttonPanel = new ButtonPanel();
        JButton okButton = new JButton();
        JButton cancelButton = new JButton();
        JButton helpButton = new JButton();
        okButton.setName(OK);
        cancelButton.setName(CANCEL);
        helpButton.setName(HELP);
        buttonPanel.addButton(okButton, ButtonPanel.AFFIRMATIVE_BUTTON);
        buttonPanel.addButton(cancelButton, ButtonPanel.CANCEL_BUTTON);
        buttonPanel.addButton(helpButton, ButtonPanel.HELP_BUTTON);

        okButton.setAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {

            public void actionPerformed(ActionEvent e) {
                setDialogResult(RESULT_AFFIRMED);
                setVisible(false);
                dispose();
            }
        });
        cancelButton.setAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText")) {

            public void actionPerformed(ActionEvent e) {
                setDialogResult(RESULT_CANCELLED);
                setVisible(false);
                dispose();
            }
        });
        final ResourceBundle resourceBundle = ButtonResources.getResourceBundle(Locale.getDefault());
        helpButton.setAction(new AbstractAction(resourceBundle.getString("Button.help")) {

            public void actionPerformed(ActionEvent e) {
                // do something
            }
        });
        helpButton.setMnemonic(resourceBundle.getString("Button.help.mnemonic").charAt(0));

        setDefaultCancelAction(cancelButton.getAction());
        setDefaultAction(okButton.getAction());
        getRootPane().setDefaultButton(okButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return buttonPanel;
    }

    public static void main(String[] a) {
        NodePropertiesDialog p = new NodePropertiesDialog();
         Vector<GraphicNode>  r=new  Vector<GraphicNode>();
        p.showNodesProperties(r);
    }
    public void showNodesProperties( Vector<GraphicNode> nodes){
        if (nodes==null ) return;
        selectedNodes=nodes;
        if(selectedNodes.size()>0 ){
            GUI_Utils.centerOnScreen(this);
            setModal(true);

            setNodeProperties(selectedNodes);

            setVisible(true);
        }
    }

    private void setNodeProperties(Vector<GraphicNode> selNodes) {
        if (selNodes.size()==1){
            /**
             * set current properties for node and show
             */
        }else{
            /**
             * set properties for all nodes
             */
        }
    }



}
