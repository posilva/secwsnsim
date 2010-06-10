package org.mei.securesim.components.instruments;
/************************************
 * Instrumento de Medição de fiabilidade de uma rede
 *
 * Tendo em conta o numero as mensagens enviadas por uma simulataneamente na rede
 * qual o numero de mensagens que chega ao destino
 *
 *
 * Neste caso as mensagens são enviadas em burst que possam provocar algum stress na rede
 * e é medido o numero de mensagens que chegam face a este stress.
 *
 * Quando se estiver perante ataques ao encaminhamento o que se faz é igual
 * permitindo determinar o impacto do ataque nesta propriedade da rede.
 *
 *
 *
 */
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class ReliabilityController {

    protected boolean enable;
    static ReliabilityController instance = null;

    public static ReliabilityController getInstance() {
        if (instance == null) {
            instance = new ReliabilityController();
        }
        return instance;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void notifyMessageSent(Object message, Node node) {
    
    }

    public void notifyMessageReception(Object message, Node node) {
    
    }
}
