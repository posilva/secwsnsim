package pt.unl.fct.di.mei.securesim.test;

import java.util.List;

import pt.unl.fct.di.mei.securesim.core.Application;
import pt.unl.fct.di.mei.securesim.core.Display;
import pt.unl.fct.di.mei.securesim.core.layers.RoutingLayer;
import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.core.radio.GaussianRadioModel;
import pt.unl.fct.di.mei.securesim.core.radio.RadioModel;
import pt.unl.fct.di.mei.securesim.test.broadcast.BroadcastApplication;
import pt.unl.fct.di.mei.securesim.test.broadcast.BroadcastNode;
import pt.unl.fct.di.mei.securesim.test.broadcast.BroadcastRoutingLayer;
import pt.unl.fct.di.mei.securesim.network.Network;
import pt.unl.fct.di.mei.securesim.network.SimulationArea;
import pt.unl.fct.di.mei.securesim.network.basic.DefaultNetwork;
import pt.unl.fct.di.mei.securesim.network.nodes.NodeFactory;
import pt.unl.fct.di.mei.securesim.network.nodes.SensorNode;
import pt.unl.fct.di.mei.securesim.network.nodes.SimpleNode;
import pt.unl.fct.di.mei.securesim.network.nodes.basic.DefaultNodeFactory;
import pt.unl.fct.di.mei.securesim.simulation.DefaultSimulator;
import pt.unl.fct.di.mei.securesim.simulation.Simulation;
import pt.unl.fct.di.mei.securesim.simulation.basic.BasicSimulation;
import pt.unl.fct.di.mei.securesim.topology.RandomTopologyManager;

public class MainTest {
	public static void main(String[] args) {
		//create simulator
		DefaultSimulator simulator= new DefaultSimulator();
		// criar o modelo de rádio
		RadioModel radioModel = new GaussianRadioModel();
		// create simulation object
		Simulation simulation = new BasicSimulation();
		// criar uma configuração para a simulação
	//	SimulationConfiguration simulationConfiguration = new SimulationConfiguration();
		// associa modelo de rádio ao simulador
		simulator.setRadioModel(radioModel);
		// associa o simulador à simulação
		simulation.setSimulator(simulator);
		// associar a configuracao à simulação 
		RandomTopologyManager topologyManager = new RandomTopologyManager();
		// create a network
		Network network = new DefaultNetwork();
		// definição da àrea de simulação
		SimulationArea simulationArea = new SimulationArea();
		simulationArea.setHeigth(300);
		simulationArea.setWidth(300);
		simulationArea.setMaxElevation(0);
		// assignar àrea de simulação à rede
		network.setSimulationArea(simulationArea);
		// cria factories de nós sink e simples
		NodeFactory simpleNodeFactory= new DefaultNodeFactory(simulator, BroadcastNode.class,BroadcastApplication.class,BroadcastRoutingLayer.class);
		NodeFactory sinkNodeFactory= new DefaultNodeFactory(simulator, BroadcastNode.class,BroadcastApplication.class,BroadcastRoutingLayer.class);
		
		simulator.setNetwork(network);
		
		// adicionar um nó sink e dois nós simples
		//SinkNode sinkNode = null;
		SimpleNode sinkNode = null;
		Application app = new BroadcastApplication();
		List<Node> listOfSimpleNodes=null;
		try {
			
			sinkNode= (SimpleNode) sinkNodeFactory.createNode((short) 1);
			sinkNode.setRoutingLayer(new BroadcastRoutingLayer());
			sinkNode.addApplication(app);
			sinkNode.setPaintNeighborhood(true);
			listOfSimpleNodes = simpleNodeFactory.createNodes(2, 999);
			network.addNode(sinkNode);
			for (Node simpleNode1 : listOfSimpleNodes) {
				simpleNode1.setRoutingLayer(new BroadcastRoutingLayer());
				simpleNode1.addApplication(new BroadcastApplication());
				network.addNode((SensorNode) simpleNode1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		network.applyTopology(topologyManager);
		simulator.init();

		simulator.setDisplay(new Display(simulator, 300));
		RoutingLayer routingLayer = new BroadcastRoutingLayer();
		sinkNode.setRoutingLayer(routingLayer);
		sinkNode.addApplication(app);
		sinkNode.sendMessageFromApplication("1", app);
		simulator.runWithDisplay();
	}
}
