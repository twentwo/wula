package io.yec.wula.core.routerule.holder;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.routerule.GroupExtensionRouteRule;

import java.util.HashMap;
import java.util.Map;

/**
 * GroupExtensionRouteRuleHolder
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public class GroupExtensionRouteRuleHolder implements IExtensionRouteRuleHolder<ExtensionPoint, GroupExtensionRouteRule> {

    private Map<Class<ExtensionPoint>, GroupExtensionRouteRule> extensionRouteRuleMap = new HashMap<>();

    @Override
    public GroupExtensionRouteRule getExtensionRouteRule(Class<ExtensionPoint> targetClz) {
        return extensionRouteRuleMap.get(targetClz);
    }

    @Override
    public void setExtensionRouteRuleMap(Map<Class<ExtensionPoint>, GroupExtensionRouteRule> extensionRouteRuleMap) {
        this.extensionRouteRuleMap = extensionRouteRuleMap;
    }

}
