package io.yec.wula.example;

import io.yec.wula.core.config.cache.ICache;
import io.yec.wula.core.executor.ExtensionExecutor;
import io.yec.wula.example.entity.OrderEntity;
import io.yec.wula.example.entity.PersonEntity;
import io.yec.wula.example.extpoint.IOrderRouter;
import io.yec.wula.example.extpoint.IPersonRouter;
import io.yec.wula.example.identity.IdentityParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

/**
 * WulaApplication
 *
 * @author baijiu.yec
 * @since 2022/06/24
 */
@SpringBootApplication
public class WulaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(WulaApplication.class, args);
        ExtensionExecutor extensionExecutor = applicationContext.getBean(ExtensionExecutor.class);
        OrderEntity newOrderEntity = OrderEntity.createNewOrderEntity();
        for (int i = 0; i < Integer.SIZE; i++) {
        extensionExecutor.executeVoid(IOrderRouter.class,
                IdentityParam.builder()
                        .businessType(newOrderEntity.getBusinessType())
                        .discounted(false)
                        .sellerId("618")
                        .build(),
                orderRouter -> orderRouter.wula(newOrderEntity));
        }

        PersonEntity yellowPersonEntity = PersonEntity.createYellowPersonEntity();
        String name = extensionExecutor.execute(IPersonRouter.class,
                IdentityParam.builder()
                        .raceEnum(yellowPersonEntity.getRaceEnum())
                        .build(),
                personRouter -> personRouter.desc(yellowPersonEntity));
        Assert.isTrue(yellowPersonEntity.toString().equals(name), "not equals");

        ICache cache = applicationContext.getBean(ICache.class);
        System.out.println(cache.record());

    }

}
