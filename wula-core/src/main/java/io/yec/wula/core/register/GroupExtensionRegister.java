package io.yec.wula.core.register;

import com.alibaba.fastjson.JSONObject;
import io.yec.wula.core.config.GroupRouteRuleJson;
import io.yec.wula.core.routerule.GroupExtensionRouteRule;
import io.yec.wula.core.routerule.holder.IExtensionRouteRuleHolder;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GroupExtensionRegister
 *
 * @author baijiu.yec
 * @since 2022/06/23
 */
public class GroupExtensionRegister implements IExtensionRegister<GroupExtensionRouteRule>, ApplicationContextAware {

    @Setter
    private IExtensionRouteRuleHolder extensionRouteRuleHolder;
    private ApplicationContext applicationContext;

    public GroupExtensionRegister(IExtensionRouteRuleHolder extensionRouteRuleHolder) {
        this.extensionRouteRuleHolder = extensionRouteRuleHolder;
    }

    @Override
    public void doRegister(List<String> jsonStr) {
        Map<Class<?>, GroupExtensionRouteRule> extensionRouteRuleGroup = new HashMap<>(128);
        for (String str : jsonStr) {
            List<GroupRouteRuleJson> routeRuleJson = JSONObject.parseArray(str, GroupRouteRuleJson.class);
            routeRuleJson.forEach(item -> extensionRouteRuleGroup.put(GroupRouteRuleJson.getClass(item.getGroup()), item.build(applicationContext)));
        }
        extensionRouteRuleHolder.setExtensionRouteRuleGroup(extensionRouteRuleGroup);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
