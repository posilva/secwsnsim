/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */

package org.wisenet.simulator.utilities.annotation;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class AnnotationTest implements Annotated{
    @Parameter(label="Insira o Valor do Campo",className="java.lang.String",value="teste")
    private String field;

    @Parameter(label="Insira o Valor do Campo2",className="java.lang.String",value="teste")
    private String field2;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        


        AnnotationTest at = new AnnotationTest();
     


        ParametersHandler.readParameters(at);

        System.out.println("Field1="+at.field);
        System.out.println("Field2="+at.field2);

    }

}
