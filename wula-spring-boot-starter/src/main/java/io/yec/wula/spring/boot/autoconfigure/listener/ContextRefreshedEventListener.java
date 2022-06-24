package io.yec.wula.spring.boot.autoconfigure.listener;

import io.yec.wula.core.common.IOUtils;
import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.register.IExtensionRegister;
import io.yec.wula.spring.boot.autoconfigure.properties.WulaConfigProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
            Resource[] resources;
            try {
                resources = applicationContext.getResources(applicationContext.getBean(WulaConfigProperties.class).getConfig());
            } catch (IOException e) {
                throw new ExtException("extension config init exception :" + e.getMessage());
            }
            List<String> strings = new ArrayList<>();
            for (Resource resource : resources) {
                try (InputStream inputStream = resource.getInputStream()) {
                    strings.add(IOUtils.convertString(inputStream));
                } catch (IOException e) {
                    throw new ExtException("extension config init exception :" + e.getMessage());
                }
            }
            applicationContext.getBean(IExtensionRegister.class).doRegister(strings);
        }
    }

}
