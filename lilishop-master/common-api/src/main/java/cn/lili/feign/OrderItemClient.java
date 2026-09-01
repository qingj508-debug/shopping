package cn.lili.feign;

import cn.lili.modules.order.order.entity.dos.OrderItem;
import cn.lili.modules.order.order.entity.enums.CommentStatusEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * order-service 内部调用 Feign 客户端（子订单域）
 * <p>
 * 端点由 order-service 的 InternalOrderController 提供。
 */
@FeignClient(name = "order-service", path = "/internal/order/orderItem", contextId = "liliOrderItemClient")
public interface OrderItemClient {

    /**
     * 根据子订单编号获取子订单信息
     */
    @GetMapping("/bySn/{sn}")
    OrderItem getBySn(@PathVariable("sn") String sn);

    /**
     * 根据订单编号获取子订单列表
     */
    @GetMapping("/byOrderSn/{orderSn}")
    List<OrderItem> getByOrderSn(@PathVariable("orderSn") String orderSn);

    /**
     * 更新评论状态
     */
    @PostMapping("/updateCommentStatus")
    void updateCommentStatus(@RequestParam("orderItemSn") String orderItemSn,
                             @RequestParam("commentStatus") CommentStatusEnum commentStatus);

    /**
     * 统计专用：按创建时间范围查询未全部退款的子订单
     */
    @GetMapping("/listByCreateTimeNotRefund")
    List<OrderItem> listByCreateTimeNotRefund(@RequestParam("startTime") Long startTime,
                                              @RequestParam("endTime") Long endTime);

    /**
     * 统计专用：按已支付订单的支付时间范围查询未全部退款的子订单（storeId 可空）
     */
    @GetMapping("/listPaidBetween")
    List<OrderItem> listPaidBetween(@RequestParam("startTime") Long startTime,
                                    @RequestParam("endTime") Long endTime,
                                    @RequestParam(value = "storeId", required = false) String storeId);
}
