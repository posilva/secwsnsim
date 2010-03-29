/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.annotation;

/**
 *
 * @author posilva
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
