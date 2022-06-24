package io.yec.wula.core.routerule;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.identity.Identity;
import lombok.Data;
import org.springframework.expression.Expression;

/**
 * ExtensionRouteRule
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
@Data
public class ExtensionRouteRule {

    private Expression expression;
    private ExtensionPoint extensionPoint;

    public boolean match(Identity identity) {
        return this.getExpression().getValue(identity.getPairs(), Boolean.class);
    }

}
