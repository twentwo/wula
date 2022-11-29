package io.yec.wula.core.routerule;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.context.RouteContext;

/**
 * IExtensionRouteRule
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public interface IExtensionRouteRule {
    /**
     * match and return ExtensionPoint
     *
     * @param clazz
     * @param routeContext
     * @return
     */
    ExtensionPoint match(Class clazz, RouteContext routeContext);
}
