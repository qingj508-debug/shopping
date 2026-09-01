package cn.lili.feign;

import cn.lili.modules.promotion.entity.dos.KanjiaActivityGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * promotion-service 砍价商品内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/kanjiaActivityGoods", contextId = "liliKanjiaActivityGoodsClient")
public interface KanjiaActivityGoodsClient {

    @GetMapping("/bySkuId")
    KanjiaActivityGoods getKanjiaGoodsBySkuId(@RequestParam("skuId") String skuId);
}
