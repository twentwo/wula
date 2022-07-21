package io.yec.wula.core.register;

import io.yec.wula.core.config.GroupRouteRuleDef;
import io.yec.wula.core.config.loader.JsonRuleConfigLoader;
import io.yec.wula.core.config.loader.RuleConfigLoader;
import io.yec.wula.core.config.loader.YamlRuleConfigLoader;
import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.routerule.GroupExtensionRouteRule;
import io.yec.wula.core.routerule.IExtensionRouteRule;
import io.yec.wula.core.routerule.holder.IExtensionRouteRuleHolder;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * GroupExtensionRegister
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public class GroupExtensionRegister implements IExtensionRegister<GroupExtensionRouteRule> {

    private final IExtensionRouteRuleHolder extensionRouteRuleHolder;
    private final ApplicationContext applicationContext;
    private final ResourceLoader resourceLoader;
    @Setter
    private RuleConfigLoader[] ruleConfigLoaders = {new JsonRuleConfigLoader(), new YamlRuleConfigLoader()};

    public GroupExtensionRegister(IExtensionRouteRuleHolder extensionRouteRuleHolder, ApplicationContext applicationContext, ResourceLoader resourceLoader) {
        this.extensionRouteRuleHolder = extensionRouteRuleHolder;
        this.applicationContext = applicationContext;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void doRegister(List<String> locations) {
        Map<Class<?>, IExtensionRouteRule> extensionRouteRuleGroup = new HashMap<>(128);
        for (String location : locations) {
            RuleConfigLoader selectedRuleConfigLoader = null;
            for (RuleConfigLoader ruleConfigLoader : ruleConfigLoaders) {
                if (ruleConfigLoader.canLoadFileExtension(location)) {
                    selectedRuleConfigLoader = ruleConfigLoader;
                    break;
                }
            }
            if (Objects.isNull(selectedRuleConfigLoader)) {
                throw new ExtException("File extension of config file location '" + location
                        + "' is not known to any RuleConfigLoader.");
            }
            List<GroupRouteRuleDef> routeRuleDefs = selectedRuleConfigLoader.load(location, new PathMatchingResourcePatternResolver(resourceLoader));
            routeRuleDefs.forEach(routeRuleDef -> extensionRouteRuleGroup.put(ClassUtils.resolveClassName(routeRuleDef.getGroup(), null), routeRuleDef.toRule(applicationContext)));
        }
        extensionRouteRuleHolder.setExtensionRouteRuleMap(extensionRouteRuleGroup);
    }

}
