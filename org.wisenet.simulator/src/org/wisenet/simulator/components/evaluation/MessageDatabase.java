package org.wisenet.simulator.components.evaluation;

import java.util.Hashtable;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;

/**
 *
 * @author posilva
 */
public class MessageDatabase {

    Hashtable<Object, Object> messagesTable = new Hashtable<Object, Object>();
    Hashtable<Object, Object> senderNodesTable = new Hashtable<Object, Object>();
    Hashtable<Object, Object> receiverNodesTable = new Hashtable<Object, Object>();
    long totalNumberOfMessagesSent = 0;
    protected boolean debugEnabled = true;

    /**
     * Registers a message sent event
     * @param message
     *              the message sent
     * @param node
     *              the sender node
     */
    public synchronized void registerMessageSent(Message message, RoutingLayer routing) {
        totalNumberOfMessagesSent++;
        /* if message source id is the same as the sender then process */
        if (message.getSourceId().equals(routing.getUniqueId())) {
            /* if message not allready processed */
            if (!messagesTable.keySet().contains(message.getUniqueId())) {
                log("Message " + message.getUniqueId() + " Sent by " + message.getSourceId() + " to " + message.getDestinationId());
                /* process message */
                messagesTable.put(message.getUniqueId(), new MessageTableEntry(message, routing));
                /* if senders aren't processed then */
                if (!senderNodesTable.keySet().contains(routing.getUniqueId())) {

                    /*process sender*/
                    senderNodesTable.put(routing.getUniqueId(), new SendersTableEntry());
                    log("Sender" + routing.getUniqueId() + " registered");
                }
            }
        }
        // senão descarta mensagem
    }

    /**
     * Registers a succeeded received message
     * @param message
     *              the message received
     * @param node
     *              the receiver node
     */
    public synchronized void registerMessageReceived(Message message, RoutingLayer routing) {

        if (message.getDestinationId().equals(routing.getUniqueId())) {
            if (!receiverNodesTable.contains(routing.getUniqueId())) {
                receiverNodesTable.put(routing.getUniqueId(), new ReceiversTableEntry()); // TODO: NOT NULL
            }

            MessageTableEntry messageEntry = (MessageTableEntry) messagesTable.get(message.getUniqueId());
            if (messageEntry != null) {
                messageEntry.arrived();
                log("MESSAGE " + message.getUniqueId() + " takes " + message.getTotalHops() + " HOPS");
                messageEntry.incrementCounter();
                RoutingLayer senderId = messageEntry.getSenderRouting();
                SendersTableEntry senderEntry = (SendersTableEntry) senderNodesTable.get(senderId.getUniqueId());
                if (senderEntry != null) {
                    senderEntry.arrived();
                }
            }
        }
    }

    public synchronized int getTotalSenderNodes() {
        return senderNodesTable.size();
    }

    public synchronized int getTotalCoveredNodes() {
        int t = 0;
        for (Object object : senderNodesTable.values()) {
            SendersTableEntry e = (SendersTableEntry) object;
            if (e.isArrived()) {
                t++;
            }
        }
        return t;
    }

    public synchronized long getTotalMessagesReceived() {
        long t = 0;
        for (Object object : messagesTable.values()) {
            MessageTableEntry e = (MessageTableEntry) object;
            if (e.isArrived()) {
                t++;
            }
        }
        return t;
    }

    public synchronized long getTotalNumberOfUniqueMessagesSent() {
        return messagesTable.size();
    }

    protected void log(String msg) {
        if (debugEnabled) {
            System.out.println(getClass().getSimpleName() + " - " + msg);
        }
    }

    // reliability control
    public class MessageTableEntry {

        boolean arrived = false;
        int count = 0;
        long simulationTimeSent = 0L;
        long simulationTimeReceived = 0L;
        private final Message message;
        private final RoutingLayer senderRouting;

        private MessageTableEntry(Message message, RoutingLayer routing) {
            this.message = message;
            this.senderRouting = routing;
        }

        public RoutingLayer getSenderRouting() {
            return senderRouting;
        }

        public boolean isArrived() {
            return arrived;
        }

        public void arrived() {
            this.arrived = true;
        }

        public Message getMessage() {
            return message;
        }

        public int getCount() {
            return count;
        }

        public void incrementCounter() {
            count++;
        }
    }
// coverage control

    public class SendersTableEntry {

        boolean arrived = false;

        public SendersTableEntry() {
        }

        public boolean isArrived() {
            return arrived;
        }

        public void arrived() {
            this.arrived = true;
        }
    }

    public class ReceiversTableEntry {

        public ReceiversTableEntry() {
        }
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public long getTotalNumberOfMessagesSent() {
        return totalNumberOfMessagesSent;
    }
}
