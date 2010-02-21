/**
 * 
 */
package pt.unl.fct.di.mei.securesim.simulation.basic;

import pt.unl.fct.di.mei.securesim.simulation.Simulation;

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
        Runnable r = new Runnable() {

            public void run() {
               
                getSimulator().runWithDisplay();
            }
        };

        new Thread(r).start();

    }

    /* (non-Javadoc)
     * @see pt.unl.fct.di.mei.securesim.simulation.Simulation#stop()
     */
    @Override
    public void stop() {
    }

    /* (non-Javadoc)
     * @see pt.unl.fct.di.mei.securesim.configuration.ConfigurableObject#init()
     */
    @Override
    protected void init() {
    }
}
