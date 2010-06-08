package org.mei.securesim.components.instruments;

import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class ReliabilityController {

    protected boolean enable;
    static ReliabilityController instance = null;

    public static ReliabilityController getInstance() {
        if (instance == null) {
            instance = new ReliabilityController();
        }
        return instance;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void notifyMessageSent(Object message, Node node) {
    
    }

    public void notifyMessageReception(Object message, Node node) {
    
    }
}
