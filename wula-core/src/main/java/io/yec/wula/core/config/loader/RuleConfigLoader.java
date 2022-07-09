package io.yec.wula.core.config.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.yec.wula.core.config.GroupRouteRuleDef;
import io.yec.wula.core.exception.ExtException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

    /**
     * load RuleConfigResources
     * @param location
     * @param resourcePatternResolver
     * @return
     */
    default Resource[] loadRuleConfigResources(String location, ResourcePatternResolver resourcePatternResolver) {
        Resource[] ruleConfigResources;
        try {
            ruleConfigResources = resourcePatternResolver.getResources(location);
        } catch (IOException e) {
            throw new ExtException("extension config load exception: " + e.getMessage());
        }
        return ruleConfigResources;
    }

    /**
     * load and parse RuleConfigResources
     * @param location
     * @param resourcePatternResolver
     * @param objectMapperSupplier
     * @return
     */
    default List<GroupRouteRuleDef> loadAndParseRuleConfigResources(String location, ResourcePatternResolver resourcePatternResolver, Supplier<ObjectMapper> objectMapperSupplier) {
        Resource[] resources = loadRuleConfigResources(location, resourcePatternResolver);
        return parseRuleConfigResources(resources, objectMapperSupplier.get());
    }

    /**
     * parse RuleConfigResources
     *
     * @param ruleConfigResources
     * @param objectMapper
     * @return
     */
    default List<GroupRouteRuleDef> parseRuleConfigResources(Resource[] ruleConfigResources, ObjectMapper objectMapper) {
        List<GroupRouteRuleDef> groupRouteRuleDefs = new ArrayList<>(64);
        Stream.of(ruleConfigResources).forEach(ruleDefResource -> {
            try {
                groupRouteRuleDefs.addAll(objectMapper.readValue(ruleDefResource.getFile(), new TypeReference<List<GroupRouteRuleDef>>() {}));
            } catch (IOException e) {
                throw new ExtException("parse ruleDef json err: " + e.getMessage());
            }
        });
        return groupRouteRuleDefs;
    }

}
