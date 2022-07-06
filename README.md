# wula

A Strategy Route Framework

## Usage

spring-boot application support

### Maven

maven dependency import

```xml

<dependency>
    <groupId>io.yec</groupId>
    <artifactId>wula-spring-boot-starter</artifactId>
    <version>${wula.version}</version>
</dependency>
```

### Strategy Bean

create different strategy bean with a logical component name

```java
@Slf4j
@Component("newOrderRouter")
public class NewOrderRouter implements IOrderRouter {

    @Override
    public void wula(OrderEntity orderEntity) {
        log.info("NewOrderRouter = {}", orderEntity);
    }

}
// ...
```

### bizRulesConfig

define strategy route rules: bizRulesConfig*.json

```json
[
  {
    "group": "io.yec.wula.example.extpoint.IOrderRouter",
    "def": [
      {
        "beanName": "newOrderRouter",
        "extEl": "['businessType'] == 'NEW' && ['discounted'] == false && ['sellerId'] == '618'",
        "desc": "新品"
      },
      {
        "beanName": "normalOrderRouter",
        "extEl": "['businessType'] == 'NORMAL' && ['discounted'] == true ",
        "desc": "普品"
      },
      {
        "beanName": "defaultOrderRouter",
        "extEl": "['businessType'] == null",
        "desc": "默认"
      }
    ]
  }
]
```

### ExtensionExecutor

strategy route executor usage

```java
@SpringBootApplication
public class WulaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(WulaApplication.class, args);
        ExtensionExecutor extensionExecutor = applicationContext.getBean(ExtensionExecutor.class);
        OrderEntity newOrderEntity = OrderEntity.createNewOrderEntity();
        extensionExecutor.executeVoid(IOrderRouter.class,
                // match rule param
                IdentityParam.builder()
                        .businessType(newOrderEntity.getBusinessType())
                        .discounted(false)
                        .sellerId("618")
                        .build(),
                orderRouter -> orderRouter.wula(newOrderEntity));
    }

}
```