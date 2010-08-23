package org.wisenet.simulator.core.node;

import java.awt.Color;
import java.text.DecimalFormat;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.core.Event;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.Batery;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.node.components.CPU;
import org.wisenet.simulator.core.node.layers.mac.MACLayer;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.components.Transceiver;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.core.radio.RadioModel.Neighborhood;
import org.wisenet.simulator.gui.GraphicNode;

/**
 * This class is the base class of all nodes. Nodes are entities in a simulator
 * which act on behalf of themselves, they also have some basic attributes like
 * location. Nodes also take part in radio transmissions, they initiate
 * transmissions and receive incomming radio messages.
 * 
 * @author Gabor Pap, Gyorgy Balogh, Miklos Maroti
 */
@SuppressWarnings("unchecked")
public abstract class Node {

    /**
     * Constants
     */
    public static int NODEID_AUTOCOUNTER = 1;
    public static final int INITIAL_BATERY_POWER = 1000;
    public static final double DEFAULT_POWER_CONSUMING = 1.0E-2;
    private static long CLOCK_TICK = Simulator.ONE_SECOND;// /100;
    /**
     * Attributes
     */
    protected boolean sinkNode = false;
    protected Batery bateryEnergy = null;
    protected Application application;
    protected RoutingLayer routingLayer = null;
    protected MACLayer macLayer = null;
    protected Config config = new Config();
    protected boolean turnedOn = true;
    protected GraphicNode graphicNode;
    protected CPU cpu = null;
    protected Transceiver transceiver = null;
    protected Color messageColor = Color.BLUE;
    /**
     * This is the message being sent, on reception it is extracted and the
     * message part is forwarded to the appropriate application, see
     * {@link Application#receiveMessage}.
     */
    protected Message message;
    /** A reference to the simulator in which the Node exists. */
    public Simulator simulator;
    /**
     * The id of the node. It is allowed that two nodes have the same id in the
     * simulator.
     */
    protected short id;
    /**
     * The neighborhood of this node, meaning all the neighboring nodes which
     * interact with this one.
     */
    public Neighborhood neighborhood;
    /**
     * 
     */
    protected Node parentNode;
    private boolean enableFunctioningEnergyConsumption = false;

    public enum NodeState {

        ACTIVE,
        SLEEP
    };
    protected NodeState state;

    public class Config {

        public static final int DEFAULT_MAX_COMUNICATION_RANGE = 30;
        public static final int DEFAULT_MAX_RADIO_STRENGTH = 100;
        /**
         * This field defines the relative strength of a mote. If it is set to a
         * high value for a given mote it can supress other motes.
         */
        double maxRadioStrength = DEFAULT_MAX_RADIO_STRENGTH;
        double setRadioRange = DEFAULT_MAX_COMUNICATION_RANGE;

        /*
         * (non-Javadoc)
         *
         * @see net.tinyos.prowler.INode#getMaximumRadioStrength()
         */
        public double getMaximumRadioStrength() {
            return maxRadioStrength;
        }

        /*
         * (non-Javadoc)
         *
         * @see net.tinyos.prowler.INode#setMaximumRadioStrength(double)
         */
        public void setMaximumRadioStrength(double d) {
            maxRadioStrength = d;
        }

        public double getSetRadioRange() {
            return setRadioRange;
        }

        public void setSetRadioRange(int setRadioRange) {
            // é necessário rever para calcular a função inversa que permite 
            // estimar o m do no face a um range desejado

            switch (setRadioRange) {
                case 30:
                    setMaximumRadioStrength(100);
                    break;
                case 130:
                    setMaximumRadioStrength(2300);
                    break;
                case 230:
                    setMaximumRadioStrength(7300);
                    break;
                case 300:
                    setMaximumRadioStrength(13000);
                    break;
            }
            this.setRadioRange = setRadioRange;
        }
    }

    /**
     * Parameterized constructor, sets the simulator and creates an initial
     * neighborhood using the RadioModel as a factory.
     *
     * @param sim
     *            the Simulator
     * @param radioModel
     *            the RadioModel used to create the nodes neighborhood
     */
    public Node(Simulator sim, RadioModel radioModel) {
        this.simulator = sim;
        neighborhood = radioModel.createNeighborhood();
        this.graphicNode = new GraphicNode(this);
        this.bateryEnergy = new Batery();
        this.bateryEnergy.setHostNode(this);
        this.cpu = new CPU(this);
        this.transceiver = new Transceiver(this);
        setId((short) NODEID_AUTOCOUNTER++);
    }

    /**
     * Method to be implemented by child classes
     */
    public abstract void init();

    public boolean isSinkNode() {
        return sinkNode;
    }

    public void setSinkNode(boolean sinkNode) {
        this.sinkNode = sinkNode;
    }

    public void initEnergyConsumation() {
        if (enableFunctioningEnergyConsumption) {
            simulator.addEvent(new Node.EnergyWasteEvent((int) Simulator.randomGenerator.random().nextDouble() * CLOCK_TICK));
        }
    }

    public boolean isEnableFunctioningEnergyConsumption() {
        return enableFunctioningEnergyConsumption;
    }

    public void setEnableFunctioningEnergyConsumption(boolean enableFunctioningEnergyConsumption) {
        this.enableFunctioningEnergyConsumption = enableFunctioningEnergyConsumption;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = (Message) message;
    }

    public MACLayer getMacLayer() {
        return macLayer;
    }

    public void setMacLayer(MACLayer macLayer) {
        this.macLayer = macLayer;
    }

    public void initBatery() {
        initEnergyConsumation();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getDistanceSquare(net.tinyos.prowler.Node)
     */
    public double getDistanceSquare(Node other) {
        return (getX() - other.getX()) * (getX() - other.getX()) + (getY() - other.getY()) * (getY() - other.getY())
                + (getZ() - other.getZ()) * (getZ() - other.getZ());
    }

    public double getDistance(Node other) {
        return Math.sqrt(getDistanceSquare(other));
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#setId(short)
     */
    public final void setId(short id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#setPosition(double, double, double)
     */
    public void setPosition(double x, double y, double z) {
        getGraphicNode().moveTo((int) x, (int) y);
        getGraphicNode().setZ((int) z);

    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getX()
     */
    public double getX() {
        return getGraphicNode().getX();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getY()
     */
    public double getY() {
        return getGraphicNode().getY();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getZ()
     */
    public double getZ() {
        return getGraphicNode().getZ();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getId()
     */
    public short getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getSimulator()
     */
    public Simulator getSimulator() {
        return simulator;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.tinyos.prowler.INode#addApplication(net.tinyos.prowler.Application)
     */
    public void addApplication(Application app) {
        app.setNode(this);
        application = app;
    }

    /**
     * Visiting the elements of the application list, it returns the first with
     * the given application class.
     *
     * @param appClass
     *            the class that identifies the needed application for us
     * @return Returns the application instance running on this node
     */
    public Application getApplication() {
        return application;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#display(net.tinyos.prowler.Display)
     */
    public synchronized void displayOn(ISimulationDisplay disp) {
        application.display(disp);
    }

    /**
     * @param config
     *            the config to set
     */
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * @return the config
     */
    public Config getConfig() {
        return config;
    }

    /**
     * @param routingLayer the routingLayer to set
     */
    public void setRoutingLayer(RoutingLayer routingLayer) {
        this.routingLayer = routingLayer;
        this.routingLayer.setNode(this);
    }

    /**
     * @return the routingLayer
     */
    public RoutingLayer getRoutingLayer() {
        return routingLayer;
    }

    public Node getParentNode() {

        return parentNode;
    }

    /**
     * @param parentNode the parentNode to set
     */
    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * @param bateryEnergy the bateryEnergy to set
     */
    public void setBateryEnergy(Batery bateryEnergy) {
        this.bateryEnergy = bateryEnergy;
    }

    /**
     * @return the bateryEnergy
     */
    public Batery getBateryEnergy() {
        return bateryEnergy;
    }

    public boolean sendMessageFromApplication(Object message, Application app) {
        return app.sendMessage(message);
    }

    public boolean sendMessage(Object message) {
        return application.sendMessage(message);
    }

    public void dispose() {
    }

    /**
     * @param turnedOn the turnedOn to set
     */
    public void setTurnedOn(boolean turnedOn) {
        this.turnedOn = turnedOn;
    }

    /**
     * @return the turnedOn
     */
    public boolean isTurnedOn() {
        return turnedOn;
    }

    public GraphicNode getGraphicNode() {
        return graphicNode;
    }

    public void setGraphicNode(GraphicNode graphicNode) {
        this.graphicNode = graphicNode;
    }

    public int getRadius() {
        return getGraphicNode().getRadius();
    }

    public void setRadius(int radius) {
        getGraphicNode().setRadius(radius);
    }

    public void startUp() {
        initBatery();
    }

    /**
     * 
     */
    class EnergyWasteEvent extends Event {

        public EnergyWasteEvent(long time) {
            super(time);
        }

        public EnergyWasteEvent() {
            super();
        }

        @Override
        public void execute() {
            if (turnedOn) {
                if (!getBateryEnergy().off()) {
                    getBateryEnergy().consumeIdle();
                    time += CLOCK_TICK;
                    simulator.addEvent(this);
                } else {
                    turnedOn = false;
                }
            }
        }
    }

    public void shutdown() {
        turnedOn = false;
    }

    public void powerOn() {
        if (!getBateryEnergy().off()) {
            turnedOn = true;
            initEnergyConsumation();
        }
    }

    public void setApplication(Application application) {
        this.application = application;
        application.setNode(this);
    }

    public String[] getInfo() {
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        final double remainingPower = 100 * getBateryEnergy().getCurrentPower() / getBateryEnergy().getInitialPower();
        int nroNeighbors = 0;
        try {
            nroNeighbors = getMacLayer().getNeighborhood().neighbors.size();
        } catch (Exception e) {
        }


        String[] nodeInfo = new String[]{"ID: " + getId(),
            "Sink:  " + (isSinkNode() ? "True" : "False"),
            "Position:  (" + (int) getX() + " , " + (int) getY() + " , " + (int) getZ() + ")",
            "Node:  " + this.getClass().getSimpleName(),
            "Application:  " + this.getApplication().getClass().getSimpleName(),
            "Routing:  " + this.getRoutingLayer().getClass().getSimpleName(),
            "MAC:  " + this.getMacLayer().getClass().getSimpleName(),
            "Nro. Neighbors:  " + nroNeighbors,
            "Remaining Power:  " + twoPlaces.format(remainingPower <= 0.0 ? 0 : remainingPower) + "%",
            "Routing Stable: " + getRoutingLayer().isStable(),
            "RSS: " + getMacLayer().getSignalStrength(),
            "MRS: " + getConfig().getMaximumRadioStrength(),};

        return nodeInfo;
    }

    public CPU getCPU() {
        return cpu;
    }

    public void setCPU(CPU cpu) {
        this.cpu = cpu;
    }

    public static void resetCounter() {
        NODEID_AUTOCOUNTER = 1;
    }

    public Transceiver getTransceiver() {
        return transceiver;
    }

    public void setTransceiver(Transceiver transceiver) {
        this.transceiver = transceiver;
    }

    public static Node cast(Object object) {
        return (Node) object;
    }

    public NodeState getState() {
        return state;
    }

    public void setState(NodeState state) {
        this.state = state;
    }

    public boolean isActive() {
        return state == NodeState.ACTIVE;
    }

    public boolean isSleep() {
        return state == NodeState.SLEEP;
    }

    public Color getMessageColor() {
        return messageColor;
    }

    public void setMessageColor(Color messageColor) {
        this.messageColor = messageColor;
    }
}
