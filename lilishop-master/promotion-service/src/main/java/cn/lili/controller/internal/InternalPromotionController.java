package cn.lili.controller.internal;

import cn.lili.modules.distribution.entity.dos.DistributionGoods;
import cn.lili.modules.distribution.service.DistributionGoodsService;
import cn.lili.modules.distribution.service.DistributionOrderService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.order.aftersale.entity.dos.AfterSale;
import cn.lili.modules.order.order.entity.dos.StoreFlow;
import cn.lili.modules.promotion.entity.dos.Coupon;
import cn.lili.modules.promotion.entity.dos.KanjiaActivity;
import cn.lili.modules.promotion.entity.dos.KanjiaActivityGoods;
import cn.lili.modules.promotion.entity.dos.MemberCoupon;
import cn.lili.modules.promotion.entity.dos.Pintuan;
import cn.lili.modules.promotion.entity.dos.PromotionGoods;
import cn.lili.modules.promotion.entity.dto.GiftCardCashDeductPreviewDTO;
import cn.lili.modules.promotion.entity.dto.GiftCardOrderApplyDTO;
import cn.lili.modules.promotion.entity.dto.search.KanjiaActivitySearchParams;
import cn.lili.modules.promotion.entity.dto.search.MemberCouponSearchParams;
import cn.lili.modules.promotion.entity.dto.search.PromotionGoodsSearchParams;
import cn.lili.modules.promotion.entity.vos.GiftCardCashDeductPreviewVO;
import cn.lili.modules.promotion.entity.vos.GiftCardCashMemberCardVO;
import cn.lili.modules.promotion.entity.vos.PointsGoodsVO;
import cn.lili.modules.promotion.entity.vos.kanjia.KanjiaActivityVO;
import cn.lili.modules.promotion.service.CouponService;
import cn.lili.modules.promotion.service.FullDiscountService;
import cn.lili.modules.promotion.service.GiftCardCashService;
import cn.lili.modules.promotion.service.KanjiaActivityGoodsService;
import cn.lili.modules.promotion.service.KanjiaActivityService;
import cn.lili.modules.promotion.service.MemberCouponService;
import cn.lili.modules.promotion.service.PintuanService;
import cn.lili.modules.promotion.service.PointsGoodsService;
import cn.lili.modules.promotion.service.PromotionGoodsService;
import cn.lili.modules.promotion.service.PromotionService;
import cn.lili.modules.promotion.service.SeckillService;
import cn.lili.common.enums.PromotionTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * promotion-service 内部调用端点
 * <p>
 * 供 PromotionGoodsClient/MemberCouponClient/CouponClient/KanjiaActivityClient 等（common-api Feign）跨服务调用，
 * 直接委托本地 service，返回裸类型。
 * 注意：/internal/** 不受买家网关安全链保护，也不会被网关路由。
 */
@RestController
@RequestMapping("/internal/promotion")
public class InternalPromotionController {

    @Autowired
    private PromotionGoodsService promotionGoodsService;
    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private KanjiaActivityService kanjiaActivityService;
    @Autowired
    private KanjiaActivityGoodsService kanjiaActivityGoodsService;
    @Autowired
    private PointsGoodsService pointsGoodsService;
    @Autowired
    private PintuanService pintuanService;
    @Autowired
    private GiftCardCashService giftCardCashService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private DistributionGoodsService distributionGoodsService;
    @Autowired
    private DistributionOrderService distributionOrderService;
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private FullDiscountService fullDiscountService;

    // ==================== PromotionGoods ====================

    @PostMapping("/promotionGoods/getPromotionsGoods")
    public PromotionGoods getPromotionsGoods(@RequestBody PromotionGoodsSearchParams searchParams) {
        return promotionGoodsService.getPromotionsGoods(searchParams);
    }

    @PostMapping("/promotionGoods/getValidPromotionsGoodsPrice")
    public Double getValidPromotionsGoodsPrice(@RequestParam("skuId") String skuId,
                                               @RequestBody List<String> promotionTypes) {
        return promotionGoodsService.getValidPromotionsGoodsPrice(skuId, promotionTypes);
    }

    @GetMapping("/promotionGoods/stock")
    public Integer getPromotionGoodsStock(@RequestParam("type") PromotionTypeEnum typeEnum,
                                          @RequestParam("promotionId") String promotionId,
                                          @RequestParam("skuId") String skuId) {
        return promotionGoodsService.getPromotionGoodsStock(typeEnum, promotionId, skuId);
    }

    @PostMapping("/promotionGoods/updateStock")
    public void updatePromotionGoodsStock(@RequestParam("skuId") String skuId,
                                          @RequestParam("quantity") Integer quantity) {
        promotionGoodsService.updatePromotionGoodsStock(skuId, quantity);
    }

    @PostMapping("/promotionGoods/currentGoodsPromotion")
    public Map<String, Object> getCurrentGoodsPromotion(@RequestBody GoodsSku dataSku,
                                                        @RequestParam("cartType") String cartType) {
        return promotionGoodsService.getCurrentGoodsPromotion(dataSku, cartType);
    }

    // ==================== MemberCoupon ====================

    @GetMapping("/memberCoupon/listByMember")
    public List<MemberCoupon> getMemberCoupons(@RequestParam("memberId") String memberId) {
        return memberCouponService.getMemberCoupons(memberId);
    }

    @PostMapping("/memberCoupon/getOne")
    public MemberCoupon getMemberCoupon(@RequestBody MemberCouponSearchParams param) {
        return memberCouponService.getMemberCoupon(param);
    }

    @PostMapping("/memberCoupon/currentGoodsCanUse")
    public List<MemberCoupon> getCurrentGoodsCanUse(@RequestParam("memberId") String memberId,
                                                    @RequestBody List<String> couponIds,
                                                    @RequestParam("totalPrice") Double totalPrice) {
        return memberCouponService.getCurrentGoodsCanUse(memberId, couponIds, totalPrice);
    }

    @PostMapping("/memberCoupon/allScope")
    public List<MemberCoupon> getAllScopeMemberCoupon(@RequestParam("memberId") String memberId,
                                                      @RequestBody List<String> storeIds) {
        return memberCouponService.getAllScopeMemberCoupon(memberId, storeIds);
    }

    @GetMapping("/memberCoupon/num")
    public Long getMemberCouponNum(@RequestParam("memberId") String memberId,
                                   @RequestParam("couponId") String couponId) {
        return memberCouponService.getMemberCouponNum(memberId, couponId);
    }

    @PostMapping("/memberCoupon/used")
    public void used(@RequestParam("memberId") String memberId,
                     @RequestBody List<String> ids) {
        memberCouponService.used(memberId, ids);
    }

    @PostMapping("/memberCoupon/receiveCoupon")
    public void receiveCoupon(@RequestParam("couponId") String couponId,
                              @RequestParam("memberId") String memberId,
                              @RequestParam("memberName") String memberName) {
        memberCouponService.receiveCoupon(couponId, memberId, memberName);
    }

    // ==================== Coupon ====================

    @GetMapping("/coupon/{id}")
    public Coupon getCouponById(@PathVariable("id") String id) {
        return couponService.getById(id);
    }

    @PostMapping("/coupon/usedCoupon")
    public void usedCoupon(@RequestParam("couponId") String couponId,
                           @RequestParam("usedNum") Integer usedNum) {
        couponService.usedCoupon(couponId, usedNum);
    }

    @GetMapping("/coupon/listAll")
    public List<Coupon> listCoupon() {
        return couponService.list();
    }

    // ==================== KanjiaActivity ====================

    @PostMapping("/kanjiaActivity/getKanjiaActivity")
    public KanjiaActivity getKanjiaActivity(@RequestBody KanjiaActivitySearchParams kanJiaActivitySearchParams) {
        return kanjiaActivityService.getKanjiaActivity(kanJiaActivitySearchParams);
    }

    @PostMapping("/kanjiaActivity/getKanjiaActivityVO")
    public KanjiaActivityVO getKanjiaActivityVO(@RequestBody KanjiaActivitySearchParams kanJiaActivitySearchParams) {
        return kanjiaActivityService.getKanjiaActivityVO(kanJiaActivitySearchParams);
    }

    @PostMapping("/kanjiaActivity/end")
    public boolean endKanjiaActivity(@RequestParam("kanjiaId") String kanjiaId) {
        return kanjiaActivityService.endKanjiaActivity(kanjiaId);
    }

    // ==================== KanjiaActivityGoods ====================

    @GetMapping("/kanjiaActivityGoods/bySkuId")
    public KanjiaActivityGoods getKanjiaGoodsBySkuId(@RequestParam("skuId") String skuId) {
        return kanjiaActivityGoodsService.getKanjiaGoodsBySkuId(skuId);
    }

    // ==================== PointsGoods ====================

    @GetMapping("/pointsGoods/bySkuId")
    public PointsGoodsVO getPointsGoodsDetailBySkuId(@RequestParam("skuId") String skuId) {
        return pointsGoodsService.getPointsGoodsDetailBySkuId(skuId);
    }

    // ==================== Pintuan ====================

    @GetMapping("/pintuan/{id}")
    public Pintuan getPintuanById(@PathVariable("id") String id) {
        return pintuanService.getById(id);
    }

    // ==================== GiftCardCash ====================

    @GetMapping("/giftCardCash/listAvailable")
    public List<GiftCardCashMemberCardVO> listAvailableMemberCards(@RequestParam("memberId") String memberId) {
        return giftCardCashService.listAvailableMemberCards(memberId);
    }

    @PostMapping("/giftCardCash/previewDeduction")
    public GiftCardCashDeductPreviewVO previewMemberCardDeduction(@RequestParam("memberId") String memberId,
                                                                  @RequestBody GiftCardCashDeductPreviewDTO dto) {
        return giftCardCashService.previewMemberCardDeduction(memberId, dto);
    }

    @PostMapping("/giftCardCash/applyOnOrderCreate")
    public void applyGiftCardUsageOnOrderCreate(@RequestBody GiftCardOrderApplyDTO applyDTO) {
        giftCardCashService.applyGiftCardUsageOnOrderCreate(applyDTO.getTradeDTO(), applyDTO.getOrders(), applyDTO.getOrderItems());
    }

    @PostMapping("/giftCardCash/resolveChannelRefund")
    public BigDecimal resolveChannelRefundAfterGiftCard(@RequestParam("orderSn") String orderSn,
                                                        @RequestParam("orderAmount") BigDecimal orderAmount) {
        return giftCardCashService.resolveChannelRefundAfterGiftCard(orderSn, orderAmount);
    }

    @PostMapping("/giftCardCash/resolveAfterSaleRefund")
    public BigDecimal resolveChannelRefundAfterGiftCardForAfterSale(@RequestBody AfterSale afterSale) {
        return giftCardCashService.resolveChannelRefundAfterGiftCardForAfterSale(afterSale);
    }

    // ==================== Promotion ====================

    @GetMapping("/promotion/goodsSkuPromotionMap")
    public Map<String, Object> getGoodsSkuPromotionMap(@RequestParam("storeId") String storeId,
                                                       @RequestParam("goodsSkuId") String goodsSkuId) {
        return promotionService.getGoodsSkuPromotionMap(storeId, goodsSkuId);
    }

    // ==================== DistributionGoods ====================

    @PostMapping("/distributionGoods/distributionGoods")
    public List<DistributionGoods> distributionGoods(@RequestBody List<String> skuIds) {
        return distributionGoodsService.distributionGoods(skuIds);
    }

    // ==================== DistributionOrder ====================

    @PostMapping("/distributionOrder/completeOrder")
    public void completeOrder(@RequestBody StoreFlow storeFlow) {
        distributionOrderService.completeOrder(storeFlow);
    }

    // ==================== Stats ====================

    @GetMapping("/stats/countActive")
    public long countActive(@RequestParam("type") String type, @RequestParam("now") Long now) {
        QueryWrapper qw = Wrappers.query();
        qw.apply("start_time <= {0} AND (end_time IS NULL OR end_time >= {0})", new Date(now));
        qw.eq("delete_flag", false);
        switch (type) {
            case "COUPON":
                return couponService.count(qw);
            case "SECKILL":
                return seckillService.count(qw);
            case "PINTUAN":
                return pintuanService.count(qw);
            case "FULL_DISCOUNT":
                return fullDiscountService.count(qw);
            case "KANJIA":
                return kanjiaActivityGoodsService.count(qw);
            case "POINTS_GOODS":
                return pointsGoodsService.count(qw);
            default:
                return 0L;
        }
    }
}
