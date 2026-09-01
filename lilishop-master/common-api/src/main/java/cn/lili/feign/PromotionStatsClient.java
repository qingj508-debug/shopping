package cn.lili.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * promotion-service 营销统计内部调用 Feign 客户端
 * <p>
 * 供 statistics-service 营销概况统计使用，端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/stats", contextId = "liliPromotionStatsClient")
public interface PromotionStatsClient {

    /**
     * 统计当前生效中的促销活动数量
     *
     * @param type 促销类型（COUPON/SECKILL/PINTUAN/FULL_DISCOUNT/KANJIA/POINTS_GOODS）
     * @param now  当前时间戳（毫秒）
     * @return 活动数量
     */
    @GetMapping("/countActive")
    long countActive(@RequestParam("type") String type, @RequestParam("now") Long now);
}
