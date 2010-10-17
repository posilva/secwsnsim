
package org.wisenet.simulator.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnergyModelParameter {
    /**
     *
     * @return
     */
    String label();
    /**
     *
     * @return
     */
    double value() default 0.0;
    /**
     *
     * @return
     */
    boolean required() default false;
    /**
     *
     * @return
     */
    String classToLoad() default "";
    /**
     *
     * @return
     */
    boolean isClass() default false;
}
