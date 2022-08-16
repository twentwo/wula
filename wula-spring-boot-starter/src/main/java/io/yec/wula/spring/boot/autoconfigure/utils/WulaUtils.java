package io.yec.wula.spring.boot.autoconfigure.utils;


import io.yec.wula.core.extension.ExtPointBeanDefinitionRegistryPostProcessor;
import lombok.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

/**
 * wula utils
 *
 * @author baijiu.yec
 * @since 2022/04/29
 */
public class WulaUtils {

    /**
     * The separator of property name
     */
    public static final String PROPERTY_NAME_SEPARATOR = ".";

    /**
     * The prefix of property name of Wula
     */
    public static final String WULA_ROUTER_PREFIX = "wula.router";

    /**
     * The prefix of property name for Wula scan
     */
    public static final String WULA_SCAN_PREFIX = WULA_ROUTER_PREFIX + PROPERTY_NAME_SEPARATOR + "scan" + PROPERTY_NAME_SEPARATOR;

    /**
     * The property name of base packages to scan
     * <p>
     * The default value is empty set.
     */
    public static final String BASE_PACKAGES_PROPERTY_NAME = "base-packages";

    /**
     * The bean name of {@link Set} presenting {@link ExtPointBeanDefinitionRegistryPostProcessor}'s base-packages
     *
     * @since 1.3.0
     */
    public static final String BASE_PACKAGES_BEAN_NAME = "wula-ext-point-class-base-packages";

    /**
     * match resource exist by locationPattern
     *
     * @param locationPattern
     * @return
     * @throws IOException
     */
    public static boolean resourceExists(@NonNull String locationPattern) throws IOException {
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(locationPattern);
        return Stream.of(resources).anyMatch(resource -> resource.exists());
    }

}
