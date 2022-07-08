package io.yec.wula.spring.boot.autoconfigure.listener;

import io.yec.wula.core.register.IExtensionRegister;
import io.yec.wula.spring.boot.autoconfigure.properties.WulaConfigProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * ContextRefreshedEventListener
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * load json router config file in the classpath, parse and register to the IExtensionRouteRuleHolder
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //判断是否执行过，执行过则不再执行
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext.getParent() == null) {
            String config = applicationContext.getBean(WulaConfigProperties.class).getConfig();
            applicationContext.getBean(IExtensionRegister.class).doRegister(parseConfig(config));
        }
    }

    private List<String> parseConfig(String config) {
        List<String> configLocations = Arrays.asList(StringUtils.trimArrayElements(StringUtils.commaDelimitedListToStringArray(config)));
        Collections.reverse(configLocations);
        return configLocations;
    }

}
