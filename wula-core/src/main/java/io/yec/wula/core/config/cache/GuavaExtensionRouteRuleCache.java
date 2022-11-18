package io.yec.wula.core.config.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.identity.Identity;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author th
 * @since v1.4.0
 */
@Slf4j
public class GuavaExtensionRouteRuleCache extends AbstractCache implements ICache<Identity, ExtensionPoint> {

    private final Cache<Object, ExtensionPoint> cache;

    public GuavaExtensionRouteRuleCache(boolean recordStats) {
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(100L)
                .expireAfterAccess(10L, TimeUnit.MINUTES)
                .recordStats();
        if (recordStats) {
            cacheBuilder.recordStats();
        }
        cache = cacheBuilder.build();
    }

    @Override
    public ExtensionPoint get(Identity identity) {
        try {
            Object key = generateKey(identity);
            return cache.getIfPresent(key);
        } catch (Exception e) {
            log.error("cache get error", e);
        }
        return null;
    }

    @Override
    public void add(Identity identity, ExtensionPoint extensionPoint) {
        try {
            Object key = generateKey(identity);
            cache.put(key, extensionPoint);
        } catch (Exception e) {
            log.error("cache add error", e);
        }

    }

    @Override
    public void remove(Identity identity) {
        Object key = generateKey(identity);
        cache.invalidate(key);
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public String record() {
        if (Objects.nonNull(cache)) {
            return cache.stats().toString();
        }
        return null;
    }

}
