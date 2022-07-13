package io.yec.wula.example.extpoint;

import io.yec.wula.core.annotation.ExtPoint;
import io.yec.wula.example.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * Normal OrderRouter
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@Slf4j
@ExtPoint("normalOrderRouter")
public class NormalOrderRouter implements IOrderRouter {

    @Override
    public void wula(OrderEntity orderEntity) {
        log.info("NormalOrderRouter = {}", orderEntity);
    }

}
