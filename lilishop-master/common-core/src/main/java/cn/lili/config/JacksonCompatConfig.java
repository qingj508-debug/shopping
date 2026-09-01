package cn.lili.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.cfg.ConstructorDetector;

/**
 * Jackson 3.x 兼容性配置（微服务架构全局生效）。
 *
 * Jackson 3.1 的 ConstructorDetector.EXPLICIT_ONLY 常量默认 requireCtorAnnotation=false
 * （与 Jackson 2.x 同名字面语义不同）：当实体编译带 -parameters 时，多参业务构造器的
 * 参数名可被内省，Jackson 会将其当作隐式 properties-based creator 参与反序列化，
 * 从而以 null 参数调用业务构造器（如 Order(CartVO, TradeDTO)）引发异常。
 *
 * 此处强制启用注解要求，恢复 Jackson 2 的严格语义：
 * 仅 @JsonCreator 显式标注的构造器参与反序列化，其余实体走无参构造器 + setter。
 *
 * @author lili
 */
@Configuration(proxyBeanMethods = false)
public class JacksonCompatConfig {

    @Bean
    public JsonMapperBuilderCustomizer lilishopConstructorDetectorCustomizer() {
        return builder -> builder.constructorDetector(ConstructorDetector.EXPLICIT_ONLY.withRequireAnnotation(true));
    }
}
