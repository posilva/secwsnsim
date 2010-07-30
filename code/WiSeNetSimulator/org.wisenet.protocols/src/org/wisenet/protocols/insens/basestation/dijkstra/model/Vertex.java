package org.wisenet.protocols.insens.basestation.dijkstra.model;

public class Vertex {

    final private short id;

    public Vertex(short id) {
        this.id = id;
    }

    public short getId() {
        return Short.valueOf(id);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vertex other = (Vertex) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		return true;
        return id == other.id;

    }

    @Override
    public String toString() {
        return "" + id;
    }
}
