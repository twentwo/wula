package io.yec.wula.core.routerule;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.identity.Identity;
import lombok.Data;

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
    public ExtensionPoint match(Class clazz, Identity identity) {
        for (ExtensionRouteRule extensionRouteRule : extensionRouteRules) {
            if (clazz.isAssignableFrom(extensionRouteRule.getExtensionPoint().getClass())
                    && extensionRouteRule.match(identity)) {
                return extensionRouteRule.getExtensionPoint();
            }
        }
        return null;
    }

}
