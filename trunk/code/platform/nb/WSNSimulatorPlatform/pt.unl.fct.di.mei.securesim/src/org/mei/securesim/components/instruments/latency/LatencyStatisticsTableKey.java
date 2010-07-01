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
        other.getMessage().getUniqueId().equals(getMessage().getUniqueId());
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.message.getUniqueId() != null ? this.message.getUniqueId().hashCode() : 0);
        return hash;
    }

}
