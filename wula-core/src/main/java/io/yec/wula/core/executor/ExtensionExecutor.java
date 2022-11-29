package io.yec.wula.core.executor;

import io.yec.wula.core.extension.context.BizCondition;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * ExtensionExecutor
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public interface ExtensionExecutor {

    <R, ExtP> R execute(Class<ExtP> targetClz, BizCondition bizCondition, Function<ExtP, R> exeFunction);


    /**
     * Execute extensions without Response
     *
     * @param targetClz   extpoint interface
     * @param bizCondition biz condition
     * @param exeFunction extpoint invoke method
     * @param <ExtP>      Parameter Type
     */
    <ExtP> void executeVoid(Class<ExtP> targetClz, BizCondition bizCondition, Consumer<ExtP> exeFunction);
}
