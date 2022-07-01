package io.yec.wula.spring.boot.autoconfigure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static io.yec.wula.spring.boot.autoconfigure.utils.WulaUtils.WULA_ROUTER_PREFIX;


/**
 * FresnoConfigProperties
 *
 * @author baijiu.yec
 * @since 2022/04/29
 */
@Setter @Getter
@ConfigurationProperties(prefix = WULA_ROUTER_PREFIX)
public class WulaConfigProperties {

    private String enabled = "true";
    private String config = "classpath:bizRulesConfig*.json";

}
