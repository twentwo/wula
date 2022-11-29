package io.yec.wula.core.extension.context;

/**
 * RouteContext assemble
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public interface RouteContextAssembler {

    /**
     * assemble route context by biz condition
     *
     * @param bizCondition
     * @return
     */
    RouteContext assemble(BizCondition bizCondition);
}
