package cn.lili.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * promotion-service 促销业务内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/promotion", contextId = "liliPromotionServiceClient")
public interface PromotionServiceClient {

    @GetMapping("/goodsSkuPromotionMap")
    Map<String, Object> getGoodsSkuPromotionMap(@RequestParam("storeId") String storeId,
                                                @RequestParam("goodsSkuId") String goodsSkuId);
}
