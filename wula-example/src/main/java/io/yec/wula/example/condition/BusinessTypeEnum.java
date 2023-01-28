package io.yec.wula.example.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BusinessTypeEnum
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Getter
@AllArgsConstructor
public enum BusinessTypeEnum {

    /**
     * 新品
     */
    NEW("新品"),
    /**
     * 普品
     */
    NORMAL("普品");

    private String desc;

}
