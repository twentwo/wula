package io.yec.wula.core.config.ruledef;

import io.yec.wula.core.routerule.IExtensionRouteRule;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.ExpressionParser;

/**
 * Rule Definition
 *
 * @author baijiu.yec
 * @since v2.6.0
 */
public interface RuleDefinition<Rule extends IExtensionRouteRule> {


    /**
     * To rule rule.
     *
     * @param beanFactory      the bean factory
     * @param expressionParser the expression parser
     * @return the rule
     */
    Rule toRule(BeanFactory beanFactory, ExpressionParser expressionParser);

}
