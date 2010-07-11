/*
 * Copyright (c) 2003, Vanderbilt University
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for any purpose, without fee, and without written agreement is
 * hereby granted, provided that the above copyright notice, the following
 * two paragraphs and the author appear in all copies of this software.
 * 
 * IN NO EVENT SHALL THE VANDERBILT UNIVERSITY BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
 * OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE VANDERBILT
 * UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * THE VANDERBILT UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
 * ON AN "AS IS" BASIS, AND THE VANDERBILT UNIVERSITY HAS NO OBLIGATION TO
 * PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 *
 * Author: Gyorgy Balogh, Gabor Pap, Miklos Maroti
 * Date last modified: 02/09/04
 */
package org.mei.securesim.core.engine;

import java.util.Collection;

import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.components.simulation.Simulation;
import org.mei.securesim.core.ui.ISimulationDisplay;
import org.mei.securesim.core.engine.events.SimulatorEvent;

import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.core.network.Network;
import org.mei.securesim.core.ui.Display;
import org.mei.securesim.utils.RandomGenerator;

/**
 * This class is the heart of Prowler, as this is the event based scheduler, or
 * simulator.
 * 
 * @author Gyorgy Balogh, Miklos Maroti, Gabor Pap (gabor.pap@vanderbilt.edu)
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class Simulator {

    public static final int SIMULATOR_STEPS = 200;
    public static final int RUNTIME_NUM_STEPS = 100;
    public static final Integer SIMULATION_SPEED_DEFAULT = 80000;
    public static Integer ONE_SECOND = SIMULATION_SPEED_DEFAULT;
    final static Logger LOGGER = Logger.getLogger(Simulator.class.getName());
    /**
     * This is a static reference to a Random instance.
     * This makes experiments repeatable, all you have to do is to set
     * the seed of this Random class.
     */
    static public RandomGenerator randomGenerator = new RandomGenerator();
    /**
     * This defines the time resolution. Every time and time interval
     * in the simulator is represented in this resolution. This rate
     * corresponds to the 38.4 kbps speed, but maybe a little friendlier.
     */
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    private boolean finished;
    private boolean stop;
    private boolean paused;
    private RadioModel radioModel;
    private final Object monitor = new Object();
    private Simulation simulation;
    private Thread runningThread;

    public enum RunMode {

        REAL_TIME, TIME, STEPS
    }

    public Simulator() {

        super();

    }
    /** Holds the events */
    public PriorityQueue eventQueue = new PriorityQueue();
    /** The time of the last event using the given resolution */
    long lastEventTime = 0;
    /** Needed for the display, stores the maximum of both the x and y coordinates */
    protected double maxCoordinate = 0;
    private ISimulationDisplay display;
    private Network network = null;

    /**
     * @param network the network to set
     */
    public void setNetwork(Network network) {
        this.network = network;
        this.network.setSimulator(this);
    }

    /**
     * @return the network
     */
    public Network getNetwork() {
        return network;
    }

    public Collection<Node> getNodes() {
        return network.getNodeDB().nodes();
    }

    /**
     * run startup method in nodes
     */
    public void startUpNodes() {
        for (Node node : getNodes()) {
            node.startUp();
        }
    }

    public void resume() {
        handleResume();
    }

    public void stop() {
        stop = true;
        SimulationController.getInstance().stop();
        reset();
    }

    public void reset() {
        if (runningThread != null) {
            runningThread.interrupt();
        }
    }

    public void pause() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isStop() {
        return stop;
    }

    public void start() {

        SimulationController.getInstance().begin();
        setupRoutingLayer();
        if (SimulationController.getInstance().getMode() == SimulationController.FAST) {
            runWithDisplay();
        } else {
            runWithDisplayInRealTime();
        }
    }

    /**
     * As the simulator is event based there is a need for a queue to handle
     * the occurring events. This general priority queue is based on a
     * a TreeSet.
     */
    public class PriorityQueue {

        private TreeSet queue = new TreeSet();

        /** Adds an item to the queue, item must be Comparable
         * @param item The item to be added to the queue
         */
        public void add(Comparable item) {
            try {
                queue.add(item);
            } catch (Exception e) {
                System.out.println("ERROR. RETRYING.");
                try {
                    queue.add(item);
                } catch (Exception e1) {
                    System.out.println("ERROR. EXITING.");
                    fireOnFinishSimulation(new SimulatorEvent(new String(e1.getMessage())));
                }

            }
            if (item == null) {

                fireOnFinishSimulation(new SimulatorEvent(new String("EXIT")));
            }
        }

        /**
         * @return Returns the first element and removes it form the queue
         */
        public Object getAndRemoveFirst() {
            if (queue.size() > 0) {
                Object first = queue.first();
                queue.remove(first);
                return first;
            } else {
                return null;
            }
        }

        /**
         * Clears the queue
         */
        public void clear() {
            queue.clear();
        }

        /**
         * @return Returns the number of items in the queue
         */
        public int size() {
            return queue.size();
        }
    }

    /**
     *
     * @return Returns the time of the last event in 1/ONE_SECOND
     */
    public long getSimulationTime() {
        return lastEventTime;
    }

    /**
     *
     * @return Returns the time of the last event in milliseconds.
     */
    public long getSimulationTimeInMillisec() {
        return (long) (1000 * lastEventTime / (double) Simulator.ONE_SECOND);
    }

    /**
     * Adds an event to the event queue.
     *
     * @param e the event to be added to the queue
     */
    public synchronized void addEvent(Event e) {
        eventQueue.add(e);
    }

    /**
     * Processes and executes the next event.
     */
    public synchronized void step() {

        Event event = (Event) eventQueue.getAndRemoveFirst();
        if (event != null) {
            lastEventTime = event.time;
            event.execute();
            handlePause();
        } else {
            if (!finished) {
                fireOnFinishSimulation(new SimulatorEvent("EXIT"));
            }
            finished = true;
        }
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

    /**
     * Runs the simulation for a given amount of time.
     *
     * @param timeInSec the time in seconds until the simulation is to run
     */
    public void run(final double timeInSec) {
        runningThread = new Thread(new Runnable() {

            public void run() {
                long tmax = lastEventTime + (int) (Simulator.ONE_SECOND * timeInSec);
                while (lastEventTime < tmax) {
                    Event event = (Event) eventQueue.getAndRemoveFirst();
                    //Event event = (Event)eventQueue.poll();
                    if (event != null) {
                        lastEventTime = event.time;
                        event.execute();
                    } else {
                        break;
                    }
                    // getDisplay().update();

                }
            }
        });
        runningThread.start();
    }

    /**
     * This function runs the simulation with the display.
     * The user of the simulator must first add all the nodes used in the
     * experiment!
     */
    public void runWithDisplay() {
        runningThread = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    step(SIMULATOR_STEPS);
                    display.update();
                }
            }
        });
        runningThread.start();
    }

    /**
     * This function runs the simulation with the display and in real time.
     * The user of the simulator must first add all the nodes used in the
     * experiment!
     */
    /**
     * Fazer a simulação andar mais depressa
     */
    public void runWithDisplayInRealTime() {
        runningThread = new Thread() {

            @Override
            public void run() {
//                display.show();

                long initDiff = System.currentTimeMillis() - getSimulationTimeInMillisec();
                while (true) {

                    step(RUNTIME_NUM_STEPS);

                    display.update();

                    long diff = System.currentTimeMillis() - getSimulationTimeInMillisec();

                    if (diff < initDiff) {
                        try {
                            System.out.print("sleeping...");
                            sleep(initDiff - diff);
                            System.out.println(" done!");
                        } catch (Exception e) {
                            System.out.println("ERROR IN RUN. EXITING");
                            fireOnFinishSimulation(new SimulatorEvent("ERROR IN RUN. EXITING"));
                        }
                    }

                }
            }
        };
        runningThread.start();
    }

    /**
     * Called by the {@link Display} whenever it is repainted, it calls the
     * {@link Node#display} method on every nodes in its Node list.
     *
     * @param disp the Display on which to draw Nodes
     */
    public synchronized void display(ISimulationDisplay disp) {
        for (Node n : getNodes()) {
            n.displayOn(disp);
        }
    }

    /**
     * @param radioModel the radioModel to set
     */
    public void setRadioModel(RadioModel radioModel) {
        this.radioModel = radioModel;
        this.radioModel.setSimulator(this);
    }

    /**
     * @return the radioModel
     */
    public RadioModel getRadioModel() {
        return radioModel;
    }

    /**
     * Initialize simulator before start
     * @return
     */
    public Simulator init() {
        radioModel.updateNeighborhoods();
        startUpNodes();
        return this;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(ISimulationDisplay display) {
        this.display = display;
    }

    /**
     * @return the display
     */
    public ISimulationDisplay getDisplay() {
        return display;
    }

    protected synchronized void fireOnFinishSimulation(SimulatorEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == SimulatorFinishListener.class) {
                ((SimulatorFinishListener) listeners[i + 1]).onFinish(evt);
            }
        }
    }

    public void addSimulatorFinishListener(SimulatorFinishListener listener) {
        listenerList.add(SimulatorFinishListener.class, listener);
    }

    public void removeSimulatorFinishListener(SimulatorFinishListener listener) {
        listenerList.remove(SimulatorFinishListener.class, listener);
    }

    private void handlePause() {

        synchronized (monitor) {
            if (paused) {
                try {
                    monitor.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void handleResume() {
        synchronized (monitor) {
            if (paused) {
                monitor.notifyAll();
            }
            paused = false;
        }

    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    private void setupRoutingLayer() {
        for (Object object : getNodes()) {
            Node n = (Node) object;
            n.getRoutingLayer().setup();
        }
    }
}
