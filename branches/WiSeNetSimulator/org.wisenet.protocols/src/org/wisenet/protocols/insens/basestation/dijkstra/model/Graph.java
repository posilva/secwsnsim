package org.wisenet.protocols.insens.basestation.dijkstra.model;

import java.util.List;

/**
 *
 * @author posilva
 */
public class Graph {
	private final List<Short> vertexes;
	private final List<Edge> edges;

        /**
         *
         * @param vertexes
         * @param edges
         */
        public Graph(List<Short> vertexes, List<Edge> edges) {
		this.vertexes = vertexes;
		this.edges = edges;
	}

        /**
         *
         * @return
         */
        public List<Short> getVertexes() {
		return vertexes;
	}

        /**
         *
         * @return
         */
        public List<Edge> getEdges() {
		return edges;
	}

}
