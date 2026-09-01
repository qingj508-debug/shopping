package cn.lili.feign;

import cn.lili.modules.promotion.entity.dos.Pintuan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * promotion-service 拼团内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/pintuan", contextId = "liliPintuanClient")
public interface PintuanClient {

    @GetMapping("/{id}")
    Pintuan getById(@PathVariable("id") String id);
}
