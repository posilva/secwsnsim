package org.wisenet.simulator.temp.insens.basestation.dijkstra.model;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge){
            return (((Edge)obj).source == source &&  ((Edge)obj).destination == destination && ((Edge)obj).weight == weight );
        }else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.source;
        hash = 17 * hash + this.destination;
        hash = 17 * hash + this.weight;
        return hash;
    }


}