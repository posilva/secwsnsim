package org.wisenet.protocols.insens.basestation.dijkstra.model;

import java.util.List;

public class Graph {
	private final List<Short> vertexes;
	private final List<Edge> edges;

	public Graph(List<Short> vertexes, List<Edge> edges) {
		this.vertexes = vertexes;
		this.edges = edges;
	}

	public List<Short> getVertexes() {
		return vertexes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

}
