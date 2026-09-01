package cn.lili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 平台管理 + 商家端 API 独立启动入口。
 * 聚合部署：依赖 8 个业务服务 jar，排除各服务自身的 Application 配置类（避免
 * 同名 primaryTaskExecutor 等 bean 冲突），Feign client 由本类统一启用。
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.lili",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "cn\\.lili\\.(goods|member|order|payment|promotion|statistics|store|system)\\.\\w+Application"
        ))
@EnableCaching
@EnableAsync
@EnableFeignClients(basePackages = "cn.lili.feign")
public class ManagerApiApplication {

    @Primary
    @Bean
    public TaskExecutor primaryTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("lili-manager-async-");
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(ManagerApiApplication.class, args);
    }
}
