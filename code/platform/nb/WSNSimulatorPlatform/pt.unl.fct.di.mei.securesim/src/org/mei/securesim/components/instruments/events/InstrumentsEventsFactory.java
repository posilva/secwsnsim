/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.events;

import org.mei.securesim.components.instruments.coverage.TotalCoverageEvent;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.core.engine.Event;

/**
 *
 * @author Pedro Marques da Silva
 */
public class InstrumentsEventsFactory {
    /**
     * Creates a Total Repetition Event
     * @param repetitions
     * @param interval
     * @return
     */
    public static Event createTotalCoverageEvent(short repetitions, long delay, long interval, Class messageClass, int messageUI) {
        if (messageClass==null)
            throw  new IllegalArgumentException("Message Class cannot be null");
        TotalCoverageEvent coverageEvent = new TotalCoverageEvent();
        coverageEvent.setMessageUniqueId(messageUI);
        coverageEvent.setMessageClass(messageClass);
        coverageEvent.setTime(SimulationController.getInstance().getCurrentSimulationTime()+ delay);
        return coverageEvent;
    }
}
