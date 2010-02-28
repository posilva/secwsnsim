/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.unl.fct.di.mei.securesim.annotation;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = RUNTIME)
@Target(FIELD)
public @interface SimParameter {
	/**
	 * Human readable description of the field.
	 * If the description isn't set, the field is configurable through the GUI.
	 */
	String description() default "";

	/**
	 * Flag that indicated if the field is required.
	 * Default is "false".
	 */
	boolean required() default false;

	/**
	 * value that should be bind when no value is configured.
	 * Can be null.
	 * Only defaultValue or defaultClass can be used. If both are used, this
	 * results in an ConfigurationException.
	 */
	String defaultValue() default "";


        Class<?>  defaultClass() default Class.class;
	/**
	 * optional name of the parameter.
	 * Used in the GUI. If not set, the field name is used.
	 * @return
	 */
	String name() default "";
}
