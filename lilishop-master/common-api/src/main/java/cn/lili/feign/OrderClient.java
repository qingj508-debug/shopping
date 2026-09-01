package cn.lili.feign;

import cn.lili.modules.live.entity.vos.LiveRoomVO;
import cn.lili.modules.order.order.entity.dos.Order;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.dto.PaymentLogQueryDTO;
import cn.lili.modules.order.order.entity.vo.OrderDetailVO;
import cn.lili.modules.order.order.entity.vo.OrderSimpleVO;
import cn.lili.modules.order.order.entity.vo.PaymentLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * order-service 内部调用 Feign 客户端（订单域）
 * <p>
 * 供其他微服务跨进程调用订单域能力，端点由 order-service 的 InternalOrderController 提供。
 */
@FeignClient(name = "order-service", path = "/internal/order", contextId = "liliOrderClient")
public interface OrderClient {

    /**
     * E_COUPON 专用完成 CAS
     */
    @GetMapping("/systemCompleteECoupon")
    boolean systemCompleteECoupon(@RequestParam("orderSn") String orderSn);

    /**
     * E_COUPON 专用取消 CAS
     */
    @GetMapping("/systemCancelECoupon")
    boolean systemCancelECoupon(@RequestParam("orderSn") String orderSn,
                                @RequestParam("reason") String reason,
                                @RequestParam("refundMoney") Boolean refundMoney);

    /**
     * 根据sn查询订单
     */
    @GetMapping("/bySn/{sn}")
    Order getBySn(@PathVariable("sn") String sn);

    /**
     * 订单列表查询
     */
    @PostMapping("/queryListByParams")
    List<Order> queryListByParams(@RequestBody OrderSearchParams orderSearchParams);

    /**
     * 订单分页查询
     */
    @PostMapping("/queryByParams")
    IPage<OrderSimpleVO> queryByParams(@RequestBody OrderSearchParams orderSearchParams);

    /**
     * 根据促销查询订单列表
     */
    @GetMapping("/queryListByPromotion")
    List<Order> queryListByPromotion(@RequestParam("orderPromotionType") String orderPromotionType,
                                     @RequestParam("payStatus") String payStatus,
                                     @RequestParam("parentOrderSn") String parentOrderSn,
                                     @RequestParam("orderSn") String orderSn);

    /**
     * 根据促销统计订单数量
     */
    @GetMapping("/queryCountByPromotion")
    long queryCountByPromotion(@RequestParam("orderPromotionType") String orderPromotionType,
                               @RequestParam("payStatus") String payStatus,
                               @RequestParam("parentOrderSn") String parentOrderSn,
                               @RequestParam("orderSn") String orderSn);

    /**
     * 检查是否开始虚拟成团
     */
    @GetMapping("/checkFictitiousOrder")
    boolean checkFictitiousOrder(@RequestParam("pintuanId") String pintuanId,
                                 @RequestParam("requiredNum") Integer requiredNum,
                                 @RequestParam("fictitious") Boolean fictitious);

    /**
     * 订单详细
     */
    @GetMapping("/queryDetail/{sn}")
    OrderDetailVO queryDetail(@PathVariable("sn") String sn);

    /**
     * 订单付款
     */
    @PostMapping("/payOrder")
    void payOrder(@RequestParam("orderSn") String orderSn,
                  @RequestParam("paymentMethod") String paymentMethod,
                  @RequestParam("receivableNo") String receivableNo);

    /**
     * 通过trade获取订单列表
     */
    @GetMapping("/getByTradeSn/{tradeSn}")
    List<Order> getByTradeSn(@PathVariable("tradeSn") String tradeSn);

    /**
     * 获取订单实际支付的总金额
     */
    @GetMapping("/paymentTotal/{sn}")
    Double getPaymentTotal(@PathVariable("sn") String sn);

    /**
     * 查询订单支付记录（由 service 内部构造查询条件）
     */
    @PostMapping("/queryPaymentLogs")
    IPage<PaymentLog> queryPaymentLogs(@RequestBody PaymentLogQueryDTO query);

    /**
     * 更新订单支付流水号（按订单SN）
     */
    @PostMapping("/updatePayOrderNoBySn")
    void updatePayOrderNoBySn(@RequestParam("orderSn") String orderSn,
                              @RequestParam("payOrderNo") String payOrderNo);

    /**
     * 更新订单支付流水号（按交易SN）
     */
    @PostMapping("/updatePayOrderNoByTradeSn")
    void updatePayOrderNoByTradeSn(@RequestParam("tradeSn") String tradeSn,
                                   @RequestParam("payOrderNo") String payOrderNo);

    /**
     * 直播订单统计
     */
    @PostMapping("/countLiveOrderData")
    void countLiveOrderData(@RequestBody LiveRoomVO liveRoomVO);
}
