package io.yec.wula.core.executor;

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
     * @param targetClz   extpoint interface
     * @param entity      biz entity
     * @param exeFunction extpoint invoke method
     * @param <R>         Response Type
     * @param <T>         Parameter Type
     * @return extension invoke result
     */
    @Override
    public <R, T> R execute(Class<T> targetClz, Object entity, Function<T, R> exeFunction) {
        T component = locateComponent(targetClz, entity);
        return exeFunction.apply(component);
    }


    /**
     * Execute extensions without Response
     *
     * @param targetClz   extpoint interface
     * @param entity      biz entity
     * @param exeFunction extpoint invoke method
     * @param <T>         Parameter Type
     */
    @Override
    public <T> void executeVoid(Class<T> targetClz, Object entity, Consumer<T> exeFunction) {
        T component = locateComponent(targetClz, entity);
        exeFunction.accept(component);
    }


    /**
     * locate ext point implements
     *
     * @param targetClz extpoint interface
     * @param entity    biz entity
     * @param <C>
     * @return ext point implement
     */
    protected abstract <C> C locateComponent(Class<C> targetClz, Object entity);

}
