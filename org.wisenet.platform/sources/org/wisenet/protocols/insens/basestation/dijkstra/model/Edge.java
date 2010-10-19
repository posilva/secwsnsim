package org.wisenet.protocols.insens.basestation.dijkstra.model;

/**
 *
 * @author posilva
 */
public class Edge {
	private final short source;
	private final short destination;
	private final int weight;

        /**
         *
         * @param source
         * @param destination
         * @param weight
         */
        public Edge(short source, short destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
        /**
         *
         * @param source
         * @param destination
         */
        public Edge(short source, short destination) {
		this.source = source;
		this.destination = destination;
		this.weight = 1;
	}

        /**
         *
         * @return
         */
        public short getDestination() {
		return destination;
	}

        /**
         *
         * @return
         */
        public short getSource() {
		return source;
	}

        /**
         *
         * @return
         */
        public int getWeight() {
		return weight;
	}

        /**
         *
         * @return
         */
        @Override
	public String toString() {
		return source + " " + destination;
	}

        /**
         *
         * @param obj
         * @return
         */
        @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge){
            return (((Edge)obj).source == source &&  ((Edge)obj).destination == destination && ((Edge)obj).weight == weight );
        }else
            return false;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.source;
        hash = 17 * hash + this.destination;
        hash = 17 * hash + this.weight;
        return hash;
    }


}