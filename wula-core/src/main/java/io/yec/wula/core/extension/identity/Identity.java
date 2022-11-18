package io.yec.wula.core.extension.identity;

import lombok.ToString;

import java.util.Collections;
import java.util.Map;

/**
 * Identity
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
@ToString
public class Identity {

    Map<String, Object> pairs;

    public Identity(Map<String, Object> pairs) {
        this.pairs = Collections.unmodifiableMap(pairs);
    }

    public Map<String, Object> getPairs() {
        return pairs;
    }

}

