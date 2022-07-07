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
public interface IExtensionRouteRuleHolder<T extends IExtensionRouteRule, P extends ExtensionPoint> {

    /**
     * Fetch
     *
     * @return
     */
    Map<Class<P>, T> getExtensionRouteRuleGroup();

    /**
     * Set
     *
     * @param extensionRouteRuleGroup
     */
    void setExtensionRouteRuleGroup(Map<Class<P>, T> extensionRouteRuleGroup);
}
