/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.platform.core.charts;

import org.jfree.data.general.Dataset;

/**
 *
 * @author posilva
 */
public interface SimulationChartUpdateAction {

    /**
     *
     * @param dataset
     */
    public void update(Dataset dataset);
}
