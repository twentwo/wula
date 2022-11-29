package io.yec.wula.example.entity;

import io.yec.wula.example.condition.BusinessTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * Order
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Builder
@Data
public class OrderEntity {
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

    public static final OrderEntity createNewOrderEntity() {
        return OrderEntity.builder()
                .businessType(BusinessTypeEnum.NEW)
                .discounted(Boolean.FALSE)
                .sellerId("618")
                .build();

    }

}
