package io.yec.wula.core.extension.context;

import lombok.ToString;

import java.util.Collections;
import java.util.Map;

/**
 * RouteContext
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
@ToString
public class RouteContext {

    Map<String, Object> pairs;

    public RouteContext(Map<String, Object> pairs) {
        this.pairs = Collections.unmodifiableMap(pairs);
    }

    public Map<String, Object> getPairs() {
        return pairs;
    }

}

