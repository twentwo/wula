package io.yec.wula.core.routerule;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.identity.Identity;
import lombok.Data;
import org.springframework.core.Ordered;
import org.springframework.expression.Expression;

import java.util.Objects;

/**
 * ExtensionRouteRule
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
@Data
public class ExtensionRouteRule implements Ordered {

    private Expression expression;
    private ExtensionPoint extensionPoint;
    private Integer order;

    public boolean match(Identity identity) {
        return this.getExpression().getValue(identity.getPairs(), Boolean.class);
    }

    @Override
    public int getOrder() {
        return Objects.isNull(order) ? Ordered.LOWEST_PRECEDENCE : order;
    }

}
