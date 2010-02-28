/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.unl.fct.di.mei.securesim.radio;

import java.util.ArrayList;
import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.core.radio.GaussianRadioModel;
import pt.unl.fct.di.mei.securesim.core.radio.RadioModel;

/**
 *
 * @author posilva
 */
public class ImprovedGaussianRadioModel extends GaussianRadioModel {

    @Override
    public RadioModel.Neighborhood createNeighborhood() {
		return new Neighborhood();
	}
    public class Neighborhood extends GaussianRadioModel.Neighborhood{
        	public ArrayList<Node> neighborsThatKnowMe=new ArrayList<Node>();
            
    }
}
