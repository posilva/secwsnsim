/**
 * 
 */
package org.mei.securesim.protocols.flooding;

import java.util.HashSet;
import org.mei.securesim.components.instruments.coverage.CoverageController;
import org.mei.securesim.components.instruments.latency.LatencyController;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.energy.EnergyConsumptionAction;
import org.mei.securesim.core.engine.BaseMessage;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.flooding.messages.FloodingMessage;
import org.mei.securesim.test.flooding.messages.LatencyTestMessage;
import org.mei.securesim.test.flooding.messages.PartialCoverageTestMessage;
import org.mei.securesim.test.flooding.messages.TotalCoverageTestMessage;
import org.mei.securesim.test.flooding.tests.FloodingComparator;

/**
 * @author posilva
 *
 */
public class FloodingRoutingLayer extends RoutingLayer {

    protected HashSet<Long> receivedMessages = new HashSet<Long>();

    public FloodingRoutingLayer() {
        super();
    }

    /**
     * Stores the sender from which it first receives the message, and passes
     * the message.
     */
    @Override
    public void receiveMessage(Object message) {

        final BaseMessage msg = (BaseMessage) message;
        getNode().getCPU().execute(new EnergyConsumptionAction() {

            /**
             * 
             */
            public void execute() {

                FloodingMessage m = (FloodingMessage) msg;
                if (!receivedMessages.contains(m.getMessageNumber())) {
                    receivedMessages.add(m.getMessageNumber());


                    if (m.getDestin() == (int) getNode().getId()) {
                        Application app = getNode().getApplication();
                        if (app != null) {
                            app.receiveMessage(msg);
                        }
                    } else {
                        getNode().getMacLayer().sendMessage(msg, FloodingRoutingLayer.this);
                    }
                }
            }

            public int getNumberOfUnits() {
                return msg.size();
            }
        });
    }

    @Override
    public boolean sendMessage(Object message, Application app) {
        System.out.println("Message Number: " + ((BaseMessage) message).getMessageNumber());
        application = app;
        receivedMessages.add(((BaseMessage) message).getMessageNumber());
        return getNode().getMacLayer().sendMessage(message, this);
    }

    /**
     * Sets the sent flag to true.
     */
    @Override
    public void sendMessageDone() {
        if (application != null) {
            application.sendMessageDone();
        }
        application = null;
    }

    @Override
    public void setup() {
        /**
         * configure the coverage controller
         */
        if (CoverageController.getInstance().getTotalCoverageTestMessageClass() == null) {
            CoverageController.getInstance().setTotalCoverageMessageClass(TotalCoverageTestMessage.class);
        }
        if (CoverageController.getInstance().getPartialCoverageMessageClass() == null) {
            CoverageController.getInstance().setPartialCoverageMessageClass(PartialCoverageTestMessage.class);
        }

        if (CoverageController.getInstance().getNodeIdComparator() == null) {
            CoverageController.getInstance().setNodeIdComparator(new FloodingComparator());
        }
        
         if (LatencyController.getInstance().getLatencyMessageClass() == null) {
            LatencyController.getInstance().setLatencyMessageClass(LatencyTestMessage.class);
        }
    }

    @Override
    public void routeMessage(Object message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
