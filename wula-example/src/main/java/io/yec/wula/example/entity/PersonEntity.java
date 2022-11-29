package io.yec.wula.example.entity;

import io.yec.wula.example.condition.RaceEnum;
import lombok.Builder;
import lombok.Data;

/**
 * person
 *
 * @author baijiu.yec
 * @since 2022/07/07
 */
@Builder
@Data
public class PersonEntity {
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

    public static final PersonEntity createYellowPersonEntity() {
        return PersonEntity.builder()
                .raceEnum(RaceEnum.YELLOW)
                .foreign(false)
                .hometown("中国")
                .build();

    }

}
