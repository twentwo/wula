package io.yec.wula.core.config.cache;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.context.RouteContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * AbstractCache
 *
 * @author baijiu.yec
 * @since v1.4.0
 */
@Slf4j
public class AbstractCache implements ICache<RouteContext, ExtensionPoint> {

    @Override
    public ExtensionPoint get(RouteContext key) {
        return null;
    }

    @Override
    public void add(RouteContext key, ExtensionPoint value) {

    }

    @Override
    public void remove(RouteContext key) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Object generateKey(RouteContext key) {
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

}
