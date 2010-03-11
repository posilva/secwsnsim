package org.mei.securesim.core.energy;

/**
 *
 * @author posilva
 */
public class StateController {

    public enum CPUStatus {

        ACTIVE, IDLE, OFF
    };

    public enum TXStatus {

        TRANSMITING, RECEIVING, IDLE, OFF
    };
    NodeState lastNodeState;
    NodeState currentNodeState;

    void switchToState(NodeState state) {
        lastNodeState = currentNodeState;
        currentNodeState = state;
    }

    public NodeState getLastNodeState() {
        return lastNodeState;
    }

    public class NodeState {

        CPUStatus cpuStatus;
        TXStatus txStatus;
        long startTime;
        long endTime;

        public NodeState(CPUStatus cpuStatus, TXStatus txStatus) {
            this.cpuStatus = cpuStatus;
            this.txStatus = txStatus;

        }

        void start() {
            startTime = System.nanoTime();
        }

        void end() {
            endTime = System.nanoTime();
        }

        long getDuration() {
            return endTime - startTime;
        }
    }
}
