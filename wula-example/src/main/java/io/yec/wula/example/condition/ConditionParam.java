package io.yec.wula.example.condition;


import io.yec.wula.core.extension.context.BizCondition;
import io.yec.wula.core.extension.context.RouteContext;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ext point condition param
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Getter
@Builder
public class ConditionParam implements BizCondition {

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

    /**
     * 人种
     */
    private RaceEnum raceEnum;
    /**
     * 是否歪果仁
     */
    private Boolean foreign;
    /**
     * 家乡
     */
    private String hometown;

    @Override
    public RouteContext toRouteContext() {
        Map<String, Object> pairs = new HashMap<>(64);
        pairs.put("businessType", Objects.nonNull(this.getBusinessType()) ? this.getBusinessType().name() : null);
        pairs.put("discounted", this.getDiscounted());
        pairs.put("sellerId", this.getSellerId());
        pairs.put("raceEnum", Objects.nonNull(this.getRaceEnum()) ? this.getRaceEnum().name() : null);
        pairs.put("foreign", this.getForeign());
        pairs.put("hometown", this.getHometown());
        return new RouteContext(pairs);
    }

}
