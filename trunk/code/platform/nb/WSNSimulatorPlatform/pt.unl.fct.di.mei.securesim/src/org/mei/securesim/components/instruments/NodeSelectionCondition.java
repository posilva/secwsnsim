/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments;

import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author CIAdmin
 */
public interface NodeSelectionCondition {

    public boolean select(Node node);
}
