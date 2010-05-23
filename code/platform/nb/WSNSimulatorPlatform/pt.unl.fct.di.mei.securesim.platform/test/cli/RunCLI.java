package cli;

import org.mei.securesim.components.simulation.SimulationFactory;
import org.mei.securesim.components.simulation.basic.BasicSimulation;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CIAdmin
 */
public class RunCLI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        readConfigurations();

        BasicSimulation simulation = new BasicSimulation();
        SimulationFactory sf = new SimulationFactory();
        

        simulation.start();
    }

    private static void readConfigurations() {
        
    }

}
