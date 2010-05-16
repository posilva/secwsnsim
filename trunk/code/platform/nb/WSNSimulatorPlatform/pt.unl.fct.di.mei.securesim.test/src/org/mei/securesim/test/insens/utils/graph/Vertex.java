/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.utils.graph;

/**
 *
 * @author CIAdmin
 */
public class Vertex implements Comparable<Vertex> {

        public final String name;
        public Edge[] adjacencies;
        public double minDistance = Double.POSITIVE_INFINITY;
        public Vertex previous;

        public Vertex(String argName) {
            name = argName;
        }

    @Override
        public String toString() {
            return name;
        }

        public int compareTo(Vertex other) {
            return Double.compare(minDistance, other.minDistance);
        }
    }
