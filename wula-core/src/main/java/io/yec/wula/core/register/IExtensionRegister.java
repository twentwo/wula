package io.yec.wula.core.register;

import io.yec.wula.core.routerule.IExtensionRouteRule;

import java.util.List;

/**
 * IExtensionRegister
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public interface IExtensionRegister<T extends IExtensionRouteRule> {

    /**
     * parse router json and register to the IExtensionRouteRuleHolder
     *
     * @param locations
     */
    void doRegister(List<String> locations);
}
