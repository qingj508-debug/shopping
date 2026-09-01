package cn.lili.feign;

import cn.lili.modules.payment.entity.RefundLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * payment-service 内部调用 Feign 客户端（退款日志域）
 * <p>
 * 供其他微服务跨进程调用退款日志域能力，端点由 payment-service 的 InternalPaymentController 提供。
 */
@FeignClient(name = "payment-service", path = "/internal/payment/refundLog", contextId = "liliRefundLogClient")
public interface RefundLogClient {

    /**
     * 根据售后sn查询退款日志
     */
    @GetMapping("/byAfterSaleSn")
    RefundLog queryByAfterSaleSn(@RequestParam("sn") String sn);
}
