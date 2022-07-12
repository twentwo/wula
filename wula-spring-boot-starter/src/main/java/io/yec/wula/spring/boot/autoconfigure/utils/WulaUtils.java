package io.yec.wula.spring.boot.autoconfigure.utils;


import lombok.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
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
