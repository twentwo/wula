package io.yec.wula.core.executor;

import io.yec.wula.core.extension.identity.BizIdentity;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * AbstractExtensionExecutor
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public abstract class AbstractExtensionExecutor implements ExtensionExecutor {

    /**
     * Execute extension with Response
     *
     * @param <R>         Response Type
     * @param <ExtP>      Parameter Type
     * @param targetClz   extpoint interface
     * @param entity      biz entity
     * @param exeFunction extpoint invoke method
     * @return extension invoke result
     */
    @Override
    public <R, ExtP> R execute(Class<ExtP> targetClz, BizIdentity entity, Function<ExtP, R> exeFunction) {
        ExtP component = locateComponent(targetClz, entity);
        return exeFunction.apply(component);
    }


    /**
     * Execute extensions without Response
     *
     * @param targetClz   extpoint interface
     * @param entity      biz entity
     * @param exeFunction extpoint invoke method
     * @param <ExtP>      Parameter Type
     */
    @Override
    public <ExtP> void executeVoid(Class<ExtP> targetClz, BizIdentity entity, Consumer<ExtP> exeFunction) {
        ExtP component = locateComponent(targetClz, entity);
        exeFunction.accept(component);
    }


    /**
     * locate ext point implements
     *
     * @param targetClz extpoint interface
     * @param bizIdentity biz entity
     * @param <ExtP>
     * @return ext point implement
     */
    protected abstract <ExtP> ExtP locateComponent(Class<ExtP> targetClz, BizIdentity bizIdentity);
}
