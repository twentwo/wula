package io.yec.wula.core.extension.context;

/**
 * Biz Condition
 *
 * @author baijiu.yec
 * @since v1.4.0
 */
public interface BizCondition {
    /**
     * convert BizCondition to RouteContext
     * @return
     */
    RouteContext toRouteContext();
}
