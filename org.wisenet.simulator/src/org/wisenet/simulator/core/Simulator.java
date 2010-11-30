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
package org.wisenet.simulator.core;

import java.util.Collection;
import java.util.List;

import java.util.TreeSet;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.evaluation.tests.TestTypeEnum;
import org.wisenet.simulator.components.simulation.AbstractSimulation;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.events.DelayedEvent;
import org.wisenet.simulator.core.listeners.SimulatorEvent;
import org.wisenet.simulator.core.listeners.SimulatorListener;

import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.NodeDB;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.utilities.RandomGenerator;
import org.wisenet.simulator.utilities.Utilities;

/**
 * This class is the heart of Prowler, as this is the event based scheduler, or
 * simulator.
 * 
 * @author Gyorgy Balogh, Miklos Maroti, Gabor Pap (gabor.pap@vanderbilt.edu)
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class Simulator {

    final static Logger LOGGER = Logger.getLogger(Simulator.class.getName());
    /**
     *
     */
    public static final int SIMULATOR_STEPS = 200;
    /**
     *
     */
    public static final int RUNTIME_NUM_STEPS = 1;
    /**
     *
     */
    public static final Integer SIMULATION_SPEED_DEFAULT = 40000;
    /**
     *
     */
    public static Integer ONE_SECOND = SIMULATION_SPEED_DEFAULT;
    /**
     *
     */
    public final static int FAST = 0;
    /**
     *
     */
    public final static int REAL = 1;
    /**
     * List of event simulatorListeners
     */
    protected EventListenerList simulatorListeners = new EventListenerList();
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
    private boolean stop;
    private boolean paused;
    private RadioModel radioModel;
    private final Object monitor = new Object();
    private final Object resetMonitor = new Object();
    private AbstractSimulation simulation;
    private Thread runningThread;
    private boolean reset = false;
    /**
     *
     */
    protected NodeDB db;
    private int mode;
    /** Holds the events */
    public PriorityQueue eventQueue = new PriorityQueue();
    /** The time of the last event using the given resolution */
    protected static long lastEventTime = 0;
    /** Needed for the display, stores the maximum of both the x and y coordinates */
    protected double maxCoordinate = 0;
    private ISimulationDisplay display;
    private boolean queueWasEmpty = false;
    private boolean start = false;
    private boolean running;

    /**
     *
     */
    public Simulator() {
        super();
        this.db = new NodeDB();
    }

    /**
     *
     * @param node
     */
    public void addNode(Node node) {
        db.nodes().add(node);
    }

    /**
     *
     * @param node
     */
    public void removeNode(Node node) {
        db.nodes().remove(node);

    }

    /**
     *
     * @return
     */
    public Collection<Node> getNodes() {
        return db.nodes();
    }

    /**
     * run startup method in nodes
     */
    public void startUpNodes() {
        for (Node node : getNodes()) {
            node.startUp();
        }
    }

    /**
     *
     */
    public void resume() {
        handleResume();
    }

    /**
     *
     */
    public void stop() {
        stop = true;

    }

    /**
     *
     */
    public void reset() {
        if (!start && !stop) {
            return;
        }
        reset = true;
        doReset();

    }

    /**
     *
     */
    public void pause() {
        paused = true;
    }

    /**
     *
     * @return
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     *
     * @return
     */
    public boolean isStop() {
        return stop;
    }

    /**
     *
     */
    public void start() {
        stop = false;
        reset = false;
        paused = false;
        setupRoutingLayer();
//        if (mode == FAST) {
//            runWithDisplayOLD();
//        } else {
        runWithDisplay();
//        }
        start = true;
    }

    private void fireOnNewStepRound(SimulatorEvent event) {
        Object[] listeners = simulatorListeners.getListenerList();
        int numListeners = listeners.length;
        for (int i = 0; i < numListeners; i += 2) {
            if (listeners[i] == SimulatorListener.class) {
                ((SimulatorListener) listeners[i + 1]).onNewStepRound(event);
            }
        }
    }

    /**
     *
     * @return
     */
    public int getNumberOfRemainEvents() {
        return eventQueue.size();
    }

    private void scheduleTemporizedTests() {
        List<AbstractTest> list = getSimulation().getTestByType(TestTypeEnum.Temporized);
        if (list != null) {
            for (AbstractTest abstractTest : list) {
                if (!abstractTest.isExecuted() && !abstractTest.isExecuting()) {
                    abstractTest.execute();
                }
            }
        }
    }

    private void scheduleOnEmptyQueueTests() {
        List<AbstractTest> list = getSimulation().getTestByType(TestTypeEnum.Temporized);
        if (list != null) {
            for (AbstractTest abstractTest : list) {
                if (!abstractTest.isExecuted() && !abstractTest.isExecuting()) {
                    abstractTest.execute();
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return getNodes().isEmpty();
    }

    private boolean doReset() {
        if (running) {
            waitToReset();
        }

        for (Node node : getNodes()) {
            node.reset();
            start = false;
            stop = false;
            reset = false;
            paused = false;
            eventQueue.clear();
            lastEventTime = 0;
        }
        getRadioModel().reset();
        return true;
    }

    private void notifyReset() {
        synchronized (resetMonitor) {
            resetMonitor.notifyAll();
            running = false;
        }
    }

    private void waitToReset() {
        synchronized (resetMonitor) {
            try {
                resetMonitor.wait();
            } catch (InterruptedException ex) {
            }
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
                }

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
    public static long getSimulationTime() {
        return lastEventTime;
    }

    /**
     *
     * @return Returns the time of the last event in milliseconds.
     */
    public static long getSimulationTimeInMillisec() {
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
            // try to get more accurate event time
            if (event instanceof DelayedEvent) {
                lastEventTime = event.time - ((DelayedEvent) event).getDelay();
            } else {
                lastEventTime = event.time;
            }
            event.execute();
            scheduleTemporizedTests();
            handlePause();
            if (queueWasEmpty) {
                queueWasEmpty = false;
            }
        } else {
            if (!queueWasEmpty) { // sent only one time per occurrency

                fireOnEmptyQueue(new SimulatorEvent(this));
                scheduleOnEmptyQueueTests();
            }
            queueWasEmpty = true;
        }
    }

    /**
     * Processes and executes the next "n" event.
     *
     * @param n the number of events to be processed
     */
    public void step(int n) {
        fireOnNewStepRound(new SimulatorEvent(this));
        for (int i = 0; i < n; ++i) {
            step();
        }
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
                    if (event != null) {
                        lastEventTime = event.time;
                        event.execute();
                    } else {
                        break;
                    }
                    // getDisplay().updateDisplay();
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
    public void runWithDisplayOLD() {
        runningThread = new Thread(new Runnable() {

            public void run() {
                running = true;
                while (canRun()) {
                    step(SIMULATOR_STEPS);
                    display.updateDisplay();
                }
                notifyReset();

            }
        });
        runningThread.start();
    }

    /**
     * This function runs the simulation with the display and in real time.
     * The user of the simulator must first add all the nodes used in the
     * experiment!
     */
    public void runWithDisplay() {
        runningThread = new Thread() {

            private int lastMode = mode;

            @Override
            public void run() {
                running = true;
                long initDiff = System.currentTimeMillis() - getSimulationTimeInMillisec();
                while (canRun()) {
                    step(RUNTIME_NUM_STEPS);
                    display.updateDisplay();

                    if (getMode() == REAL) {
                        if (lastMode != REAL) {
                            initDiff = System.currentTimeMillis() - getSimulationTimeInMillisec();
                            lastMode = REAL;
                        }
                        long diff = System.currentTimeMillis() - getSimulationTimeInMillisec();

                        if (diff < initDiff) {
                            try {
                                Thread.sleep(initDiff - diff);
                            } catch (Exception e) {
                                System.out.println("ERROR IN RUN. EXITING");
                            }
                        }
                    } else {
                        lastMode = FAST;
                    }
                }
                notifyReset();
            }
        };
        runningThread.start();
    }

    private boolean canRun() {
        return !reset && !stop;
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

    private void handlePause() {
        synchronized (monitor) {
            if (paused) {
                try {
                    monitor.wait();
                } catch (InterruptedException ex) {
                    Utilities.handleException(ex);
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

    /**
     *
     * @return
     */
    public AbstractSimulation getSimulation() {
        return simulation;
    }

    /**
     *
     * @param simulation
     */
    public void setSimulation(AbstractSimulation simulation) {
        this.simulation = simulation;
    }

    private void setupRoutingLayer() {
        for (Object object : getNodes()) {
            Node n = (Node) object;
            n.getRoutingLayer().startup();
        }
    }

    /**
     *
     * @return
     */
    public Node getRandomNode() {
        return db.randomNode();
    }

    /**
     *
     * @param mode
     */
    public synchronized void setMode(int mode) {
        this.mode = mode;
    }

    /**
     *
     * @return
     */
    public synchronized int getMode() {
        return mode;
    }

    /**
     * Adds a simulator listener
     * @param listener
     */
    public void addListener(SimulatorListener listener) {
        simulatorListeners.add(SimulatorListener.class, listener);
    }

    /**
     * Remove the simulator listener
     * @param listener
     */
    public void removeListener(SimulatorListener listener) {
        simulatorListeners.remove(SimulatorListener.class, listener);
    }

    private void fireOnEmptyQueue(SimulatorEvent event) {
        Object[] listeners = simulatorListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i < numListeners; i += 2) {
            if (listeners[i] == SimulatorListener.class) {
                // pass the event to the simulatorListeners event dispatch method
                ((SimulatorListener) listeners[i + 1]).onEmptyQueue(event);
            }
        }
    }
}
