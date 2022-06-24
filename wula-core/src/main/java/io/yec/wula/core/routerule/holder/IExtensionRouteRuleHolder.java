package io.yec.wula.core.routerule.holder;

import io.yec.wula.core.routerule.IExtensionRouteRule;

import java.util.List;

/**
 * ExtensionRouteRuleHolder Interface
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public interface IExtensionRouteRuleHolder<T extends IExtensionRouteRule> {

    /**
     * Fetch
     *
     * @return
     */
    List<T> getExtensionRouteRuleList();

    /**
     * Set
     *
     * @param extensionRouteRuleList
     */
    void setExtensionRouteRuleList(List<T> extensionRouteRuleList);
}
