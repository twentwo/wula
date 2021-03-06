package io.yec.wula.core.config.loader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.yec.wula.core.config.GroupRouteRuleDef;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.List;

/**
 * YamlRuleConfigLoader
 *
 * @author baijiu.yec
 * @since 2022/07/08
 */
@Slf4j
public class YamlRuleConfigLoader implements RuleConfigLoader {

    private static ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    @Override
    public String[] getFileExtensions() {
        return new String[] { "yml", "yaml" };
    }

    @Override
    public List<GroupRouteRuleDef> load(@NonNull String location, @NonNull ResourcePatternResolver resourcePatternResolver) {
        return loadAndParseRuleConfigResources(location, resourcePatternResolver, () -> objectMapper);
    }

}
