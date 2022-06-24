package io.yec.wula.core.extension.identity;

/**
 * biz identity assembly
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public interface IdentityAssembler {

    /**
     * assemble route identity by biz info
     *
     * @param object
     * @return
     */
    Identity assemble(Object object);
}
