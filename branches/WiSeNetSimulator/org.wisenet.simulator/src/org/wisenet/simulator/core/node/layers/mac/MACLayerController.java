/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.mac;

import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.Parameterizable;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;
import org.wisenet.simulator.common.Persistent;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class MACLayerController extends PersistantObject implements Parameterizable, Persistent {

    /**
     *
     */
    protected long totalMessagesSent = 0;
    /**
     *
     */
    protected long totalMessagesNotSent = 0;
    /**
     *
     */
    protected long totalMessagesCorrupted = 0;
    /**
     *
     */
    protected MACLayerParameters parameters = new MACLayerParameters();
    /**
     * 
     */
    private long totalNotReceivedMessages = 0;

    /**
     * 
     * @return
     */
    public long getTotalNotReceivedMessages() {
        return totalNotReceivedMessages;
    }

    /**
     *
     * @return
     */
    public long getTotalMessagesCorrupted() {
        return totalMessagesCorrupted;
    }

    /**
     *
     */
    public void incrementTotalMessagesCorrupted() {
        totalMessagesCorrupted += 1;
    }

    /**
     *
     * @return
     */
    public long getTotalMessagesNotSent() {
        return totalMessagesNotSent;
    }

    /**
     *
     */
    public void incrementTotalMessagesNotSent() {
        totalMessagesNotSent += 1;
    }

    /**
     *
     * @return
     */
    public long getTotalMessagesSent() {
        return totalMessagesSent;
    }

    /**
     *
     */
    public void incrementTotalMessagesSent() {
        totalMessagesSent += 1;
    }

    /**
     *
     * @return
     */
    public MACLayerParameters getParameters() {
        return parameters;
    }

    /**
     *
     * @param parameters
     */
    public void setParameters(ObjectParameters parameters) {
        this.parameters = (MACLayerParameters) parameters;
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        parameters.saveToXML(configuration);
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        parameters.loadFromXML(configuration);

    }

    /**
     *
     */
    public void reset() {
        totalMessagesCorrupted = 0;
        totalMessagesNotSent = 0;
        totalMessagesSent = 0;

    }

    public synchronized double getMessageCorruptedRate() {
        if (this.totalMessagesSent > 0) {
            return this.totalMessagesCorrupted * 100 / this.totalMessagesSent;
        } else {
            return 0.0;
        }
    }

    public synchronized double getMessageNotSentRate() {
        if (this.totalMessagesSent > 0) {
            return this.totalMessagesNotSent * 100 / this.totalMessagesSent;
        } else {
            return 0.0;
        }
    }

    void incrementTotalMessagesNotReceived() {
        totalNotReceivedMessages++;
    }

    public double getNotReceivedMessagesRate() {
        if (this.totalMessagesSent > 0) {
            return this.totalNotReceivedMessages * 100 / this.totalMessagesSent;
        } else {
            return 0.0;
        }
    }
}
