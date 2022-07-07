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
public class GroupExtensionRouteRuleHolder implements IExtensionRouteRuleHolder<GroupExtensionRouteRule, ExtensionPoint> {

    private Map<Class<ExtensionPoint>, GroupExtensionRouteRule> extensionRouteRuleGroup = new HashMap<>();

    @Override
    public Map<Class<ExtensionPoint>, GroupExtensionRouteRule> getExtensionRouteRuleGroup() {
        return this.extensionRouteRuleGroup;
    }

    @Override
    public void setExtensionRouteRuleGroup(Map<Class<ExtensionPoint>, GroupExtensionRouteRule> extensionRouteRuleGroup) {
        this.extensionRouteRuleGroup = extensionRouteRuleGroup;
    }

}
