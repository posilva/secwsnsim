package org.mei.securesim.components.instruments.latency;

import java.util.HashSet;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.core.engine.Simulator;
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
    protected HashSet<Node> senders = new HashSet<Node>();
    protected HashSet<Node> receivers = new HashSet<Node>();
    protected Class latencyMessageClass;
    /**
     * List of the messages sent
     */
    protected HashSet listOfMessagesSent;
    protected LatencyStatisticsTable tableStatistic = new LatencyStatisticsTable();

    public void notifyMessageSent(Object message, Node node) {
        if (message instanceof ILatencyMessage) {
            tableStatistic.messageSent((ILatencyMessage) message);
        }
    }

    public void notifyMessageReception(Object message, Node node) {
        if (message instanceof ILatencyMessage) {
            if (node instanceof ILatencyHandler) {
                tableStatistic.messageReceivedBy((ILatencyMessage) message, (ILatencyHandler) node);
            }
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

    public void registerSender(Node node) {
        if (!senders.contains(node)) {
            senders.add(node);
        }
    }

    public void registerReceiver(Node node) {
        if (!receivers.contains(node)) {
            receivers.add(node);
        }
    }

    public void startAnalysis() {
        Node s1 = SimulationController.getInstance().getSimulation().getSimulator().getNetwork().getNodeDB().randomNode();
        Node s2 = SimulationController.getInstance().getSimulation().getSimulator().getNetwork().getNodeDB().randomNode();
        Node s3 = SimulationController.getInstance().getSimulation().getSimulator().getNetwork().getNodeDB().randomNode();
        Node r1 = SimulationController.getInstance().getSimulation().getSimulator().getNetwork().getNodeDB().randomNode();
        senders.add(s1);
        senders.add(s2);
        senders.add(s3);
        receivers.add(r1);
        Simulator sim = SimulationController.getInstance().getSimulation().getSimulator();
        
        int ct =0;
        for (Node node : senders) {
             LatencyEvent le= new LatencyEvent();
             le.setSourceNode(node);
             le.setDestinationNode(r1);
             le.setMessageUniqueId(""+ct);
             long time=ct*Simulator.ONE_SECOND*3+sim.getSimulationTime();
             le.setTime(time);
             ct++;
             le.setMessageClass(latencyMessageClass);
            sim.addEvent(le);

        }
    }

    public Class getLatencyMessageClass() {
        return latencyMessageClass;
    }

    public void setLatencyMessageClass(Class latencyMessageClass) {
        this.latencyMessageClass = latencyMessageClass;
    }
}
