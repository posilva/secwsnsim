package pt.unl.fct.di.mei.securesim.utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static pt.unl.fct.di.mei.securesim.engine.Simulator.random;
/**
 * 
 * An utility class that implements a list that can index elements at random positions.
 * It uses the Simulation random generator to produce the random indices.
 * @author Sérgio Duarte (smd@di.fct.unl.pt)
 *
 * @param <T> The type of the elements of the list.
 */
@SuppressWarnings("serial")
public class RandomList<T> extends ArrayList<T>{

	public RandomList() {}

	public RandomList( Collection <? extends T> c) {
		super(c) ;
	}
	
	public RandomList( Iterator <? extends T> it ) {
		for( ; it.hasNext() ; )
			add( it.next() ) ;
	}
	
	public T randomElement() {
		return isEmpty() ? null : get( (int)(random.nextDouble() * size() ) ) ;
	}
	
	public T removeRandomElement() {
		return isEmpty() ? null : remove( (int)(random.nextDouble() * size() ) ) ;		
	}		
}