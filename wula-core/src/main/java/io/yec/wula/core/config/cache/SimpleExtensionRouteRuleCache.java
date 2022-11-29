package io.yec.wula.core.config.cache;

import com.google.common.base.Supplier;
import com.google.common.cache.AbstractCache.SimpleStatsCounter;
import com.google.common.cache.AbstractCache.StatsCounter;
import com.google.common.cache.CacheStats;
import com.google.common.collect.Maps;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.context.RouteContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * Simple ExtensionRouteRuleCache
 *
 * @author baijiu.yec
 * @since v1.4.0
 */
@Slf4j
public class SimpleExtensionRouteRuleCache extends AbstractCache implements ICache<RouteContext, ExtensionPoint> {

    private final Map<Object, ExtensionPoint> cache;

    private final StatsCounter globalStatsCounter;

    public SimpleExtensionRouteRuleCache(boolean recordStats) {
        this.cache = Maps.newConcurrentMap();
        this.globalStatsCounter = recordStats ? CACHE_STATS_COUNTER.get() : NULL_STATS_COUNTER.get();
    }

    @Override
    public ExtensionPoint get(RouteContext routeContext) {
        try {
            Object key = generateKey(routeContext);
            ExtensionPoint value = cache.get(key);
            if (value == null) {
                globalStatsCounter.recordMisses(1);
            } else {
                globalStatsCounter.recordHits(1);
            }
            return value;
        } catch (Exception e) {
            log.error("cache get error", e);
        }
        return null;
    }

    @Override
    public void add(RouteContext routeContext, ExtensionPoint extensionPoint) {
        try {
            Object key = generateKey(routeContext);
            cache.putIfAbsent(key, extensionPoint);
        } catch (Exception e) {
            log.error("cache add error", e);
        }
    }

    @Override
    public void remove(RouteContext routeContext) {
        Object key = generateKey(routeContext);
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public String record() {
        if (Objects.nonNull(globalStatsCounter)) {
            return globalStatsCounter.snapshot().toString();
        }
        return null;
    }

    static final CacheStats EMPTY_STATS = new CacheStats(0, 0, 0, 0, 0, 0);

    static final Supplier<? extends StatsCounter> NULL_STATS_COUNTER =
            () -> new StatsCounter() {
                        @Override
                        public void recordHits(int count) {}

                        @Override
                        public void recordMisses(int count) {}

                        @SuppressWarnings("GoodTime") // b/122668874
                        @Override
                        public void recordLoadSuccess(long loadTime) {}

                        @SuppressWarnings("GoodTime") // b/122668874
                        @Override
                        public void recordLoadException(long loadTime) {}

                        @Override
                        public void recordEviction() {}

                        @Override
                        public CacheStats snapshot() {
                            return EMPTY_STATS;
                        }
                    };

    static final Supplier<StatsCounter> CACHE_STATS_COUNTER =
            () -> new SimpleStatsCounter();

}
