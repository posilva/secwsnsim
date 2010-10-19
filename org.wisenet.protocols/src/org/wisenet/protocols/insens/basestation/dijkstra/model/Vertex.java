package org.wisenet.protocols.insens.basestation.dijkstra.model;

/**
 *
 * @author posilva
 */
public class Vertex {

    final private short id;

    /**
     *
     * @param id
     */
    public Vertex(short id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public short getId() {
        return Short.valueOf(id);
    }


    /**
     *
     * @param obj
     * @return
     */
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "" + id;
    }
}
