
import java.util.Iterator;
import java.util.TreeSet;
import org.mei.securesim.test.cleanslate.utils.NeighborInfo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro Marques da Silva
 */
public class Test {

    public static void main(String args[]) {
        NeighborInfo n1 = new NeighborInfo(1, (short)1);
        NeighborInfo n2 = new NeighborInfo(1, (short)1);
        if (n1.equals(n2)) {
            System.out.println("PORREIRO");
        }


        TreeSet t = new TreeSet();
        t.add(n1);
        t.add(n2);

        for (Iterator it = t.iterator(); it.hasNext();) {
            NeighborInfo object = (NeighborInfo) it.next();
            System.out.println(object+"");
            
        }
    }

}
