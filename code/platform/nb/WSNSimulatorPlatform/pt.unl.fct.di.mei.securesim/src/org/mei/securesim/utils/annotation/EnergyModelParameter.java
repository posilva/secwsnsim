
package org.mei.securesim.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author posilva
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnergyModelParameter {
    String label();
    double value() default 0.0;
    boolean required() default false;
    String classToLoad() default "";
    boolean isClass() default false;
}
