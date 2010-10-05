/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.evaluation;

import java.util.Hashtable;
import org.wisenet.simulator.components.evaluation.tests.TestMessage;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author posilva
 */
public class MessageDatabase {

    Hashtable<Long, Object> messagesTable = new Hashtable<Long, Object>();
    Hashtable<Short, Object> senderNodesTable = new Hashtable<Short, Object>();
    Hashtable<Short, Object> receiverNodesTable = new Hashtable<Short, Object>();

    /**
     * Registers a message sent event
     * @param message
     *              the message sent
     * @param node
     *              the sender node
     */
    public void registerMessageSent(TestMessage message, Node node) {

        if (message.getSourceId().equals(node.getUniqueID())) {
            if (!messagesTable.contains(message.getMessageNumber())) {
                messagesTable.put(message.getMessageNumber(), null); //TODO: NOT NULL
            }
            if (!senderNodesTable.contains(node.getId())) {
                senderNodesTable.put(node.getId(), null); // TODO: NOT NULL
            }

        }

        // se a mensagem tem origem no NO que a envia
        // então regista o envio se esta mensagem ainda não existe

        // efectua processamento de fiabilidade
        // efectua processamento de cobertura
        // efectua processamento de latencia
        // efectua processamento de energia




        // senão descarta mensagem



    }

    /**
     * Registers a succeeded received message
     * @param message
     *              the message received
     * @param node
     *              the receiver node
     */
    public void registerMessageReceived(TestMessage message, Node node) {
        // Se a mensagem é destinada ao nó que a recebe
        // então regista chegada
        // efectua processamento de fiabilidade

        // efectua processamento de cobertura
        // efectua processamento de latencia
        // efectua processamento de energia

        // senao descarta mensagem
        if (message.getDestinationId().equals(node.getUniqueID())) {
            if (!receiverNodesTable.contains(node.getId())) {
                receiverNodesTable.put(node.getId(), null); // TODO: NOT NULL
            }
        }
    }
}
