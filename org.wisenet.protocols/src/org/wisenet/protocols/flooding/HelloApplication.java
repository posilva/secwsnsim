/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.flooding;

import javax.swing.JOptionPane;
import org.wisenet.protocols.flooding.messages.FloodingMessage;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.utilities.Utilities;

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
        short parameter = askForParameter();
        sendHelloTo((short) parameter);
    }
//    public void run() {
//        byte parameter = askForParameter();
//        switch (parameter) {
//            case 0:
//                sendHelloTo((short) parameter);
//                break;
//            default:
//                JOptionPane.showMessageDialog(null, "NONE");
//                break;
//        }
//    }

    /**
     * 
     * @return
     */
    private short askForParameter() {
        try {
            String result = JOptionPane.showInputDialog("Choose destination node ID");
            return Short.valueOf(result);

        } catch (Exception e) {
            Utilities.handleException(e);
        }
        return (short) 0;
    }

    void sendHelloTo(short id) {
        FloodingMessage m = new FloodingMessage("HELLO".getBytes());
        m.setType((byte) 1);
        m.setSourceId(getNode().getId());
        m.setDestinationId(id);
        m.setMessageData("HELLO");
        sendMessage(m);
    }
}
