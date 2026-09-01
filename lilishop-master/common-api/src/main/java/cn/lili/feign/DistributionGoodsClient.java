package cn.lili.feign;

import cn.lili.modules.distribution.entity.dos.DistributionGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * promotion-service 分销商品内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/distributionGoods", contextId = "liliDistributionGoodsClient")
public interface DistributionGoodsClient {

    @PostMapping("/distributionGoods")
    List<DistributionGoods> distributionGoods(@RequestBody List<String> skuIds);
}
