package io.yec.wula.core.executor;

import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.extension.ExtensionPoint;
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

    public ExtExtensionExecutorImpl(IExtensionRouteRuleHolder extensionRouteRuleHolder, IdentityAssembler entityIdentityAssembler) {
        this.extensionRouteRuleHolder = extensionRouteRuleHolder;
        this.entityIdentityAssembler = entityIdentityAssembler;
    }

    @Override
    protected <C> C locateComponent(Class<C> targetClz, Object entity) {
        return locateExtension(targetClz, entity);
    }

    /**
     * locate extension point
     *
     * assemble biz params by IdentityAssembler
     * traverse ExtensionRouteRule, find the ext point interface impl && judge the biz ext-el match or not
     * hit the only ext point and return
     *
     * @param targetClz
     * @param entity
     * @param <ExtP>
     * @return
     */
    protected <ExtP> ExtP locateExtension(Class<ExtP> targetClz, Object entity) {
        checkNull(entity);
        Identity identity = entityIdentityAssembler.assemble(entity);
        IExtensionRouteRule extensionRouteRule = extensionRouteRuleHolder.getExtensionRouteRule(targetClz);
        if (Objects.nonNull(extensionRouteRule)) {
            ExtensionPoint extensionPoint = extensionRouteRule.match(targetClz, identity);
            if (Objects.nonNull(extensionPoint)) {
                return (ExtP) extensionPoint;
            }
        }
        throw new ExtException("locate ext fail, not extension match");
    }

    private void checkNull(Object entity) {
        if (entity == null) {
            throw new ExtException("biz entity can not be null for extension");
        }
    }

}
