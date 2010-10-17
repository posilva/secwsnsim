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
    /**
     *
     */
    public static final int INITIAL_BATERY_POWER = 1000;
    /**
     *
     */
    public static final double DEFAULT_POWER_CONSUMING = 1.0E-2;
    private static long CLOCK_TICK = Simulator.ONE_SECOND;// /100;
    /**
     * Coordinates
     */
    protected double x = 0;
    /**
     *
     */
    protected double y = 0;
    /**
     *
     */
    protected double z = 0;
    /**
     * Attributes
     */
    protected boolean sinkNode = false;
    /**
     *
     */
    protected Batery bateryEnergy = null;
    /**
     *
     */
    protected Application application;
    /**
     *
     */
    protected RoutingLayer routingLayer = null;
    /**
     *
     */
    protected MACLayer macLayer = null;
    /**
     *
     */
    protected Config config = new Config();
    /**
     *
     */
    protected boolean turnedOn = true;
    /**
     *
     */
    protected GraphicNode graphicNode;
    /**
     *
     */
    protected CPU cpu = null;
    /**
     *
     */
    protected Transceiver transceiver = null;
    /**
     *
     */
    protected Color messageColor = Color.BLUE;
    /**
     *
     */
    protected double environmentAttenuation = 0;
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
    /* is a source of messages node */
    private boolean source;
    private boolean receiver=false;

    /**
     *
     * @return
     */
    public boolean isSource() {
        return source;
    }

    /**
     *
     * @param b
     */
    public void setSource(boolean b) {
        source=b;
    }

    /**
     *
     * @param b
     */
    public void setReceiver(boolean b) {
        receiver=b;
    }

    /**
     *
     * @return
     */
    public boolean isReceiver() {
        return receiver;
    }

    /**
     *
     */
    public enum NodeState {

        /**
         *
         */
        ACTIVE,
        /**
         *
         */
        SLEEP
    };
    /**
     *
     */
    protected NodeState state;

    /**
     *
     */
    public class Config {

        /**
         *
         */
        public static final int DEFAULT_MAX_COMUNICATION_RANGE = 30;
        /**
         *
         */
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
        /**
         *
         * @return
         */
        public double getMaximumRadioStrength() {
            return maxRadioStrength;
        }

        /*
         * (non-Javadoc)
         *
         * @see net.tinyos.prowler.INode#setMaximumRadioStrength(double)
         */
        /**
         *
         * @param d
         */
        public void setMaximumRadioStrength(double d) {
            maxRadioStrength = d;
        }

        /**
         *
         * @return
         */
        public double getSetRadioRange() {
            return setRadioRange;
        }

        /**
         *
         * @param setRadioRange
         */
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

    /**
     *
     * @return
     */
    public boolean isSinkNode() {
        return sinkNode;
    }

    /**
     *
     * @param sinkNode
     */
    public void setSinkNode(boolean sinkNode) {
        this.sinkNode = sinkNode;
    }

    /**
     *
     */
    public void initEnergyConsumation() {
        if (enableFunctioningEnergyConsumption) {
            simulator.addEvent(new Node.EnergyWasteEvent((int) Simulator.randomGenerator.random().nextDouble() * CLOCK_TICK));
        }
    }

    /**
     *
     * @return
     */
    public boolean isEnableFunctioningEnergyConsumption() {
        return enableFunctioningEnergyConsumption;
    }

    /**
     *
     * @param enableFunctioningEnergyConsumption
     */
    public void setEnableFunctioningEnergyConsumption(boolean enableFunctioningEnergyConsumption) {
        this.enableFunctioningEnergyConsumption = enableFunctioningEnergyConsumption;
    }

    /**
     *
     * @return
     */
    public Object getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(Object message) {
        this.message = (Message) message;
    }

    /**
     *
     * @return
     */
    public MACLayer getMacLayer() {
        return macLayer;
    }

    /**
     *
     * @param macLayer
     */
    public void setMacLayer(MACLayer macLayer) {
        this.macLayer = macLayer;
    }

    /**
     *
     */
    public void initBatery() {
        initEnergyConsumation();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getDistanceSquare(net.tinyos.prowler.Node)
     */
    /**
     *
     * @param other
     * @return
     */
    public double getDistanceSquare(Node other) {
        return (getX() - other.getX()) * (getX() - other.getX()) + (getY() - other.getY()) * (getY() - other.getY())
                + (getZ() - other.getZ()) * (getZ() - other.getZ());
    }

    /**
     *
     * @param other
     * @return
     */
    public double getDistance(Node other) {
        return Math.sqrt(getDistanceSquare(other));
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#setId(short)
     */
    /**
     *
     * @param id
     */
    public final void setId(short id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#setPosition(double, double, double)
     */
    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getX()
     */
    /**
     *
     * @return
     */
    public double getX() {
        return this.x;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getY()
     */
    /**
     *
     * @return
     */
    public double getY() {
        return this.y;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getZ()
     */
    /**
     *
     * @return
     */
    public double getZ() {
        return this.z;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getId()
     */
    /**
     *
     * @return
     */
    public short getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getSimulator()
     */
    /**
     *
     * @return
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
    /**
     *
     * @param app
     */
    public void addApplication(Application app) {
        app.setNode(this);
        application = app;
    }

    /**
     * Visiting the elements of the application list, it returns the first with
     * the given application class.
     *
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
    /**
     *
     * @param disp
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

    /**
     *
     * @return
     */
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

    /**
     *
     * @param message
     * @param app
     * @return
     */
    public boolean sendMessageFromApplication(Object message, Application app) {
        return app.sendMessage(message);
    }

    /**
     *
     * @param message
     * @return
     */
    public boolean sendMessage(Object message) {
        return application.sendMessage(message);
    }

    /**
     *
     */
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

    /**
     *
     * @return
     */
    public GraphicNode getGraphicNode() {
        return graphicNode;
    }

    /**
     *
     * @param graphicNode
     */
    public void setGraphicNode(GraphicNode graphicNode) {
        this.graphicNode = graphicNode;
    }

    /**
     *
     * @return
     */
    public int getRadius() {
        return getGraphicNode().getRadius();
    }

    /**
     *
     * @param radius
     */
    public void setRadius(int radius) {
        getGraphicNode().setRadius(radius);
    }

    /**
     *
     */
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

    /**
     *
     */
    public void shutdown() {
        turnedOn = false;
    }

    /**
     *
     */
    public void powerOn() {
        if (!getBateryEnergy().off()) {
            turnedOn = true;
            initEnergyConsumation();
        }
    }

    /**
     *
     * @param application
     */
    public void setApplication(Application application) {
        this.application = application;
        application.setNode(this);
    }

    /**
     *
     * @return
     */
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
            "Routing Under Attack: " + getRoutingLayer().isUnderAttack(),
            "RSS: " + getMacLayer().getSignalStrength(),
            "MRS: " + getConfig().getMaximumRadioStrength(),};
        return nodeInfo;
    }

    /**
     *
     * @return
     */
    public CPU getCPU() {
        return cpu;
    }

    /**
     *
     * @param cpu
     */
    public void setCPU(CPU cpu) {
        this.cpu = cpu;
    }

    /**
     *
     */
    public static void resetCounter() {
        NODEID_AUTOCOUNTER = 1;
    }

    /**
     *
     * @return
     */
    public Transceiver getTransceiver() {
        return transceiver;
    }

    /**
     *
     * @param transceiver
     */
    public void setTransceiver(Transceiver transceiver) {
        this.transceiver = transceiver;
    }

    /**
     *
     * @param object
     * @return
     */
    public static Node cast(Object object) {
        return (Node) object;
    }

    /**
     *
     * @return
     */
    public NodeState getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(NodeState state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {
        return state == NodeState.ACTIVE;
    }

    /**
     *
     * @return
     */
    public boolean isSleep() {
        return state == NodeState.SLEEP;
    }

    /**
     *
     * @return
     */
    public Color getMessageColor() {
        return messageColor;
    }

    /**
     *
     * @param messageColor
     */
    public void setMessageColor(Color messageColor) {
        this.messageColor = messageColor;
    }

    /**
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     *
     * @param z
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     *
     * @return
     */
    public double getEnvironmentAttenuation() {
        return environmentAttenuation;
    }

    /**
     *
     * @param environmentAttenuation
     */
    public void setEnvironmentAttenuation(double environmentAttenuation) {
        this.environmentAttenuation = environmentAttenuation;
    }

    /**
     *
     * @return
     */
    public abstract Object getUniqueID();

    /**
     *
     */
    public void reset() {
        getGraphicNode().reset();
        setParentNode(null);
        getBateryEnergy().reset();
        getRoutingLayer().reset();
        getMacLayer().reset();
        getApplication().reset();
    }
}
