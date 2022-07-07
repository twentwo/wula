package io.yec.wula.example.identity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RaceEnum
 *
 * @author baijiu.yec
 * @since 2022/07/07
 */
@Getter
@AllArgsConstructor
public enum RaceEnum {

    /**
     * 黄种人
     */
    YELLOW("黄种人"),
    /**
     * 黑人
     */
    BLACK("黑人"),
    /**
     * 白人
     */
    WHITE("白人");

    private String desc;

}
