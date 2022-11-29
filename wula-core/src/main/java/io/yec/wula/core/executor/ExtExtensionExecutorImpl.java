package io.yec.wula.core.executor;

import com.google.common.base.Preconditions;
import io.yec.wula.core.config.cache.ICache;
import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.context.BizCondition;
import io.yec.wula.core.extension.context.RouteContext;
import io.yec.wula.core.extension.context.RouteContextAssembler;
import io.yec.wula.core.routerule.IExtensionRouteRule;
import io.yec.wula.core.routerule.holder.IExtensionRouteRuleHolder;
import lombok.Setter;

import java.util.Objects;

/**
 * ExtExtensionExecutorImpl
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public class ExtExtensionExecutorImpl extends AbstractExtensionExecutor {

    @Setter
    private IExtensionRouteRuleHolder extensionRouteRuleHolder;

    @Setter
    private RouteContextAssembler routeContextAssembler;

    @Setter
    private ICache cache;

    public ExtExtensionExecutorImpl(IExtensionRouteRuleHolder extensionRouteRuleHolder, RouteContextAssembler routeContextAssembler, ICache cache) {
        this.extensionRouteRuleHolder = extensionRouteRuleHolder;
        this.routeContextAssembler = routeContextAssembler;
        this.cache = cache;
    }

    @Override
    protected <ExtP extends ExtensionPoint> ExtP locateComponent(Class<ExtP> targetClz, BizCondition bizCondition) {
        return locateExtension(targetClz, bizCondition);
    }

    /**
     * locate extension point
     * <p>
     * assemble route context by routeContextAssembler
     * traverse ExtensionRouteRule, find the ext point interface impl && judge the biz ext-el match or not
     * hit the only ext point and return
     *
     * @param targetClz
     * @param bizCondition
     * @param <ExtP>
     * @return
     */
    protected <ExtP extends ExtensionPoint> ExtP locateExtension(Class<ExtP> targetClz, BizCondition bizCondition) {
        Preconditions.checkArgument(Objects.nonNull(bizCondition), "biz condition can not be null for extension");
        RouteContext routeContext = routeContextAssembler.assemble(bizCondition);
        ExtP extensionPoint = (ExtP) cache.get(routeContext);
        if (Objects.nonNull(extensionPoint)) {
            return extensionPoint;
        }
        IExtensionRouteRule extensionRouteRule = extensionRouteRuleHolder.getExtensionRouteRule(targetClz);
        if (Objects.nonNull(extensionRouteRule)) {
            extensionPoint = (ExtP) extensionRouteRule.match(targetClz, routeContext);
            cache.add(routeContext, extensionPoint);
            return extensionPoint;
        }
        throw new ExtException("locate ext fail, not extension match");
    }

}
