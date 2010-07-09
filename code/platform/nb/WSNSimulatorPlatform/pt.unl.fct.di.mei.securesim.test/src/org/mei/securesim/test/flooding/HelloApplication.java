/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.flooding;

import javax.swing.JOptionPane;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.test.flooding.messages.FloodingMessage;

/**
 *
 * @author Pedro Marques da Silva
 */
public class HelloApplication extends Application {

    @Override
    public void receiveMessage(Object message) {
        FloodingMessage m = (FloodingMessage) message;
        //JOptionPane.showMessageDialog(null, "Node: " + getNode().getId() +"\n" + "Data: "+ m.getMessageData() + "\nFrom: " + m.getSource());
    }

    @Override
    public void run() {
        byte parameter = askForParameter();
        switch (parameter) {
            case 0:
                sendHelloTo((short)2);
                break;
            default:
                JOptionPane.showMessageDialog(null, "NONE");
                break;
        }
    }
    /**
     * 
     * @return
     */
    private byte askForParameter() {
        String result = JOptionPane.showInputDialog("Escolha as opções:\n 0 - Send HELLO Message");
        return Byte.valueOf(result);
    }

    void sendHelloTo( short id ){
        FloodingMessage m = new FloodingMessage(null);
        m.setType((byte)1);
        m.setSource(getNode().getId());
        m.setDestin(id);
        m.setMessageData("HELLO");
        sendMessage(m);
    }
}
