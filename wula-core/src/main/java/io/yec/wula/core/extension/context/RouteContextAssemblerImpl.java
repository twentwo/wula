package io.yec.wula.core.extension.context;

/**
 * RouteContextAssembler Impl
 *
 * @author baijiu.yec
 * @since v1.4.0
 */
public class RouteContextAssemblerImpl implements RouteContextAssembler {

    @Override
    public RouteContext assemble(BizCondition bizCondition) {
        return bizCondition.toRouteContext();
    }

}
