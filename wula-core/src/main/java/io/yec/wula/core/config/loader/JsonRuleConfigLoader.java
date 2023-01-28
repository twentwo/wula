package io.yec.wula.core.config.loader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.yec.wula.core.config.ruledef.GroupRouteRuleDef;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.List;

/**
 * JsonRuleConfigLoader
 *
 * @author baijiu.yec
 * @since 2022/07/08
 */
@Slf4j
public class JsonRuleConfigLoader implements RuleConfigLoader<GroupRouteRuleDef> {

    private static ObjectMapper jsonToObjectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    @Override
    public String[] getFileExtensions() {
        return new String[] {"json"};
    }

    @Override
    public List<GroupRouteRuleDef> load(@NonNull String location, @NonNull ResourcePatternResolver resourcePatternResolver) {
        return loadAndParseRuleConfigResources(location, resourcePatternResolver, () -> jsonToObjectMapper);
    }

}
