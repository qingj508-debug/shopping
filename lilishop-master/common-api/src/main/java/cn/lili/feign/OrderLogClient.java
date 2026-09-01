package cn.lili.feign;

import cn.lili.modules.order.trade.entity.dos.OrderLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * order-service 内部调用 Feign 客户端（订单日志域）
 * <p>
 * 端点由 order-service 的 InternalOrderController 提供。
 */
@FeignClient(name = "order-service", path = "/internal/order/orderLog", contextId = "liliOrderLogClient")
public interface OrderLogClient {

    /**
     * 保存订单日志
     */
    @PostMapping("/save")
    boolean saveOrderLog(@RequestBody OrderLog orderLog);
}
