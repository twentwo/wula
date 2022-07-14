package io.yec.wula.core.routerule.holder;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.routerule.IExtensionRouteRule;

import java.util.Map;

/**
 * ExtensionRouteRuleHolder Interface
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public interface IExtensionRouteRuleHolder<ExtP extends ExtensionPoint, Rule extends IExtensionRouteRule> {

    /**
     * Fetch
     * @param targetClz
     * @return
     */
    Rule getExtensionRouteRule(Class<ExtP> targetClz);

    /**
     * Set
     *
     * @param extensionRouteRuleMap
     */
    void setExtensionRouteRuleMap(Map<Class<ExtP>, Rule> extensionRouteRuleMap);
}
