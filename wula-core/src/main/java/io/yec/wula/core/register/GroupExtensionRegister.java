package io.yec.wula.core.register;

import com.alibaba.fastjson.JSONObject;
import io.yec.wula.core.config.GroupRouteRuleJson;
import io.yec.wula.core.routerule.GroupExtensionRouteRule;
import io.yec.wula.core.routerule.holder.IExtensionRouteRuleHolder;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

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
        List<GroupExtensionRouteRule> extensionRouteRuleList = new ArrayList<>();
        for (String str : jsonStr) {
            List<GroupRouteRuleJson> routeRuleJson = JSONObject.parseArray(str, GroupRouteRuleJson.class);
            routeRuleJson.forEach(item -> extensionRouteRuleList.add(item.build(applicationContext)));
        }
        extensionRouteRuleHolder.setExtensionRouteRuleList(extensionRouteRuleList);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
