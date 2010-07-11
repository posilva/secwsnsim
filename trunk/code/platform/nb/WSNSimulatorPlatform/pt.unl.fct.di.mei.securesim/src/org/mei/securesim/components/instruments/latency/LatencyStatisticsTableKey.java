/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.latency;

/**
 *
 * @author CIAdmin
 */
public class LatencyStatisticsTableKey {

    private ILatencyMessage message;

    LatencyStatisticsTableKey(ILatencyMessage message) {
        this.message = message;

    }

    public void setMessage(ILatencyMessage message) {
        this.message = message;
    }

    public ILatencyMessage getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final LatencyStatisticsTableKey other = (LatencyStatisticsTableKey) obj;
        return other.getMessage().getUniqueId() == getMessage().getUniqueId();
    }

    @Override
    public int hashCode() {
        return (int) message.getUniqueId();
    }
}
