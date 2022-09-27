package io.yec.wula.core.extension;

import com.alibaba.spring.beans.factory.annotation.AnnotationBeanDefinitionRegistryPostProcessor;
import com.alibaba.spring.context.annotation.ExposingClassPathBeanDefinitionScanner;
import io.yec.wula.core.extension.annotation.ExtPoint;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;

import java.util.Map;
import java.util.Set;

/**
 * ExtPoint BeanDefinitionRegistryPostProcessor
 *
 * @author baijiu.yec
 * @since 2022/08/16
 */
public class ExtPointBeanDefinitionRegistryPostProcessor extends AnnotationBeanDefinitionRegistryPostProcessor {

    public ExtPointBeanDefinitionRegistryPostProcessor(Set<String> packagesToScan) {
        super(ExtPoint.class, packagesToScan);
    }

    @Override
    protected void registerSecondaryBeanDefinitions(ExposingClassPathBeanDefinitionScanner scanner, Map<String, AnnotatedBeanDefinition> primaryBeanDefinitions, String[] basePackages) {

    }

}
