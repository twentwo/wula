package io.yec.wula.core.config.ruledef;


import io.yec.wula.core.exception.ExtException;
import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.core.extension.annotation.ExtPoint;
import io.yec.wula.core.routerule.ExtensionRouteRule;
import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.ExpressionParser;

/**
 * ExtensionRouteRuleDef
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
@Data
public class ExtensionRouteRuleDef implements RuleDefinition<ExtensionRouteRule>  {

    String extEl;
    String beanName;
    String desc;
    /**
     * order define
     */
    Integer order;

    @Override
    public ExtensionRouteRule toRule(BeanFactory beanFactory, ExpressionParser expressionParser) {
        Object extensionObj = beanFactory.getBean(this.getBeanName());
        if (!ExtensionPoint.class.isAssignableFrom(extensionObj.getClass())) {
            throw new ExtException("extension init fail : extension didn't implements ExtensionPoint");
        }
        if (!AnnotationUtils.isCandidateClass(extensionObj.getClass(), ExtPoint.class)) {
            throw new ExtException("extension init fail : extension didn't annotated by  @ExtPoint");
        }
        return ExtensionRouteRule.builder()
                .extensionPoint((ExtensionPoint) extensionObj)
                .expression(expressionParser.parseExpression(this.getExtEl()))
                .order(this.getOrder())
                .build();
    }

}
