package io.yec.wula.example.identity;


import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * ext point identity param
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Getter
@Builder
public class IdentityParam implements Serializable {

    private static final long serialVersionUID = -8723365298228222705L;

    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType;
    /**
     * 是否促销
     */
    private Boolean discounted;
    /**
     * 卖家id
     */
    private String sellerId;

}
