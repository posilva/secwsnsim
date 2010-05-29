/**
 * 
 */
package org.mei.securesim.components.simulation.basic;

import org.mei.securesim.components.simulation.Simulation;

/**
 * @author posilva
 *
 */
public class BasicSimulation extends Simulation {

    /**
     *
     */
    public BasicSimulation() {
    }

    /* (non-Javadoc)
     * @see pt.unl.fct.di.mei.securesim.simulation.Simulation#start()
     */
    @Override
    public void start() {
        if (!getSimulator().isPaused()) {
            getSimulator().start();
        
        } else {
            getSimulator().resume();
        }

    }

    /* (non-Javadoc)
     * @see pt.unl.fct.di.mei.securesim.simulation.Simulation#stop()
     */
    @Override
    public void stop() {
        getSimulator().stop();



    }

    @Override
    public void reset() {
        getSimulator().reset();


    }

    @Override
    public void pause() {
        getSimulator().pause();

    }
}
