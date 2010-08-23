/**
 * 
 */
package org.wisenet.simulator.core.energy.listeners;

import java.util.EventObject;
import org.wisenet.simulator.core.energy.Batery;

/**
 * @author posilva
 *
 */
public class EnergyEvent extends EventObject {
    private double value;
    private long realTime;
    private long simTime;
    private String event;
    private short nodeid;
    private String state;

    public String getEvent() {
        return event;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public short getNodeid() {
        return nodeid;
    }

    public void setNodeid(short nodeid) {
        this.nodeid = nodeid;
    }

    @Override
    public Batery getSource() {
        return (Batery) source;
    }

    public long getRealTime() {
        return realTime;
    }

    public void setRealTime(long time) {
        this.realTime = time;
    }

    public double getValue() {
        return value;
    }

    public long getSimTime() {
        return simTime;
    }

    public void setSimTime(long simTime) {
        this.simTime = simTime;
    }

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
