package org.mei.securesim.core.engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.core.engine.Simulator.RunMode;
import org.mei.securesim.core.engine.events.SimulatorEvent;

/**
 *
 * @author posilva
 */
public class SimulatorWorker extends Thread {

    public static final int RUNTIME_NUM_STEPS = 1;
    Simulator simulator;
    RunMode mode;
    final Object monitor = new Object();
    volatile boolean running = true;
    private volatile boolean pause;

    public SimulatorWorker(Simulator simulator) {
        this.simulator = simulator;
    }

    public void startRun(RunMode mode) {
        this.mode = mode;
        start();
    }

    public void handlePause() {
        if (pause) {
            synchronized (monitor) {
                try {
                    System.out.println("PAUSE SIM");
                    monitor.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(SimulatorWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("RESUMED DONE");
            }
        }
    }

    public void pauseRun() {
        simulator.setPaused(true);
    }

    public void resumeRun() {
        if (pause) {
            System.out.println("Try2 Pause");
            synchronized (monitor) {
                simulator.setPaused(false);
                System.out.println("RESUME SIM");
                pause=false;
                monitor.notifyAll();
            }
        }
    }

    public void stopRun() {
        simulator.setStop(true);
        running = false;
    }

    @Override
    public void run() {


        switch (mode) {
            case REAL_TIME:
                runWithDisplayInRealTime();
                break;

            case STEPS:
                break;
            case TIME:
                break;
        }

    }

    public void runWithDisplayInRealTime() {
        long initDiff = System.currentTimeMillis() - simulator.getSimulationTimeInMillisec();
        while (running) {
            step(RUNTIME_NUM_STEPS);
            simulator.getDisplay().update();
            long diff = System.currentTimeMillis() - simulator.getSimulationTimeInMillisec();
            if (diff < initDiff) {
                try {
                    sleep(initDiff - diff);
                } catch (Exception e) {
                    System.out.println("ERROR IN RUN. EXITING");
                    simulator.fireOnFinishSimulation(new SimulatorEvent("ERROR IN RUN. EXITING"));
                }
            }
        }
    }

    /**
     * Processes and executes the next event.
     */
    public void step() {

        Event event = (Event) simulator.eventQueue.getAndRemoveFirst();
        //Event event = (Event)eventQueue.poll();

        if (event != null) {
            simulator.lastEventTime = event.time;
            event.execute();
            // System.out.println("Executado um evento: "+ event.getClass().getSimpleName());
        } else {
//            if (!simulator.finished) {
//                simulator.fireOnFinishSimulation(new SimulatorEvent(new String("EXIT")));
//            }
//            simulator.finished = true;
        }
        handlePause();
    }

    /**
     * Processes and executes the next "n" event.
     *
     * @param n the number of events to be processed
     */
    public void step(int n) {
        for (int i = 0; i < n; ++i) {
            step();
        }
        //System.out.println("Faltam tratar "+ eventQueue.size()+ "eventos") ;
    }

    void setPause(boolean pause) {
        this.pause = pause;
    }
}
