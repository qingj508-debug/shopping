package cn.lili.feign;

import cn.lili.modules.order.order.entity.dos.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * payment-service 内部调用 Feign 客户端（线下收款域）
 * <p>
 * 供其他微服务跨进程触发线下转账回调，端点由 payment-service 的 InternalPaymentController 提供。
 */
@FeignClient(name = "payment-service", path = "/internal/payment/bankTransfer", contextId = "liliBankTransferClient")
public interface BankTransferClient {

    /**
     * 线下转账支付回调
     */
    @PostMapping("/callBack")
    void callBack(@RequestBody Order order);
}
