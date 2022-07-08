package io.yec.wula.core.config;

import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * RuleConfigLoader
 *
 * @author baijiu.yec
 * @since 2022/07/08
 */
public interface RuleConfigLoader {

    /**
     * Returns the file extensions that the loader supports (excluding the '.').
     * @return the file extensions
     */
    String[] getFileExtensions();

    /**
     * Load the resource into one or more GroupRouteRuleDef.
     * @param location
     * @param resourcePatternResolver
     * @return
     */
    List<GroupRouteRuleDef> load(String location, ResourcePatternResolver resourcePatternResolver);

    /**
     * can load config or not by file extension
     * @param location
     * @return
     */
    default boolean canLoadFileExtension(String location) {
        return Arrays.stream(this.getFileExtensions())
                .anyMatch((fileExtension) -> StringUtils.endsWithIgnoreCase(location, fileExtension));
    }

}
