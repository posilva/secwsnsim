package org.mei.securesim.components.instruments;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class LatencyController {

    protected boolean enable;
    protected static LatencyController instance = null;
    Hashtable<Node, Vector<LatencyEntry>> latencyControlTable = new Hashtable<Node, Vector<LatencyEntry>>();
    protected HashSet<Node> senders= new HashSet<Node>();
    protected HashSet<Node> receivers= new HashSet<Node>();

    public void notifyMessageSent(Object message, Node node) {
    
    }

    public void notifyMessageReception(Object message, Node node) {
    
    }

    /**
     * Table Entry to update values
     */
    class LatencyEntry {

        Node destNode;
        long time;

        public LatencyEntry(Node destNode, long time) {
            this.destNode = destNode;
            this.time = time;
        }

        public Node getDestNode() {
            return destNode;
        }

        public void setDestNode(Node destNode) {
            this.destNode = destNode;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }

    public static LatencyController getInstance() {
        if (instance == null) {
            instance = new LatencyController();
        }
        return instance;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public  void registerSender(Node node){
        if (!senders.contains(node)){
            senders.add(node);
            latencyControlTable.put(node, new Vector<LatencyEntry>());
        }
    }
    
    public  void registerReceiver(Node node){
        if(!receivers.contains(node))
            receivers.add(node);
    }


    

}
