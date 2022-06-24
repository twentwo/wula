package io.yec.wula.core.routerule.holder;

import io.yec.wula.core.routerule.GroupExtensionRouteRule;

import java.util.ArrayList;
import java.util.List;

/**
 * GroupExtensionRouteRuleHolder
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public class GroupExtensionRouteRuleHolder implements IExtensionRouteRuleHolder<GroupExtensionRouteRule> {

    private List<GroupExtensionRouteRule> extensionRouteRules = new ArrayList<>();

    @Override
    public List<GroupExtensionRouteRule> getExtensionRouteRuleList() {
        return this.extensionRouteRules;
    }

    @Override
    public void setExtensionRouteRuleList(List<GroupExtensionRouteRule> extensionRouteRuleList) {
        this.extensionRouteRules = extensionRouteRuleList;
    }

}
