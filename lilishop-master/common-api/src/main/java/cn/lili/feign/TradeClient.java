package cn.lili.feign;

import cn.lili.modules.order.order.entity.dos.Trade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * order-service 内部调用 Feign 客户端（交易域）
 * <p>
 * 端点由 order-service 的 InternalOrderController 提供。
 */
@FeignClient(name = "order-service", path = "/internal/order/trade", contextId = "liliTradeClient")
public interface TradeClient {

    /**
     * 获取交易详情
     */
    @GetMapping("/bySn/{sn}")
    Trade getBySn(@PathVariable("sn") String sn);

    /**
     * 整笔交易付款
     */
    @PostMapping("/payTrade")
    void payTrade(@RequestParam("tradeSn") String tradeSn,
                  @RequestParam("paymentName") String paymentName,
                  @RequestParam("receivableNo") String receivableNo);
}
