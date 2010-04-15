/**
 * 
 */
package org.mei.securesim.components.topology;


import org.mei.securesim.utils.annotation.Annotated;
import java.awt.Rectangle;
import java.util. Vector;
import org.mei.securesim.utils.annotation.Parameter;



import org.mei.securesim.core.nodes.Node;

/**
 * @author posilva
 * 
 */
public class GridTopologyManager extends TopologyManager implements Annotated {
	/**
	 * 
	 */
    @Parameter(label="Indique a largura da quadricula",value="50")
    public String gridW; 
    @Parameter(label="Indique a altura da quadricula",value="50")
    public String gridH;

    public GridTopologyManager() {

	}

    @Override
    public  Vector<Node> apply(Rectangle rect,  Vector<Node> nodes) {

     int h= Integer.valueOf(gridH);
     int w= Integer.valueOf(gridW);


     if (rect.width<w || rect.height<h){
         
     }

     return nodes;
    }
}
