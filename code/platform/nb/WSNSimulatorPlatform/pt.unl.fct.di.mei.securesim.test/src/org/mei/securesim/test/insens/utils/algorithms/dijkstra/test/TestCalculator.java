package org.mei.securesim.test.insens.utils.algorithms.dijkstra.test;

import org.mei.securesim.test.insens.utils.algorithms.dijkstra.engine.Calculator;

public class TestCalculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calculator.getInstance().addVertex((short)1);
		Calculator.getInstance().addVertex((short)2);
		Calculator.getInstance().addVertex((short)3);
		Calculator.getInstance().addVertex((short)4);
		Calculator.getInstance().addVertex((short)5);
		Calculator.getInstance().addVertex((short)6);
		Calculator.getInstance().addVertex((short)7);
		Calculator.getInstance().addVertex((short)8);
		Calculator.getInstance().addVertex((short)9);
		Calculator.getInstance().addVertex((short)10);

		Calculator.getInstance().addEdge((short)1,(short)7 );
		Calculator.getInstance().addEdge((short)1,(short)7 );
		Calculator.getInstance().addEdge((short)1,(short)7 );
		Calculator.getInstance().addEdge((short)1,(short)7 );
		Calculator.getInstance().addEdge((short)1,(short)7 );
		Calculator.getInstance().addEdge((short)1,(short)7 );
		Calculator.getInstance().addEdge((short)1,(short)7 );

		
		
	
	}

}
