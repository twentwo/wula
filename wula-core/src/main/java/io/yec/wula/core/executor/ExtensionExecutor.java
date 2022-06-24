package io.yec.wula.core.executor;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * ExtensionExecutor
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public interface ExtensionExecutor {

    <R, T> R execute(Class<T> targetClz, Object entity, Function<T, R> exeFunction);

    /**
     * Execute extensions without Response
     *
     * @param targetClz   extpoint interface
     * @param entity      biz entity
     * @param exeFunction extpoint invoke method
     * @param <T>         Parameter Type
     */
    <T> void executeVoid(Class<T> targetClz, Object entity, Consumer<T> exeFunction);

}
