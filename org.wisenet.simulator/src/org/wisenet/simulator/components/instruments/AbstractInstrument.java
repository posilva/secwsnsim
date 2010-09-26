/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments;

import java.util.Vector;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class AbstractInstrument {

    protected boolean debugEnabled = true;
    protected int probingTests = 0;
    protected boolean notifyPanel = false;
    protected static int messageUniqueId = 0; 
    protected boolean autonumber = false;
    protected Vector senders = new Vector();
    protected Vector receivers = new Vector();
    protected IInstrumentControlPanel controlPanel;
    protected int totalMessagesSent = 0;
    protected boolean enable = false;
    protected Class messageClass = null;
    protected Simulation simulation = null;

    public AbstractInstrument() {
    }

    public AbstractInstrument(Simulation simulation) {
        this.simulation = simulation;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     *
     * @return
     */
    public boolean isNotifyPanel() {
        return notifyPanel;
    }

    /**
     *
     * @param notifyPanel
     */
    public void setNotifyPanel(boolean notifyPanel) {
        this.notifyPanel = notifyPanel;
    }

    /**
     *
     * @param cp
     */
    public void registerControlPanel(IInstrumentControlPanel cp) {
        controlPanel = cp;
    }

    /**
     *
     * @param n
     */
    public void registerSender(IInstrumentHandler n) {
        if (!receivers.contains(n)) {
            if (!senders.contains(n)) {
                senders.add(n);
                refreshPanel();
            }
        }

    }

    /**
     * 
     * @param n
     */
    public void registerReceiver(IInstrumentHandler n) {
        if (!senders.contains(n)) {
            if (!receivers.contains(n)) {
                receivers.add(n);
                refreshPanel();
            }
        }
    }

    /**
     * 
     * @param n
     */
    public void unregisterSender(IInstrumentHandler n) {
        senders.remove(n);
        refreshPanel();
    }

    public void unregisterReceiver(IInstrumentHandler n) {
        receivers.remove(n);
        refreshPanel();

    }

    public int getTotalMessagesSent() {
        return totalMessagesSent;
    }

    public void refreshPanel() {
        if (notifyPanel) {
            if (controlPanel != null) {
                controlPanel.refresh(this);
            }
        }
    }

    /**
     *
     * @param message
     * @param handler
     */
    public void notifyMessageSent(IInstrumentMessage message, IInstrumentHandler handler) {
        onMessageSent(message, handler);
        refreshPanel();
    }

    /**
     * 
     * @param message
     * @param handler
     */
    public void notifyMessageReceived(IInstrumentMessage message, IInstrumentHandler handler) {
        onMessageReceived(message, handler);
        refreshPanel();
    }

    public boolean isAutonumber() {
        return autonumber;
    }

    public void setAutonumber(boolean autonumber) {
        this.autonumber = autonumber;
    }

    protected void onMessageSent(IInstrumentMessage message, IInstrumentHandler handler) {
        if (senders.contains(handler)) { // sent by a sender
            saveMessage(message, handler);
        }
    }

    protected void onMessageReceived(IInstrumentMessage message, IInstrumentHandler handler) {
        if (receivers.contains(handler)) { // for a receiver
            computeMessageReception(message, handler);
        }
    }

    public void setMessageClass(Class messageClass) {
        try {
            Object o = messageClass.newInstance();
            if (o instanceof IInstrumentMessage) {
                this.messageClass = messageClass;
                return;
            }
        } catch (InstantiationException ex) {
            Utilities.handleException(ex);
        } catch (IllegalAccessException ex) {
            Utilities.handleException(ex);
        }
    }

    protected IInstrumentMessage createMessageInstance() {
        if (this.messageClass != null) {
            try {
                return (IInstrumentMessage) this.messageClass.newInstance();
            } catch (InstantiationException ex) {
                Utilities.handleException(ex);
            } catch (IllegalAccessException ex) {
                Utilities.handleException(ex);
            }
        }
        return null;
    }

    public int getProbingTests() {
        return probingTests;
    }

    public IInstrumentControlPanel getControlPanel() {
        return controlPanel;
    }

    public Class getMessageClass() {
        return messageClass;
    }

    public Vector getReceivers() {
        return receivers;
    }

    public Vector getSenders() {
        return senders;
    }

    protected void log(String m) {
        if (isDebugEnabled()) {
            System.out.println("<" + this.getClass().getSimpleName() + ">" + m);
        }
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    /**
     * Execute a probe procedure 
     */
    public abstract void probe();

    public abstract void reset();

    protected abstract void saveMessage(IInstrumentMessage message, IInstrumentHandler handler);

    protected abstract void computeMessageReception(IInstrumentMessage message, IInstrumentHandler handler);

    public Simulation getSimulation() {
        return simulation;
    }

    public abstract IResult getLastProbeResult();
}
