/**
 * 
 */
package org.mei.securesim.core.energy.listeners;

import java.util.EventObject;
import org.mei.securesim.core.energy.Batery;

/**
 * @author posilva
 *
 */
public class EnergyEvent extends EventObject {
    private double value=0;
    private long time;
    private String event="";
    private short nodeid;

    public String getEvent() {
        return event;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    public EnergyEvent(Batery aThis, double value, long time) {
        super(aThis);
        this.value=value;
        this.time = time;
    }

    public double getValue() {
        return value;
    }

//	public EnergyEvent(Object arg0) {
//		super(arg0);
//	}
//
//    public EnergyEvent(Batery aThis, double value) {
//        super(aThis);
//        this.value=value;
//    }

}
