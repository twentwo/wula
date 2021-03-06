package io.yec.wula.core.config;

import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.routerule.ExtensionRouteRule;
import io.yec.wula.core.routerule.GroupExtensionRouteRule;
import io.yec.wula.core.routerule.IExtensionRouteRule;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;

/**
 * GroupRouteRuleDef
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Data
public class GroupRouteRuleDef<E extends IExtensionRouteRule> {

    private String group;
    private List<ExtensionRouteRuleDef> def;

    public E toRule(BeanFactory beanFactory) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        GroupExtensionRouteRule groupExtensionRouteRule = new GroupExtensionRouteRule();
        List<ExtensionRouteRule> extensionRouteRules = new ArrayList<>();
        for (ExtensionRouteRuleDef routeRuleDef : def) {
            Object extensionObj = beanFactory.getBean(routeRuleDef.getBeanName());
            if (!ExtensionPoint.class.isAssignableFrom(extensionObj.getClass())) {
                throw new ExtException("extension init fail : extension didn't implements ExtensionPoint");
            }
            ensureMatchGroup(group, extensionObj);
            ExtensionRouteRule extensionRouteRule = new ExtensionRouteRule();
            extensionRouteRule.setExpression(expressionParser.parseExpression(routeRuleDef.getExtEl()));
            extensionRouteRule.setExtensionPoint((ExtensionPoint) extensionObj);
            extensionRouteRules.add(extensionRouteRule);
        }
        groupExtensionRouteRule.setGroup(this.group);
        groupExtensionRouteRule.setExtensionRouteRules(extensionRouteRules);
        return (E) groupExtensionRouteRule;
    }

    private static void ensureMatchGroup(@NonNull String group, @NonNull Object extensionPointImpl) {
        Class<?> groupInterface = getClass(group);
        if (!groupInterface.isAssignableFrom(extensionPointImpl.getClass())) {
            throw new ExtException("group interface is not exist: " + group);
        }
    }

    public static Class<?> getClass(@NonNull String clazzName) {
        Class<?> groupInterface;
        try {
            groupInterface =  Class.forName(clazzName);
        } catch (ClassNotFoundException exception) {
            throw new ExtException("group interface is not exist: " + clazzName);
        }
        return groupInterface;
    }

}