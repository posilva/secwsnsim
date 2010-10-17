package org.wisenet.simulator.utilities;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import static org.wisenet.simulator.core.Simulator.randomGenerator;
/**
 * 
 * An utility class that implements a list that can index elements at randomGenerator positions.
 * It uses the Simulation randomGenerator generator to produce the randomGenerator indices.
 * @author Sérgio Duarte (smd@di.fct.unl.pt)
 *
 * @param <T> The type of the elements of the list.
 */
@SuppressWarnings("serial")
public class RandomList<T> extends Vector<T>{

    /**
     *
     */
    public RandomList() {}

    /**
     *
     * @param c
     */
    public RandomList( Collection <? extends T> c) {
		super(c) ;
	}
	
        /**
         *
         * @param it
         */
        public RandomList( Iterator <? extends T> it ) {
		for( ; it.hasNext() ; )
			add( it.next() ) ;
	}
	
        /**
         *
         * @return
         */
        public T randomElement() {
		return isEmpty() ? null : get( (int)(randomGenerator.random().nextDouble() * size() ) ) ;
	}
	
        /**
         *
         * @return
         */
        public T removeRandomElement() {
		return isEmpty() ? null : remove( (int)(randomGenerator.random().nextDouble() * size() ) ) ;
	}		
}