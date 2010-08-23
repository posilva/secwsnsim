/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wisenet.simulator.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author CIAdmin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApplicationMethod {
    String menuLabel() default "Method Label Missing";
    String methodName () default "";
    String menuTooltip() default "put mehod description here";
}
