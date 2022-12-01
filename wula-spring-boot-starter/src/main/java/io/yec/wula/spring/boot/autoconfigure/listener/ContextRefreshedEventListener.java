package io.yec.wula.spring.boot.autoconfigure.listener;

import io.yec.wula.core.register.IExtensionRegister;
import io.yec.wula.spring.boot.autoconfigure.properties.WulaConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ContextRefreshedEventListener
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Slf4j
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * load json router config file in the classpath, parse and register to the IExtensionRouteRuleHolder
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext.getParent() == null) {
            WulaConfigProperties wulaConfigProperties;
            try {
                wulaConfigProperties = applicationContext.getBean(WulaConfigProperties.class);
            } catch (BeansException exception) {
                log.error("Get bean of type err: " + WulaConfigProperties.class);
                return;
            }
            String config = wulaConfigProperties.getConfig();
            applicationContext.getBean(IExtensionRegister.class).doRegister(parseConfig(config));
        }
    }

    /**
     * parse config to list
     * @param config
     * @return
     */
    private List<String> parseConfig(String config) {
        List<String> configLocations = Arrays.asList(StringUtils.trimArrayElements(StringUtils.commaDelimitedListToStringArray(config)));
        Collections.reverse(configLocations);
        return configLocations;
    }

}
