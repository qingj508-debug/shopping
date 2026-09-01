package cn.lili.feign;

import cn.lili.modules.order.aftersale.entity.dos.AfterSale;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * payment-service 内部调用 Feign 客户端（退款支持域）
 * <p>
 * 供其他微服务跨进程触发售后退款流程，端点由 payment-service 的 InternalPaymentController 提供。
 */
@FeignClient(name = "payment-service", path = "/internal/payment/refund", contextId = "liliRefundClient")
public interface RefundClient {

    /**
     * 售后退款（原路退回 / 礼品卡回卡 / 退款流水记录）
     */
    @PostMapping("/afterSale")
    void refund(@RequestBody AfterSale afterSale);
}
