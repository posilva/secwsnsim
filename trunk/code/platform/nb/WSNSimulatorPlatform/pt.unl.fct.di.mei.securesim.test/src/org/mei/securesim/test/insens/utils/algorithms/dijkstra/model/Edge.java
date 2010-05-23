package org.mei.securesim.test.insens.utils.algorithms.dijkstra.model;

public class Edge {
	private final short source;
	private final short destination;
	private final int weight;

	public Edge(short source, short destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
	public Edge(short source, short destination) {
		this.source = source;
		this.destination = destination;
		this.weight = 1;
	}

	public short getDestination() {
		return destination;
	}

	public short getSource() {
		return source;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return source + " " + destination;
	}

}