/**
 * 
 */
package pt.unl.fct.di.mei.securesim.engine.layers;

import java.util.EventListener;

/**
 * @author posilva
 *
 */
public interface SendMessageDoneListener extends EventListener {
	public void onMessageDone(MessageEvent evt);
}
