/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.flooding;

import javax.swing.JOptionPane;
import org.wisenet.protocols.flooding.messages.FloodingMessage;
import org.wisenet.simulator.core.Application;

/**
 *
 * @author Pedro Marques da Silva
 */
public class HelloApplication extends Application {

    @Override
    protected void onMessageReceived(Object message) {
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
