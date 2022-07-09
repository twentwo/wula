package io.yec.wula.core.config.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.yec.wula.core.config.GroupRouteRuleDef;
import io.yec.wula.core.exception.ExtException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * JsonRuleConfigLoader
 *
 * @author baijiu.yec
 * @since 2022/07/08
 */
@Slf4j
public class JsonRuleConfigLoader implements RuleConfigLoader {

    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    @Override
    public String[] getFileExtensions() {
        return new String[] { "json"};
    }

    @Override
    public List<GroupRouteRuleDef> load(@NonNull String location, @NonNull ResourcePatternResolver resourcePatternResolver) {
        Resource[] ruleDefResources;
        try {
            ruleDefResources = resourcePatternResolver.getResources(location);
        } catch (IOException e) {
            throw new ExtException("extension config load exception: " + e.getMessage());
        }
        List<GroupRouteRuleDef> groupRouteRuleDefs = new ArrayList<>(64);
        Stream.of(ruleDefResources).forEach(ruleDefResource -> {
            try {
                groupRouteRuleDefs.addAll(objectMapper.readValue(ruleDefResource.getFile(), new TypeReference<List<GroupRouteRuleDef>>() {}));
            } catch (IOException e) {
                log.error("parse ruleDef json err ", e);
                throw new ExtException("parse ruleDef json err: " + e.getMessage());
            }
        });
        return groupRouteRuleDefs;
    }

}
