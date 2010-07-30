/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.instruments;

import org.wisenet.simulator.core.nodes.Node;

/**
 *
 * @author CIAdmin
 */
public interface NodeSelectionCondition {

    public boolean select(Node node);
}
