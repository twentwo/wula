package io.yec.wula.example.identity;

import io.yec.wula.core.extension.identity.Identity;
import io.yec.wula.core.extension.identity.IdentityAssembler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * IdentityAssemblerImpl
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Component("identityAssembler")
public class IdentityAssemblerImpl implements IdentityAssembler {

    @Override
    public Identity assemble(Object object) {
        IdentityParam identityParam = (IdentityParam) object;
        Map<String, Object> pairs = new HashMap<>(8);
        pairs.put("businessType", Objects.nonNull(identityParam.getBusinessType()) ? identityParam.getBusinessType().name() : null);
        pairs.put("discounted", identityParam.getDiscounted());
        pairs.put("sellerId", identityParam.getSellerId());
        return new Identity(pairs);
    }

}
