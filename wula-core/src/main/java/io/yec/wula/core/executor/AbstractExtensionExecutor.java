package io.yec.wula.core.executor;

import io.yec.wula.core.extension.context.BizCondition;

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
     * @param bizCondition biz bizCondition
     * @param exeFunction extpoint invoke method
     * @return extension invoke result
     */
    @Override
    public <R, ExtP> R execute(Class<ExtP> targetClz, BizCondition bizCondition, Function<ExtP, R> exeFunction) {
        ExtP component = locateComponent(targetClz, bizCondition);
        return exeFunction.apply(component);
    }


    /**
     * Execute extensions without Response
     *
     * @param targetClz   extpoint interface
     * @param bizCondition biz bizCondition
     * @param exeFunction extpoint invoke method
     * @param <ExtP>      Parameter Type
     */
    @Override
    public <ExtP> void executeVoid(Class<ExtP> targetClz, BizCondition bizCondition, Consumer<ExtP> exeFunction) {
        ExtP component = locateComponent(targetClz, bizCondition);
        exeFunction.accept(component);
    }


    /**
     * locate ext point implements
     *
     * @param targetClz extpoint interface
     * @param bizCondition biz condition
     * @param <ExtP>
     * @return ext point implement
     */
    protected abstract <ExtP> ExtP locateComponent(Class<ExtP> targetClz, BizCondition bizCondition);
}
