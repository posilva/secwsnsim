/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.events;

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
    public static Event createTotalCoverageEvent(short repetitions, long interval, Class messageClass) {
        TotalCoverageEvent coverageEvent = new TotalCoverageEvent();
        coverageEvent.setNumberOfRepetitions(repetitions);
        coverageEvent.setInterval(interval);
        coverageEvent.setMessageClass(messageClass);
        coverageEvent.setTime(SimulationController.getInstance().getCurrentSimulationTime()+ interval);
        return coverageEvent;
    }
}
