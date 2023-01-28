package io.yec.wula.core.config.ruledef;

import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.routerule.ExtensionRouteRule;
import io.yec.wula.core.routerule.GroupExtensionRouteRule;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * GroupRouteRuleDef
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Data
public class GroupRouteRuleDef implements RuleDefinition<GroupExtensionRouteRule> {

    private String group;
    private List<ExtensionRouteRuleDef> def;

    @Override
    public GroupExtensionRouteRule toRule(BeanFactory beanFactory, ExpressionParser expressionParser) {
        List<ExtensionRouteRule> extensionRouteRules = new ArrayList<>();
        for (ExtensionRouteRuleDef routeRuleDef : def) {
            ExtensionRouteRule extensionRouteRule = routeRuleDef.toRule(beanFactory, expressionParser);
            extensionRouteRules.add(extensionRouteRule);
        }
        ensureMatchGroup(this.group, extensionRouteRules);
        GroupExtensionRouteRule groupExtensionRouteRule = GroupExtensionRouteRule.builder()
                .extensionRouteRules(extensionRouteRules)
                .group(this.group)
                .build();
        groupExtensionRouteRule.sortExtensionRouteRules();
        return groupExtensionRouteRule;
    }

    private static void ensureMatchGroup(@NonNull String group, @NonNull List<ExtensionRouteRule> extensionRouteRules) {
        Class<?> groupInterface = ClassUtils.resolveClassName(group, null);
        extensionRouteRules.forEach(extensionRouteRuleDef -> {
            ExtensionPoint extensionPoint = extensionRouteRuleDef.getExtensionPoint();
            if (!groupInterface.isAssignableFrom(extensionPoint.getClass())) {
                throw new ExtException("group interface is not match, group: " + group + ", impl class: " + extensionPoint.getClass());
            }
        });
    }

}