package io.yec.wula.example.extpoint;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.example.entity.OrderEntity;

/**
 * IOrderRouter ExtensionPoint
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
public interface IOrderRouter extends ExtensionPoint {
    void wula(OrderEntity orderEntity);
}
