package io.yec.wula.core.config.cache;

/**
 * @author th
 * @since v1.4.0
 */
public interface ICache<K, V> {
    /**
     * fetch
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * add
     *
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * del
     *
     * @param key
     */
    void remove(K key);

    /**
     * clear
     */
    void clear();

    /**
     * generate
     *
     * @param key
     * @return
     */
    Object generateKey(K key);

    /**
     * record
     *
     * @return
     */
    default String record() {
        return null;
    }
}
