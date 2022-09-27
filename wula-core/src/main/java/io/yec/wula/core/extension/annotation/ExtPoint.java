package io.yec.wula.core.extension.annotation;

import java.lang.annotation.*;

/**
 * ExtPoint is a rule strategy
 *
 * @author baijiu.yec
 * @since 2022/07/13
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtPoint {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";
}
