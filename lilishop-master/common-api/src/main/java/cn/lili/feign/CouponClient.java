package cn.lili.feign;

import cn.lili.modules.promotion.entity.dos.Coupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * promotion-service 优惠券内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/coupon", contextId = "liliCouponClient")
public interface CouponClient {

    @GetMapping("/{id}")
    Coupon getById(@PathVariable("id") String id);

    @PostMapping("/usedCoupon")
    void usedCoupon(@RequestParam("couponId") String couponId,
                    @RequestParam("usedNum") Integer usedNum);

    @GetMapping("/listAll")
    List<Coupon> list();
}
