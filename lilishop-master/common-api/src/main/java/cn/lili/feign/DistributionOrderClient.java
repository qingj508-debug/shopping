package cn.lili.feign;

import cn.lili.modules.order.order.entity.dos.StoreFlow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * promotion-service 分销订单内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/distributionOrder", contextId = "liliDistributionOrderClient")
public interface DistributionOrderClient {

    @PostMapping("/completeOrder")
    void completeOrder(@RequestBody StoreFlow storeFlow);
}
