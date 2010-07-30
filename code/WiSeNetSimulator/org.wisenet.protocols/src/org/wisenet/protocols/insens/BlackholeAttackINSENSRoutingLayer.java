package org.wisenet.protocols.insens;

/**
 *
 * @author CIAdmin
 */
public class BlackholeAttackINSENSRoutingLayer extends INSENSRoutingLayer {

    @Override
    public void onRouteMessage(Object message) {
        if (!isStable()) {
            super.onRouteMessage(message);
        }

    }
}
