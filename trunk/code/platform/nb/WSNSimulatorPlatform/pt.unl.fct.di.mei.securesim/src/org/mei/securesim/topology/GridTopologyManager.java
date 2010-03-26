/**
 * 
 */
package org.mei.securesim.topology;


import org.mei.securesim.annotation.Annotated;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.mei.securesim.annotation.Parameter;



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
    public ArrayList<Node> apply(Rectangle rect, ArrayList<Node> nodes) {

     int h= Integer.valueOf(gridH);
     int w= Integer.valueOf(gridW);


     if (rect.width<w || rect.height<h){
         
     }

     return nodes;
    }
}
