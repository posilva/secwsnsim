/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.protocols.insens;

import org.wisenet.protocols.insens.messages.INSENSMessage;
import org.wisenet.protocols.insens.messages.data.INSENSMessagePayload;
import org.wisenet.protocols.insens.utils.OneWaySequenceNumbersChain;
import org.wisenet.simulator.utilities.CryptoFunctions;
import org.wisenet.simulator.core.energy.EnergyConsumptionAction;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author CIAdmin
 */
public class INSENSFunctions {

    protected static OneWaySequenceNumbersChain sequenceNumbersChain = new OneWaySequenceNumbersChain(INSENSConstants.CHAIN_SIZE);

    /**
     * Gets the initial sequence number from one way hash chain
     * @return
     */
    public static long getInitialSequenceNumber() {
        return sequenceNumbersChain.get(0);
    }

    /**
     * Gets the next number from de sequence of one way hash chain numbers
     * @return
     */
    public static long getNextOWS() {
        return sequenceNumbersChain.getNextSequenceNumber();
    }

    /**
     * Helper function for extract type  from message
     * @param m
     * @return
     */
    public static byte getMessageType(INSENSMessage m) throws INSENSException {
        INSENSMessagePayload payload = new INSENSMessagePayload(m.getPayload());
        return payload.type;
    }

    public static boolean verifyMAC(byte[] payload, byte type) {
        switch (type) {
            case INSENSConstants.MSG_ROUTE_REQUEST:

                break;
            case INSENSConstants.MSG_FEEDBACK:
                break;
            case INSENSConstants.MSG_ROUTE_UPDATE:
                break;
            case INSENSConstants.MSG_DATA:
                break;
            default:
                return false;
        }
        return true;
    }

    public static byte[] decryptData(Node node, final byte[] data, final byte[] key) {
        EnergyConsumptionAction action = new EnergyConsumptionAction() {

            public void execute() {
                byte[] decipherText = data;//CryptoFunctions.decipherData(data, key, INSENSConstants.globalIV);
                setResult(decipherText);
            }

            public int getNumberOfUnits() {
                return data.length;
            }
        };
        node.getCPU().executeDecryption(action);
        return (byte[]) action.getResult();
    }

    public static byte[] encryptData(Node node, final byte[] data, final byte[] key) {
        EnergyConsumptionAction action = new EnergyConsumptionAction() {

            public void execute() {
                byte[] cipherText = data;//CryptoFunctions.cipherData(data, key, INSENSConstants.globalIV);
                setResult(cipherText);
            }

            public int getNumberOfUnits() {
                return data.length;
            }
        };
        node.getCPU().executeEncryption(action);
        return (byte[]) action.getResult();
    }

    public static boolean verifyMAC(Node node, final byte[] data, final byte[] mac, final byte[] key) {
        EnergyConsumptionAction action = new EnergyConsumptionAction() {

            public void execute() {
                boolean b = CryptoFunctions.verifyMessageIntegrityMAC(data, mac, key);
                setResult(b);
            }

            public int getNumberOfUnits() {
                return data.length;
            }
        };
        node.getCPU().executeVerifySignature(action);
        return (Boolean) action.getResult();
    }

    public static byte[] createMAC(final byte[] data, final byte[] key, Node node) {
        EnergyConsumptionAction action = new EnergyConsumptionAction() {

            public void execute() {
                byte[] mac = CryptoFunctions.createMAC(data, key);
                setResult(mac);
            }

            public int getNumberOfUnits() {
                return data.length;
            }
        };
        node.getCPU().executeSignature(action);
        return (byte[]) action.getResult();


    }
}
