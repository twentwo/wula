package io.yec.wula.spring.boot.autoconfigure;

import io.yec.wula.core.executor.ExtExtensionExecutorImpl;
import io.yec.wula.core.executor.ExtensionExecutor;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.identity.IdentityAssembler;
import io.yec.wula.core.register.GroupExtensionRegister;
import io.yec.wula.core.register.IExtensionRegister;
import io.yec.wula.core.routerule.holder.GroupExtensionRouteRuleHolder;
import io.yec.wula.core.routerule.holder.IExtensionRouteRuleHolder;
import io.yec.wula.spring.boot.autoconfigure.properties.WulaConfigProperties;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

/**
 * WulaAutoConfiguration
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
@ConditionalOnProperty(prefix = "wula.router", name = "enabled", matchIfMissing = true)
@ConditionalOnExpression("#{T(io.yec.wula.spring.boot.autoconfigure.utils.WulaUtils).resourceExists('classpath:bizRulesConfig*.*')}")
@ConditionalOnBean(value = {ExtensionPoint.class, IdentityAssembler.class})
@EnableConfigurationProperties(value = {
        WulaConfigProperties.class
})
@Configuration(proxyBeanMethods = false)
public class WulaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IExtensionRouteRuleHolder.class)
    public IExtensionRouteRuleHolder extensionRouteRuleHolder() {
        return new GroupExtensionRouteRuleHolder();
    }

    @Bean
    @ConditionalOnMissingBean(IExtensionRegister.class)
    public IExtensionRegister extensionRegister(IExtensionRouteRuleHolder extensionRouteRuleHolder, ApplicationContext applicationContext, ResourceLoader resourceLoader) {
        return new GroupExtensionRegister(extensionRouteRuleHolder, applicationContext, resourceLoader);
    }

    @Bean
    @ConditionalOnMissingBean(ExtensionExecutor.class)
    @ConditionalOnBean(IdentityAssembler.class)
    public ExtensionExecutor extensionExecutor(IExtensionRouteRuleHolder extensionRouteRuleHolder, IdentityAssembler identityAssembler) {
        return new ExtExtensionExecutorImpl(extensionRouteRuleHolder, identityAssembler);
    }

}
