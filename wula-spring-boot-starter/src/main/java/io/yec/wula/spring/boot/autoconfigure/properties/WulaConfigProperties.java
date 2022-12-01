package io.yec.wula.spring.boot.autoconfigure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashSet;
import java.util.Set;

import static io.yec.wula.spring.boot.autoconfigure.utils.WulaUtils.WULA_ROUTER_PREFIX;


/**
 * WulaConfigProperties
 *
 * @author baijiu.yec
 * @since 2022/04/29
 */
@Setter @Getter
@ConfigurationProperties(prefix = WULA_ROUTER_PREFIX)
public class WulaConfigProperties {

    private String enabled = "true";
    private String config = "classpath:bizRulesConfig*.json,classpath:bizRulesConfig*.yml";

    @NestedConfigurationProperty
    private Scan scan = new Scan();

    public static class Scan {
        /**
         * The basePackages to scan , the multiple-value is delimited by comma
         *
         */
        private Set<String> basePackages = new LinkedHashSet<>();

        public Set<String> getBasePackages() {
            return basePackages;
        }

        public void setBasePackages(Set<String> basePackages) {
            this.basePackages = basePackages;
        }
    }

}
