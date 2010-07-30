package org.wisenet.simulator.utils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import static org.wisenet.simulator.core.engine.Simulator.randomGenerator;
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

	public RandomList() {}

	public RandomList( Collection <? extends T> c) {
		super(c) ;
	}
	
	public RandomList( Iterator <? extends T> it ) {
		for( ; it.hasNext() ; )
			add( it.next() ) ;
	}
	
	public T randomElement() {
		return isEmpty() ? null : get( (int)(randomGenerator.random().nextDouble() * size() ) ) ;
	}
	
	public T removeRandomElement() {
		return isEmpty() ? null : remove( (int)(randomGenerator.random().nextDouble() * size() ) ) ;
	}		
}