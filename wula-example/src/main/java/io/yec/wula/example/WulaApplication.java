package io.yec.wula.example;

import io.yec.wula.core.executor.ExtensionExecutor;
import io.yec.wula.example.entity.OrderEntity;
import io.yec.wula.example.entity.PersonEntity;
import io.yec.wula.example.extpoint.IOrderRouter;
import io.yec.wula.example.extpoint.IPersonRouter;
import io.yec.wula.example.identity.IdentityParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
        extensionExecutor.executeVoid(IOrderRouter.class,
                IdentityParam.builder()
                        .businessType(newOrderEntity.getBusinessType())
                        .discounted(false)
                        .sellerId("618")
                        .build(),
                orderRouter -> orderRouter.wula(newOrderEntity));

        PersonEntity yellowPersonEntity = PersonEntity.createYellowPersonEntity();
        String name = extensionExecutor.execute(IPersonRouter.class,
                IdentityParam.builder()
                        .raceEnum(yellowPersonEntity.getRaceEnum())
                        .build(),
                personRouter -> personRouter.desc(yellowPersonEntity));
        assert yellowPersonEntity.toString().equals(name);
    }

}
