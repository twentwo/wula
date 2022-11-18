package io.yec.wula.spring.boot.autoconfigure;

import io.yec.wula.core.config.cache.ICache;
import io.yec.wula.core.config.cache.SimpleExtensionRouteRuleCache;
import io.yec.wula.core.executor.ExtExtensionExecutorImpl;
import io.yec.wula.core.executor.ExtensionExecutor;
import io.yec.wula.core.extension.ExtPointBeanDefinitionRegistryPostProcessor;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.identity.IdentityAssembler;
import io.yec.wula.core.extension.identity.IdentityAssemblerImpl;
import io.yec.wula.core.register.GroupExtensionRegister;
import io.yec.wula.core.register.IExtensionRegister;
import io.yec.wula.core.routerule.holder.GroupExtensionRouteRuleHolder;
import io.yec.wula.core.routerule.holder.IExtensionRouteRuleHolder;
import io.yec.wula.spring.boot.autoconfigure.properties.WulaConfigProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import java.util.Objects;
import java.util.Set;

import static io.yec.wula.spring.boot.autoconfigure.utils.WulaUtils.*;

/**
 * WulaAutoConfiguration
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
@EnableConfigurationProperties(value = {
        WulaConfigProperties.class
})
@ConditionalOnProperty(prefix = WULA_ROUTER_PREFIX, name = "enabled", matchIfMissing = true)
@ConditionalOnExpression("#{T(io.yec.wula.spring.boot.autoconfigure.utils.WulaUtils).resourceExists('classpath:bizRulesConfig*.*')}")
@Configuration(proxyBeanMethods = false)
public class WulaAutoConfiguration {

    /**
     * The bean is used to scan the packages of Wula ExtPoint classes
     *
     * @param environment {@link Environment} instance
     * @return non-null {@link Set}
     * @since 1.3.0
     */
    @ConditionalOnProperty(prefix = WULA_SCAN_PREFIX, name = BASE_PACKAGES_PROPERTY_NAME)
    @ConditionalOnMissingBean(name = BASE_PACKAGES_BEAN_NAME)
    @Bean(name = BASE_PACKAGES_BEAN_NAME)
    public Set<String> wulaBasePackages(Environment environment) {
        WulaConfigProperties wulaConfigProperties = Binder.get(environment)
                .bind(WULA_ROUTER_PREFIX, WulaConfigProperties.class)
                .orElse(null);
        return Objects.isNull(wulaConfigProperties) ? null : wulaConfigProperties.getScan().getBasePackages();
    }

    /**
     * Creates {@link ExtPointBeanDefinitionRegistryPostProcessor} Bean
     *
     * @param packagesToScan the packages to scan
     * @return {@link ExtPointBeanDefinitionRegistryPostProcessor}
     */
    @ConditionalOnBean(name = BASE_PACKAGES_BEAN_NAME)
    @ConditionalOnMissingBean(value = {ExtensionPoint.class})
    @Bean
    public ExtPointBeanDefinitionRegistryPostProcessor extPointAnnotationBeanPostProcessor(@Qualifier(BASE_PACKAGES_BEAN_NAME) Set<String> packagesToScan) {
        return new ExtPointBeanDefinitionRegistryPostProcessor(packagesToScan);
    }

    @Bean
    @ConditionalOnMissingBean(IExtensionRouteRuleHolder.class)
    @ConditionalOnBean(ExtPointBeanDefinitionRegistryPostProcessor.class)
    public IExtensionRouteRuleHolder extensionRouteRuleHolder() {
        return new GroupExtensionRouteRuleHolder();
    }

    @Bean
    @ConditionalOnMissingBean(IExtensionRegister.class)
    @ConditionalOnBean(IExtensionRouteRuleHolder.class)
    public IExtensionRegister extensionRegister(IExtensionRouteRuleHolder extensionRouteRuleHolder, ApplicationContext applicationContext, ResourceLoader resourceLoader) {
        return new GroupExtensionRegister(extensionRouteRuleHolder, applicationContext, resourceLoader);
    }

    @Bean
    @ConditionalOnMissingBean(ICache.class)
    @ConditionalOnBean(IExtensionRouteRuleHolder.class)
    public ICache extensionCache() {
        return new SimpleExtensionRouteRuleCache(true);
    }

    @Bean
    @ConditionalOnMissingBean(IdentityAssembler.class)
    public IdentityAssembler identityAssembler() {
        return new IdentityAssemblerImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ExtensionExecutor.class)
    @ConditionalOnBean({IExtensionRouteRuleHolder.class, ICache.class, IdentityAssembler.class})
    public ExtensionExecutor extensionExecutor(IExtensionRouteRuleHolder extensionRouteRuleHolder, IdentityAssembler identityAssembler, ICache cache) {
        return new ExtExtensionExecutorImpl(extensionRouteRuleHolder, identityAssembler, cache);
    }

}
