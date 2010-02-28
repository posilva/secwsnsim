package pt.unl.fct.di.mei.securesim.test.broadcast;

import pt.unl.fct.di.mei.securesim.core.Event;
import pt.unl.fct.di.mei.securesim.core.Simulator;
import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.core.radio.GaussianRadioModel;

public class BroadcastMain {
	private static final double ROOT_POSZ = 0.0;
	private static final double ROOT_POSY = 150.0;
	private static final double ROOT_POSX = 150.0;
	private static final int ROOT_ID = 1;
	private static final int MAX_ELEVATION = 5;
	private static final int AREA_WIDTH = 300;
	private static final int FIRST_SIMPLENODE_ID = 2;
	private static final int NUMBER_OF_SIMPLE_NODES = 4999;

	public static void main(String[] args) throws Exception{       
		System.out.println("creating nodes...");        
		Simulator sim = new Simulator();
		// creating the desired radio model, uncomment the one you need 
		GaussianRadioModel radioModel = new GaussianRadioModel();
		//RayleighRadioModel radioModel = new RayleighRadioModel(sim);

		sim.setRadioModel(radioModel);
		long time0 = System.currentTimeMillis();
		BroadcastNode root = (BroadcastNode)sim.createNode( BroadcastNode.class, radioModel, (short)ROOT_ID, ROOT_POSX, ROOT_POSY, ROOT_POSZ);
		BroadcastApplication bcApp = BroadcastApplication.createInstance();
		root.addApplication(bcApp);

		// creating all the other nodes
		sim.createNodes( BroadcastNode.class, radioModel, FIRST_SIMPLENODE_ID, NUMBER_OF_SIMPLE_NODES, AREA_WIDTH,  MAX_ELEVATION);
		for (Node n : sim.getNodes()) {
			BroadcastApplication tempBcApp = new BroadcastApplication();
			n.addApplication(tempBcApp);
		}
	
		sim.init();
		
        System.out.println("creation time: " + (System.currentTimeMillis()-time0) + " millisecs" );
        final long time1 = System.currentTimeMillis();
	    System.out.println("start simulation");

	    root.sendMessageFromApplication("25", bcApp );
        
        boolean realTime = true;
        if( realTime )
        {
            sim.runWithDisplayInRealTime();
        }
        else
        {
            // run as fast as possible and measure dump execution time
            Event event = new Event()
            {
                public void execute()
                {
                    System.out.println("execution time: " + (System.currentTimeMillis()-time1) + " millisecs" );
                }
            };
            event.setTime(Simulator.ONE_SECOND * 1000);
            sim.addEvent(event);
            sim.run(20000);
        }       
	}

}
