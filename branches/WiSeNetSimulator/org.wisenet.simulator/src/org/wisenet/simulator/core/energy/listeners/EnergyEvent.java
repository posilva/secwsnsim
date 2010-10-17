/**
 * 
 */
package org.wisenet.simulator.core.energy.listeners;

import java.util.EventObject;
import org.wisenet.simulator.core.energy.Batery;

/**
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 *
 */
public class EnergyEvent extends EventObject {
    private double value;
    private long realTime;
    private long simTime;
    private String event;
    private short nodeid;
    private String state;

    /**
     *
     * @return
     */
    public String getEvent() {
        return event;
    }

    /**
     * 
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @param event
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     *
     * @return
     */
    public short getNodeid() {
        return nodeid;
    }

    /**
     *
     * @param nodeid
     */
    public void setNodeid(short nodeid) {
        this.nodeid = nodeid;
    }

    /**
     *
     * @return
     */
    @Override
    public Batery getSource() {
        return (Batery) source;
    }

    /**
     *
     * @return
     */
    public long getRealTime() {
        return realTime;
    }

    /**
     *
     * @param time
     */
    public void setRealTime(long time) {
        this.realTime = time;
    }

    /**
     *
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     *
     * @return
     */
    public long getSimTime() {
        return simTime;
    }

    /**
     *
     * @param simTime
     */
    public void setSimTime(long simTime) {
        this.simTime = simTime;
    }

    /**
     *
     * @param source
     * @param value
     * @param realTime
     * @param simTime
     * @param event
     * @param nodeid
     * @param state
     */
    public EnergyEvent(Object source, double value, long realTime, long simTime, String event, short nodeid, String state) {
        super(source);
        this.value = value;
        this.realTime = realTime;
        this.simTime = simTime;
        this.event = event;
        this.nodeid = nodeid;
        this.state = state;
    }


}
