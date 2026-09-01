package cn.lili.feign;

import cn.lili.cache.CachePrefix;
import cn.lili.common.enums.PromotionTypeEnum;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.promotion.entity.dos.PromotionGoods;
import cn.lili.modules.promotion.entity.dto.search.PromotionGoodsSearchParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * promotion-service 促销商品内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/promotionGoods", contextId = "liliPromotionGoodsClient")
public interface PromotionGoodsClient {

    /**
     * 缓存商品库存key（静态方法，与 PromotionGoodsService 保持一致，供本地拼接缓存键）
     */
    static String getPromotionGoodsStockCacheKey(PromotionTypeEnum typeEnum, String promotionId, String skuId) {
        return CachePrefix.SKU_STOCK.getPrefix() + "_" + typeEnum.name() + "_" + promotionId + "_" + skuId;
    }

    @PostMapping("/getPromotionsGoods")
    PromotionGoods getPromotionsGoods(@RequestBody PromotionGoodsSearchParams searchParams);

    @PostMapping("/getValidPromotionsGoodsPrice")
    Double getValidPromotionsGoodsPrice(@RequestParam("skuId") String skuId,
                                        @RequestBody List<String> promotionTypes);

    @GetMapping("/stock")
    Integer getPromotionGoodsStock(@RequestParam("type") PromotionTypeEnum typeEnum,
                                   @RequestParam("promotionId") String promotionId,
                                   @RequestParam("skuId") String skuId);

    @PostMapping("/updateStock")
    void updatePromotionGoodsStock(@RequestParam("skuId") String skuId,
                                   @RequestParam("quantity") Integer quantity);

    @PostMapping("/currentGoodsPromotion")
    Map<String, Object> getCurrentGoodsPromotion(@RequestBody GoodsSku dataSku,
                                                 @RequestParam("cartType") String cartType);
}
