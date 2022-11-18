package io.yec.wula.core.executor;

import com.google.common.base.Preconditions;
import io.yec.wula.core.config.cache.ICache;
import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.extension.identity.BizIdentity;
import io.yec.wula.core.extension.identity.Identity;
import io.yec.wula.core.extension.identity.IdentityAssembler;
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
    private IdentityAssembler entityIdentityAssembler;

    @Setter
    private ICache cache;

    public ExtExtensionExecutorImpl(IExtensionRouteRuleHolder extensionRouteRuleHolder, IdentityAssembler entityIdentityAssembler, ICache cache) {
        this.extensionRouteRuleHolder = extensionRouteRuleHolder;
        this.entityIdentityAssembler = entityIdentityAssembler;
        this.cache = cache;
    }

    @Override
    protected <ExtP> ExtP locateComponent(Class<ExtP> targetClz, BizIdentity entity) {
        return locateExtension(targetClz, entity);
    }

    /**
     * locate extension point
     * <p>
     * assemble biz params by IdentityAssembler
     * traverse ExtensionRouteRule, find the ext point interface impl && judge the biz ext-el match or not
     * hit the only ext point and return
     *
     * @param targetClz
     * @param entity
     * @param <ExtP>
     * @return
     */
    protected <ExtP> ExtP locateExtension(Class<ExtP> targetClz, BizIdentity entity) {
        Preconditions.checkArgument(Objects.nonNull(entity), "biz entity can not be null for extension");
        Identity identity = entityIdentityAssembler.assemble(entity);
        ExtP extensionPoint = (ExtP) cache.get(identity);
        if (Objects.nonNull(extensionPoint)) {
            return extensionPoint;
        }
        IExtensionRouteRule extensionRouteRule = extensionRouteRuleHolder.getExtensionRouteRule(targetClz);
        if (Objects.nonNull(extensionRouteRule)) {
            extensionPoint = (ExtP) extensionRouteRule.match(targetClz, identity);
            cache.add(identity, extensionPoint);
            return extensionPoint;
        }
        throw new ExtException("locate ext fail, not extension match");
    }

}
