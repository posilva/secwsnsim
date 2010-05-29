package org.mei.securesim.components.instruments;

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
}
