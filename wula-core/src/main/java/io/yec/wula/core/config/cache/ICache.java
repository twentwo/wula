package io.yec.wula.core.config.cache;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.context.RouteContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

/**
 * @author th
 * @since v1.4.0
 */
public interface ICache<K extends RouteContext, V extends ExtensionPoint> {

    /**
     * The constant log.
     */
    Logger log = LoggerFactory.getLogger(ICache.class);

    /**
     * Get v.
     *
     * @param key the key
     * @return the v
     */
    default V get(K key) {
        return null;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    default void add(K key, V value) {

    }

    /**
     * Remove.
     *
     * @param key the key
     */
    default void remove(K key) {

    }

    /**
     * Clear.
     */
    default void clear() {

    }

    /**
     * Generate key object.
     *
     * @param key the key
     * @return the object
     */
    default Object generateKey(K key) {
        Map<String, Object> pairs = key.getPairs();
        StringBuilder builder = new StringBuilder();
        try {
            for (String left : pairs.keySet()) {
                if (Objects.nonNull(pairs.get(left))) {
                    String s = String.format("%s%s", left, pairs.get(left));
                    builder.append(s);
                }
            }
            log.debug("generated Key : source={} -> key={}", key, builder);
        } catch (Exception e) {
            log.error("generate Key error", e);
        }
        return builder.toString();
    }


    /**
     * Record string.
     *
     * @return the string
     */
    default String record() {
        return null;
    }

}
