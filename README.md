# wula

A Strategy Route Framework

- original inspiration: https://github.com/alibaba/COLA.git

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
@ExtPoint("newOrderRouter")
public class NewOrderRouter implements IOrderRouter {

    @Override
    public void wula(OrderEntity orderEntity) {
        log.info("NewOrderRouter = {}", orderEntity);
    }

}
// ...
```

### bizRulesConfig

define strategy route rules, support both: 
- bizRulesConfig*.json
- bizRulesConfig*.yml

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

or

```yaml
---
- group: io.yec.wula.example.extpoint.IOrderRouter
  def:
    - beanName: newOrderRouter
      extEl: "['businessType'] == 'NEW' && ['discounted'] == false && ['sellerId'] == '618'"
      desc: 新品
    - beanName: normalOrderRouter
      extEl: "['businessType'] == 'NORMAL' && ['discounted'] == true"
      desc: 普品
    - beanName: defaultOrderRouter
      extEl: "['businessType'] == null"
      desc: 默认
- group: io.yec.wula.example.extpoint.IPersonRouter
  def:
    - beanName: yellowRouter
      extEl: "['raceEnum'] == 'YELLOW'"
      desc: 黄种人
```

### BizIdentity

define BizIdentity

```java
@Getter
@Builder
public class IdentityParam implements BizIdentity {

    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType;
    /**
     * 是否促销
     */
    private Boolean discounted;
    /**
     * 卖家id
     */
    private String sellerId;

    /**
     * 人种
     */
    private RaceEnum raceEnum;
    /**
     * 是否歪果仁
     */
    private Boolean foreign;
    /**
     * 家乡
     */
    private String hometown;


    @Override
    public Identity toIdentity() {
        Map<String, Object> pairs = new HashMap<>(64);
        pairs.put("businessType", Objects.nonNull(this.getBusinessType()) ? this.getBusinessType().name() : null);
        pairs.put("discounted", this.getDiscounted());
        pairs.put("sellerId", this.getSellerId());
        pairs.put("raceEnum", Objects.nonNull(this.getRaceEnum()) ? this.getRaceEnum().name() : null);
        pairs.put("foreign", this.getForeign());
        pairs.put("hometown", this.getHometown());
        return new Identity(pairs);
    }

}
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