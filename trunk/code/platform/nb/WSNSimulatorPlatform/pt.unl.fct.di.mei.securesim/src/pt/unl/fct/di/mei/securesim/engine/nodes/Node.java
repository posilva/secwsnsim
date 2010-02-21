package pt.unl.fct.di.mei.securesim.engine.nodes;

import java.util.HashMap;
import java.util.Map;

import pt.unl.fct.di.mei.securesim.engine.Application;
import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.energy.Batery;
import pt.unl.fct.di.mei.securesim.engine.layers.RoutingLayer;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel.Neighborhood;
import pt.unl.fct.di.mei.securesim.ui.GraphicNode;

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


    protected Batery bateryEnergy = new Batery();
    protected Map<Class, Application> applications = new HashMap<Class, Application>();
    protected RoutingLayer routingLayer = null;
    protected Config config = new Config();
    protected boolean turnedOn = true;
    protected long lastTransmissionTime;
    protected GraphicNode graphicNode;
    protected boolean receive = true;
    protected CPUStatus cpuStatus=CPUStatus.ON;
    protected TXStatus txStatus=TXStatus.ON;


    public enum CPUStatus {

        ON, OFF
    };

    public enum TXStatus {

        ON, OFF
    };

    public boolean canReceive() {
        return txStatus==TXStatus.ON;
    }

    public void startTransmissionTime() {
        lastTransmissionTime = System.currentTimeMillis();
    }

    public void endTransmissionTime() {
        bateryEnergy.consume(.0000010);
        lastTransmissionTime = System.currentTimeMillis() - lastTransmissionTime;
    }

    public class Config {

        public static final int MAX_RADIO_STRENGTH = 350;
        /**
         * This field defines the relative strength of a mote. If it is set to a
         * high value for a given mote it can supress other motes.
         */
        double maxRadioStrength = MAX_RADIO_STRENGTH;

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

    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#getNeighborhood()
     */
    public Neighborhood getNeighborhood() {
        return neighborhood;
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

    /**
     * Called by the drived class implementing the MAC layer when radio
     * transmission is initiated. This method will call the
     * {@link Node#receptionBegin} method in each of the neighboring nodes with
     * the same <code>stream</code> object but with a diminished radio signal
     * strength. Derived classes must avoid nested transmissions.
     *
     * @param strength
     *            The signal strength of the transmission. This must be positive
     *            and less than or equal to the maximum transmit strength.
     * @param stream
     *            The object that is beeing sent. This parameter cannot be
     *            <code>null</code> and two nodes cannot send the same object at
     *            the same time.
     * @see Node#getTransmitStrengthMultiplicator
     */
    protected final void beginTransmission(double strength, Object stream) {
        neighborhood.beginTransmission(strength, stream);
    }

    /**
     * Called by the derived class implementing the MAC layer when radio
     * transmission is finished. This method will call the
     * {@link Node#receptionEnd} method in each of the neighboring nodes with
     * the same <code>stream</code> object but with a diminished radio strength.
     * Derived classes must make sure that this method is invoked only once for
     * each matching {@link Node#beginTransmission} call.
     */
    protected final void endTransmission() {
        neighborhood.endTransmission();
    }

    /**
     * Called for each transmission of a neighboring node by the radio model.
     * The <code>recpetionBegin</code> and <code>receptionEnd</code> calles can
     * be nested or interleaved, but they are always coming in pairs. The
     * derived class implementing the MAC protocol must select the transmission
     * that it wants to receive based on some heuristics on the radio signal
     * stregths. Note that these methods are called even when the nodes is
     * currently transmitting.
     *
     * @param strength
     *            The radio strength of the incoming signal.
     * @param stream
     *            The object representing the incoming data. This stream object
     *            is never <code>null</code>.
     * @see #receptionEnd
     */
    public abstract void receptionBegin(double strength, Object stream);

    /**
     * Called for each transmission of a neighboring node by the radio model.
     * This method is always invoked after a corresponding
     * {@link #receptionBegin} method invokation with the exact same parameters.
     *
     * @param strength
     *            The radio strength of the incoming signal.
     * @param stream
     *            The received object message.
     */
    public abstract void receptionEnd(double strength, Object stream);

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#sendMessage(java.lang.Object,
     * net.tinyos.prowler.Application)
     */
    public abstract boolean sendMessage(Object message, RoutingLayer layer);

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
        getGraphicNode().setZ((int)z);
        
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
        if (!applications.containsKey(app.getClass())) {
            app.setNode(this);
            applications.put(app.getClass(), app);
        }
    }

    /**
     * Visiting the elements of the application list, it returns the first with
     * the given application class.
     *
     * @param appClass
     *            the class that identifies the needed application for us
     * @return Returns the application instance running on this node
     */
    public Application getApplication(Class appClass) {
        return applications.get(appClass);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#display(net.tinyos.prowler.Display)
     */
    public void displayOn(ISimulationDisplay disp) {
        for (Application app : applications.values()) {
            app.display(disp);
        }
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
        // TODO Auto-generated method stub
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

    public abstract void init();

    public void dispose() {
        // TODO Auto-generated method stub
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
}
