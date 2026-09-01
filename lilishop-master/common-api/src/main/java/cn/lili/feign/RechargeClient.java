package cn.lili.feign;

import cn.lili.modules.payment.entity.enums.PaymentMethodEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * payment-service 内部调用 Feign 客户端（充值统计域）
 * <p>
 * 供其他微服务跨进程查询充值统计，端点由 payment-service 的 InternalPaymentController 提供。
 */
@FeignClient(name = "payment-service", path = "/internal/payment/recharge", contextId = "liliRechargeClient")
public interface RechargeClient {

    /**
     * 获取周期内的充值金额
     */
    @PostMapping("/getRecharge")
    Double getRecharge(@RequestBody Date[] dates,
                       @RequestParam(value = "paymentMethod", required = false) PaymentMethodEnum paymentMethodEnum);
}
