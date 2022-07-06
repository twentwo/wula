package io.yec.wula.core.config;

import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.routerule.ExtensionRouteRule;
import io.yec.wula.core.routerule.GroupExtensionRouteRule;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;

/**
 * GroupRouteRuleJson
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Data
public class GroupRouteRuleJson {

    private String group;
    private List<ExtensionRouteRuleDef> def;

    public GroupExtensionRouteRule build(ApplicationContext applicationContext) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        GroupExtensionRouteRule groupExtensionRouteRule = new GroupExtensionRouteRule();
        List<ExtensionRouteRule> extensionRouteRules = new ArrayList<>();
        for (ExtensionRouteRuleDef routeRuleDef : def) {
            Object extensionObj = applicationContext.getBean(routeRuleDef.getBeanName());
            if (!ExtensionPoint.class.isAssignableFrom(extensionObj.getClass())) {
                throw new ExtException("extension init fail : extension didn't implements ExtensionPoint");
            }
            ExtensionRouteRule extensionRouteRule = new ExtensionRouteRule();
            extensionRouteRule.setExpression(expressionParser.parseExpression(routeRuleDef.getExtEl()));
            extensionRouteRule.setExtensionPoint((ExtensionPoint) extensionObj);
            extensionRouteRules.add(extensionRouteRule);
        }
        groupExtensionRouteRule.setGroup(this.group);
        groupExtensionRouteRule.setExtensionRouteRules(extensionRouteRules);
        return groupExtensionRouteRule;
    }

}