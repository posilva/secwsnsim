package pt.unl.fct.di.mei.securesim.network;

import pt.unl.fct.di.mei.securesim.core.Application;
import pt.unl.fct.di.mei.securesim.core.Display;
import pt.unl.fct.di.mei.securesim.core.Simulator;
import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.core.radio.RadioModel;
import pt.unl.fct.di.mei.securesim.core.radio.RadioModel.Neighborhood;

public interface INode {

	/**
	 * A getter method used by the RadioModels to manipulate neighborhood of nodes. 
	 */
	public abstract Neighborhood getNeighborhood();

	/**
	 * Calculates the square of the distance between two nodes. This method is 
	 * used by the radio models to calculate the fading of radio signals.
	 * 
	 * @param other The other node
	 * @return The square of the distance between this and the other node
	 */
	public abstract double getDistanceSquare(Node other);

	/**
	 * Returns the maximum radio strength this node will ever transmit with. 
	 * This must be a positive number.
	 * 
	 * @return the maximum transmit radio power
	 * @see Node#beginTransmission
	 */
	public abstract double getMaximumRadioStrength();

	/**
	 * A setter function for the {@link Node#maxRadioStrength}
	 * field.
	 * 
	 * @param d the desired new transmit strength of this mote
	 */
	public abstract void setMaximumRadioStrength(double d);

	/**
	 * Sends out a radio message. If the node is in receiving mode the sending is 
	 * postponed until the receive is finished. This method should behave
	 * exactly as the SendMsg.send command in TinyOS.
	 * 
	 * @param message the message to be sent
	 * @param app the application sending the message
	 * @return If the node is in sending state it returns false otherwise true.
	 */
	public abstract boolean sendMessage(Object message, Application app);

	/**
	 * Sets the id of the node. It is allowed that two nodes have the
	 * same id for experimentation.
	 * 
	 * @param id the new id of the node.
	 */
	public abstract void setId(short id);

	/**
	 * Sets the position of the mote in space. Please call the 
	 * {@link RadioModel#updateNeighborhoods} to update the network topology 
	 * information before starting the simulation.
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param z the z position
	 */
	public abstract void setPosition(double x, double y, double z);

	/**
	 * A getter function for position X.
	 * 
	 * @return Returns the x coordinate of the node.
	 */
	public abstract double getX();

	/**
	 * A getter function for position Y.
	 * 
	 * @return Returns the y coordinate of the node.
	 */
	public abstract double getY();

	/**
	 * A getter function for position Z.
	 * 
	 * @return Returns the z coordinate of the node.
	 */
	public abstract double getZ();

	public abstract short getId();

	/**
	 * @return simply returns the simulator in which this Node exists
	 */
	public abstract Simulator getSimulator();

	/**
	 * This function is part of the application management. Adds an 
	 * {@link Application} to the list of applications running on this Node.
	 * Note that applications on a node represent TinyOS components, so do not 
	 * try to solve all your problems in a derived class of Node. Also note
	 * that there can be only one instance of an Application class running on 
	 * every Node, unlike components in TinyOS! Yes, this is a reasonable
	 * constraint that makes message demultiplexing easier.   
	 * 
	 * @param app the Application 
	 */
	public abstract void addApplication(Application app);

	/**
	 * The default implementation of the display function, calls the display 
	 * funtions of the member applications.
	 * 
	 * @param disp the main display provided by the Simulator
	 */
	public abstract void display(Display disp);

	public abstract void init();

	public abstract void dispose();

}