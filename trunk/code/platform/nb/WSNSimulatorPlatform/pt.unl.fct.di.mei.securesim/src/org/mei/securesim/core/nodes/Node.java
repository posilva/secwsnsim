package org.mei.securesim.core.nodes;

import org.mei.securesim.core.SimulationObject;
import java.text.DecimalFormat;
import org.mei.securesim.cpu.NodeCPU;
import org.mei.securesim.core.Application;
import org.mei.securesim.core.Event;
import org.mei.securesim.core.ISimulationDisplay;
import org.mei.securesim.core.Simulator;
import org.mei.securesim.core.energy.Batery;
import org.mei.securesim.core.layers.MACLayer;
import org.mei.securesim.core.layers.RoutingLayer;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.core.radio.RadioModel.Neighborhood;
import org.mei.securesim.ui.GraphicNode;

/**
 * This class is the base class of all nodes. Nodes are entities in a simulator
 * which act on behalf of themselves, they also have some basic attributes like
 * location. Nodes also take part in radio transmissions, they initiate
 * transmissions and receive incomming radio messages.
 * 
 * @author Gabor Pap, Gyorgy Balogh, Miklos Maroti
 */
@SuppressWarnings("unchecked")
public abstract class Node extends SimulationObject {

    public static int NODEID_AUTOCOUNTER = 1;
    public static final int INITIAL_BATERY_POWER = 1000;
    public static final double DEFAULT_POWER_CONSUMING = 1.0E-2;
    private static long CLOCK_TICK = Simulator.ONE_SECOND;// /100;
    protected boolean sinkNode = false;
    protected Batery bateryEnergy = null;
    protected Application application;
    protected RoutingLayer routingLayer = null;
    protected MACLayer macLayer = null;
    protected Config config = new Config();
    protected boolean turnedOn = true;
    protected long lastTransmissionTime;
    protected GraphicNode graphicNode;
    protected boolean receive = true;
    protected NodeCPU cpu = null;
    protected CPUStatus cpuStatus = CPUStatus.ON;
    protected TXStatus txStatus = TXStatus.ON;
    /**
     * This is the message being sent, on reception it is extracted and the
     * message part is forwarded to the appropriate application, see
     * {@link Application#receiveMessage}.
     */
    protected Object message;

    public enum CPUStatus {

        ON, OFF
    };

    public enum TXStatus {

        ON, OFF
    };

    public boolean isSinkNode() {
        return sinkNode;
    }

    public void setSinkNode(boolean sinkNode) {
        this.sinkNode = sinkNode;
    }

    public void initEnergyConsumation() {
       // simulator.addEvent(new Node.EnergyWasteEvent((int) Simulator.random.nextDouble() * CLOCK_TICK));
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public MACLayer getMacLayer() {
        return macLayer;
    }

    public void setMacLayer(MACLayer macLayer) {
        this.macLayer = macLayer;
    }

    public boolean canReceive() {
        return txStatus == TXStatus.ON;
    }

    public void startTransmissionTime() {
        lastTransmissionTime = System.currentTimeMillis();
    }

    public long endTransmissionTime() {
        bateryEnergy.consume(.0000010);
        lastTransmissionTime = System.currentTimeMillis() - lastTransmissionTime;
        return lastTransmissionTime;
    }

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
            setMaximumRadioStrength((DEFAULT_MAX_RADIO_STRENGTH * setRadioRange) / DEFAULT_MAX_COMUNICATION_RANGE);
            this.setRadioRange = setRadioRange;
        }
    }
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
    private Node parentNode;

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
        this.graphicNode.setPhysicalNode(this);
        this.bateryEnergy = new Batery();
        this.cpu = new NodeCPU(this);
        this.bateryEnergy.setHostNode(this);
        setId((short) NODEID_AUTOCOUNTER++);
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
    public void setId(short id) {
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
    public void displayOn(ISimulationDisplay disp) {
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

    public abstract void init();

    public void dispose() {
    }

    /**
     * @return the lastTransmissionTime
     */
    public double getLastTransmissionTime() {
        return lastTransmissionTime;
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
                    getBateryEnergy().consume(DEFAULT_POWER_CONSUMING);
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
        return new String[]{"ID: " + getId(),
                    "Sink:  " + (isSinkNode() ? "True" : "False"),
                    "Position:  (" + (int) getX() + "," + (int) getY() + "," + (int) getZ() + ")",
                    "Node:  " + this.getClass().getSimpleName(),
                    "Application:  " + this.getApplication().getClass().getSimpleName(),
                    "Routing:  " + this.getRoutingLayer().getClass().getSimpleName(),
                    "",
                    "Remaining Power:  " + twoPlaces.format(remainingPower <= 0.0 ? 0 : remainingPower) + "%"
                };


    }

    public NodeCPU getCPU() {
        return cpu;
    }

    public void setCPU(NodeCPU cpu) {
        this.cpu = cpu;
    }

    public static void resetCouter() {
        NODEID_AUTOCOUNTER = 1;
    }
}
