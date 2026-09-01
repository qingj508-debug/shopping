package cn.lili.feign;

import cn.lili.modules.promotion.entity.dos.MemberCoupon;
import cn.lili.modules.promotion.entity.dto.search.MemberCouponSearchParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * promotion-service 客户优惠券内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/memberCoupon", contextId = "liliMemberCouponClient")
public interface MemberCouponClient {

    @GetMapping("/listByMember")
    List<MemberCoupon> getMemberCoupons(@RequestParam("memberId") String memberId);

    @PostMapping("/getOne")
    MemberCoupon getMemberCoupon(@RequestBody MemberCouponSearchParams param);

    @PostMapping("/currentGoodsCanUse")
    List<MemberCoupon> getCurrentGoodsCanUse(@RequestParam("memberId") String memberId,
                                             @RequestBody List<String> couponIds,
                                             @RequestParam("totalPrice") Double totalPrice);

    @PostMapping("/allScope")
    List<MemberCoupon> getAllScopeMemberCoupon(@RequestParam("memberId") String memberId,
                                               @RequestBody List<String> storeIds);

    @GetMapping("/num")
    Long getMemberCouponNum(@RequestParam("memberId") String memberId,
                            @RequestParam("couponId") String couponId);

    @PostMapping("/used")
    void used(@RequestParam("memberId") String memberId,
              @RequestBody List<String> ids);

    @PostMapping("/receiveCoupon")
    void receiveCoupon(@RequestParam("couponId") String couponId,
                       @RequestParam("memberId") String memberId,
                       @RequestParam("memberName") String memberName);
}
