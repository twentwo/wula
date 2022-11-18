package io.yec.wula.core.extension.identity;

/**
 * IdentityAssemblerImpl
 *
 * @author baijiu.yec
 * @since v1.4.0
 */
public class IdentityAssemblerImpl implements IdentityAssembler {

    @Override
    public Identity assemble(BizIdentity bizIdentity) {
        return bizIdentity.toIdentity();
    }

}
