package io.yec.wula.core.config.loader;

import com.alibaba.fastjson.JSONObject;
import io.yec.wula.core.common.IOUtils;
import io.yec.wula.core.config.GroupRouteRuleDef;
import io.yec.wula.core.config.loader.RuleConfigLoader;
import io.yec.wula.core.exception.ExtException;
import lombok.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonRuleConfigLoader
 *
 * @author baijiu.yec
 * @since 2022/07/08
 */
public class JsonRuleConfigLoader implements RuleConfigLoader {

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
            throw new ExtException("extension config init exception :" + e.getMessage());
        }
        List<String> ruleDefs = new ArrayList<>();
        for (Resource ruleDefResource : ruleDefResources) {
            try (InputStream ruleDefInputStream = ruleDefResource.getInputStream()) {
                ruleDefs.add(IOUtils.convertString(ruleDefInputStream));
            } catch (IOException e) {
                throw new ExtException("extension config init exception :" + e.getMessage());
            }
        }
        List<GroupRouteRuleDef> groupRouteRuleDefs = new ArrayList<>(64);
        ruleDefs.forEach(ruleDef -> groupRouteRuleDefs.addAll(JSONObject.parseArray(ruleDef, GroupRouteRuleDef.class)));
        return groupRouteRuleDefs;
    }

}
