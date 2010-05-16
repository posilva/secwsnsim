/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.utils.graph;

/**
 *
 * @author CIAdmin
 */
  public class Edge {

        public final Vertex target;
        public final double weight;

        public Edge(Vertex argTarget, double argWeight) {
            target = argTarget;
            weight = argWeight;
        }

    public Edge(Vertex target) {

        this.target = target;
        this.weight=1;
    }

    }