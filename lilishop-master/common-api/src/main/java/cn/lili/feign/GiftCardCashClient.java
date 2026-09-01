package cn.lili.feign;

import cn.lili.modules.order.aftersale.entity.dos.AfterSale;
import cn.lili.modules.promotion.entity.dto.GiftCardCashDeductPreviewDTO;
import cn.lili.modules.promotion.entity.dto.GiftCardOrderApplyDTO;
import cn.lili.modules.promotion.entity.vos.GiftCardCashDeductPreviewVO;
import cn.lili.modules.promotion.entity.vos.GiftCardCashMemberCardVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * promotion-service 现金礼品卡内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/giftCardCash", contextId = "liliGiftCardCashClient")
public interface GiftCardCashClient {

    @GetMapping("/listAvailable")
    List<GiftCardCashMemberCardVO> listAvailableMemberCards(@RequestParam("memberId") String memberId);

    @PostMapping("/previewDeduction")
    GiftCardCashDeductPreviewVO previewMemberCardDeduction(@RequestParam("memberId") String memberId,
                                                           @RequestBody GiftCardCashDeductPreviewDTO dto);

    @PostMapping("/applyOnOrderCreate")
    void applyGiftCardUsageOnOrderCreate(@RequestBody GiftCardOrderApplyDTO applyDTO);

    @PostMapping("/resolveChannelRefund")
    BigDecimal resolveChannelRefundAfterGiftCard(@RequestParam("orderSn") String orderSn,
                                                 @RequestParam("orderAmount") BigDecimal orderAmount);

    @PostMapping("/resolveAfterSaleRefund")
    BigDecimal resolveChannelRefundAfterGiftCardForAfterSale(@RequestBody AfterSale afterSale);
}
