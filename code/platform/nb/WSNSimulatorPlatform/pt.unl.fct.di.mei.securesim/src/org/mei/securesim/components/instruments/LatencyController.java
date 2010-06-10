package org.mei.securesim.components.instruments;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;
import org.mei.securesim.core.nodes.Node;

/********************************************************
 *  INSTRUMENTO DE MEDICAO DE LATENCIA NUMA RSSF
 *
 *  Existem dois modos de medição de latencia numa rede:
 *
 * 1. Tempo de simulação ou tempo real (depende da metrica que se pretende seguir)
 *
 * 2. Medição do numero de hops percorridos
 *
 *
 * Para o 1º Caso:
 *
 * Implementação semelhante à da cobertura que vai calculando 3 dados estatisticos
 * valor minimo, maximo e média do tempo para percorrer um caminho entre o no A e B
 *
 * A implementação assenta na notificação do envio de uma mensagem e o registo do tempo
 * a quando da recepção é calculado a diferenca de tempo
 *
 *
 * Para o 2º Caso:
 *
 * EM cada envio de mensagens é verificado o identificador das mensagens e com isto
 * é contabilizado o numero de hops até que o destino notifique a sua chegada
 *
 * no final é apresentado a latencia máxima, minima e média e poderá ser poderada
 * pela distancia fisica entre os dois pontos. Desta forma corrige-se os valores
 * menores provenientes de nós próximos.s
 *
 */


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
