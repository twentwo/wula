package io.yec.wula.core.routerule;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.context.RouteContext;
import lombok.Data;
import org.springframework.core.OrderComparator;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * extension route rule with group
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
@Data
public class GroupExtensionRouteRule implements IExtensionRouteRule {

    /**
     * group
     */
    private String group;
    /**
     * extension list
     */
    private List<ExtensionRouteRule> extensionRouteRules;

    @Override
    public ExtensionPoint match(Class clazz, RouteContext routeContext) {
        for (ExtensionRouteRule extensionRouteRule : extensionRouteRules) {
            if (clazz.isAssignableFrom(extensionRouteRule.getExtensionPoint().getClass())
                    && extensionRouteRule.match(routeContext)) {
                return extensionRouteRule.getExtensionPoint();
            }
        }
        return null;
    }

    public void sortExtensionRouteRules() {
        if (!CollectionUtils.isEmpty(extensionRouteRules)) {
            OrderComparator.sort(extensionRouteRules);
        }
    }

}
