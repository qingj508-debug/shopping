package cn.lili.controller.internal;

import cn.lili.modules.finance.entity.dto.FinanceStoreFlowSearchParams;
import cn.lili.modules.live.entity.vos.LiveRoomVO;
import cn.lili.modules.order.aftersale.entity.dos.AfterSale;
import cn.lili.modules.order.order.entity.dos.Order;
import cn.lili.modules.order.order.entity.dos.OrderItem;
import cn.lili.modules.order.order.entity.dos.StoreFlow;
import cn.lili.modules.order.order.entity.dos.Trade;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.dto.PaymentLogQueryDTO;
import cn.lili.modules.order.order.entity.dto.StoreFlowQueryDTO;
import cn.lili.modules.order.order.entity.enums.PayStatusEnum;
import cn.lili.modules.order.order.entity.enums.RefundStatusEnum;
import cn.lili.modules.order.order.entity.vo.OrderDetailVO;
import cn.lili.modules.order.order.entity.vo.PaymentLog;
import cn.lili.modules.order.order.service.OrderItemService;
import cn.lili.modules.order.order.service.OrderService;
import cn.lili.modules.order.order.service.StoreFlowService;
import cn.lili.modules.order.order.service.TradeService;
import cn.lili.modules.order.trade.entity.dos.OrderLog;
import cn.lili.modules.order.trade.service.OrderLogService;
import cn.lili.modules.store.entity.dos.Bill;
import cn.lili.modules.store.entity.dto.BillSearchParams;
import cn.lili.modules.store.entity.vos.StoreFlowPayDownloadVO;
import cn.lili.modules.store.entity.vos.StoreFlowRefundDownloadVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * order-service 内部调用端点
 * <p>
 * 供 OrderClient/OrderItemClient/TradeClient/StoreFlowClient/OrderLogClient（common-api Feign）跨服务调用，
 * 直接委托本地 service，返回裸类型。
 * 注意：/internal/** 不受买家网关安全链保护，也不会被网关路由。
 */
@RestController
@RequestMapping("/internal/order")
public class InternalOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private StoreFlowService storeFlowService;
    @Autowired
    private OrderLogService orderLogService;

    // ==================== Order ====================

    @GetMapping("/systemCompleteECoupon")
    public boolean systemCompleteECoupon(@RequestParam("orderSn") String orderSn) {
        return orderService.systemCompleteECoupon(orderSn);
    }

    @GetMapping("/systemCancelECoupon")
    public boolean systemCancelECoupon(@RequestParam("orderSn") String orderSn,
                                       @RequestParam("reason") String reason,
                                       @RequestParam("refundMoney") Boolean refundMoney) {
        return orderService.systemCancelECoupon(orderSn, reason, refundMoney);
    }

    @GetMapping("/bySn/{sn}")
    public Order getBySn(@PathVariable("sn") String sn) {
        return orderService.getBySn(sn);
    }

    @PostMapping("/queryListByParams")
    public List<Order> queryListByParams(@RequestBody OrderSearchParams orderSearchParams) {
        return orderService.queryListByParams(orderSearchParams);
    }

    @PostMapping("/queryByParams")
    public IPage<cn.lili.modules.order.order.entity.vo.OrderSimpleVO> queryByParams(@RequestBody OrderSearchParams orderSearchParams) {
        return orderService.queryByParams(orderSearchParams);
    }

    @GetMapping("/queryListByPromotion")
    public List<Order> queryListByPromotion(@RequestParam("orderPromotionType") String orderPromotionType,
                                            @RequestParam("payStatus") String payStatus,
                                            @RequestParam("parentOrderSn") String parentOrderSn,
                                            @RequestParam("orderSn") String orderSn) {
        return orderService.queryListByPromotion(orderPromotionType, payStatus, parentOrderSn, orderSn);
    }

    @GetMapping("/queryCountByPromotion")
    public long queryCountByPromotion(@RequestParam("orderPromotionType") String orderPromotionType,
                                      @RequestParam("payStatus") String payStatus,
                                      @RequestParam("parentOrderSn") String parentOrderSn,
                                      @RequestParam("orderSn") String orderSn) {
        return orderService.queryCountByPromotion(orderPromotionType, payStatus, parentOrderSn, orderSn);
    }

    @GetMapping("/checkFictitiousOrder")
    public boolean checkFictitiousOrder(@RequestParam("pintuanId") String pintuanId,
                                        @RequestParam("requiredNum") Integer requiredNum,
                                        @RequestParam("fictitious") Boolean fictitious) {
        return orderService.checkFictitiousOrder(pintuanId, requiredNum, fictitious);
    }

    @GetMapping("/queryDetail/{sn}")
    public OrderDetailVO queryDetail(@PathVariable("sn") String sn) {
        return orderService.queryDetail(sn);
    }

    @PostMapping("/payOrder")
    public void payOrder(@RequestParam("orderSn") String orderSn,
                         @RequestParam("paymentMethod") String paymentMethod,
                         @RequestParam("receivableNo") String receivableNo) {
        orderService.payOrder(orderSn, paymentMethod, receivableNo);
    }

    @GetMapping("/getByTradeSn/{tradeSn}")
    public List<Order> getByTradeSn(@PathVariable("tradeSn") String tradeSn) {
        return orderService.getByTradeSn(tradeSn);
    }

    @GetMapping("/paymentTotal/{sn}")
    public Double getPaymentTotal(@PathVariable("sn") String sn) {
        return orderService.getPaymentTotal(sn);
    }

    @PostMapping("/queryPaymentLogs")
    public IPage<PaymentLog> queryPaymentLogs(@RequestBody PaymentLogQueryDTO query) {
        if (query == null || query.getOrder() == null) {
            return new Page<>();
        }
        return orderService.queryPaymentLogs(query.getOrder(), query.getSearchVO(), query.getPageVO());
    }

    @PostMapping("/updatePayOrderNoBySn")
    public void updatePayOrderNoBySn(@RequestParam("orderSn") String orderSn,
                                     @RequestParam("payOrderNo") String payOrderNo) {
        orderService.update(new LambdaUpdateWrapper<Order>()
                .eq(Order::getSn, orderSn)
                .set(Order::getPayOrderNo, payOrderNo));
    }

    @PostMapping("/updatePayOrderNoByTradeSn")
    public void updatePayOrderNoByTradeSn(@RequestParam("tradeSn") String tradeSn,
                                          @RequestParam("payOrderNo") String payOrderNo) {
        orderService.update(new LambdaUpdateWrapper<Order>()
                .eq(Order::getTradeSn, tradeSn)
                .set(Order::getPayOrderNo, payOrderNo));
    }

    @PostMapping("/countLiveOrderData")
    public void countLiveOrderData(@RequestBody LiveRoomVO liveRoomVO) {
        orderService.countLiveOrderData(liveRoomVO);
    }

    // ==================== OrderItem ====================

    @GetMapping("/orderItem/bySn/{sn}")
    public OrderItem getOrderItemBySn(@PathVariable("sn") String sn) {
        return orderItemService.getBySn(sn);
    }

    @GetMapping("/orderItem/byOrderSn/{orderSn}")
    public List<OrderItem> getOrderItemByOrderSn(@PathVariable("orderSn") String orderSn) {
        return orderItemService.getByOrderSn(orderSn);
    }

    @PostMapping("/orderItem/updateCommentStatus")
    public void updateCommentStatus(@RequestParam("orderItemSn") String orderItemSn,
                                    @RequestParam("commentStatus") cn.lili.modules.order.order.entity.enums.CommentStatusEnum commentStatus) {
        orderItemService.updateCommentStatus(orderItemSn, commentStatus);
    }

    @GetMapping("/orderItem/listByCreateTimeNotRefund")
    public List<OrderItem> listByCreateTimeNotRefund(@RequestParam("startTime") Long startTime,
                                                     @RequestParam("endTime") Long endTime) {
        return orderItemService.lambdaQuery()
                .between(OrderItem::getCreateTime, new Date(startTime), new Date(endTime))
                .ne(OrderItem::getIsRefund, RefundStatusEnum.ALL_REFUND.name())
                .list();
    }

    @GetMapping("/orderItem/listPaidBetween")
    public List<OrderItem> listPaidBetween(@RequestParam("startTime") Long startTime,
                                           @RequestParam("endTime") Long endTime,
                                           @RequestParam(value = "storeId", required = false) String storeId) {
        QueryWrapper<OrderItem> qw = Wrappers.query();
        if (cn.lili.common.utils.StringUtils.isNotEmpty(storeId)) {
            qw.apply("EXISTS (SELECT 1 FROM li_order o WHERE o.sn = li_order_item.order_sn AND o.pay_status = {0} AND o.payment_time BETWEEN {1} AND {2} AND o.store_id = {3})",
                    PayStatusEnum.PAID.name(), new Date(startTime), new Date(endTime), storeId);
        } else {
            qw.apply("EXISTS (SELECT 1 FROM li_order o WHERE o.sn = li_order_item.order_sn AND o.pay_status = {0} AND o.payment_time BETWEEN {1} AND {2})",
                    PayStatusEnum.PAID.name(), new Date(startTime), new Date(endTime));
        }
        qw.ne("is_refund", RefundStatusEnum.ALL_REFUND.name());
        return orderItemService.list(qw);
    }

    // ==================== Trade ====================

    @GetMapping("/trade/bySn/{sn}")
    public Trade getTradeBySn(@PathVariable("sn") String sn) {
        return tradeService.getBySn(sn);
    }

    @PostMapping("/trade/payTrade")
    public void payTrade(@RequestParam("tradeSn") String tradeSn,
                         @RequestParam("paymentName") String paymentName,
                         @RequestParam("receivableNo") String receivableNo) {
        tradeService.payTrade(tradeSn, paymentName, receivableNo);
    }

    // ==================== StoreFlow ====================

    @PostMapping("/storeFlow/countByParams")
    public long countByParams(@RequestBody FinanceStoreFlowSearchParams params) {
        return storeFlowService.count(buildStoreFlowWrapper(params));
    }

    @PostMapping("/storeFlow/pageByParams")
    public IPage<StoreFlow> pageByParams(@RequestBody FinanceStoreFlowSearchParams params) {
        Page<StoreFlow> page = new Page<>(params.getPageNumber() == null ? 1 : params.getPageNumber(),
                params.getPageSize() == null ? 10 : params.getPageSize());
        return storeFlowService.page(page, buildStoreFlowWrapper(params));
    }

    @PostMapping("/storeFlow/payDownload")
    public List<StoreFlowPayDownloadVO> getStoreFlowPayDownloadVO(@RequestBody StoreFlowQueryDTO storeFlowQueryDTO) {
        return storeFlowService.getStoreFlowPayDownloadVO(storeFlowQueryDTO);
    }

    @PostMapping("/storeFlow/refundDownload")
    public List<StoreFlowRefundDownloadVO> getStoreFlowRefundDownloadVO(@RequestBody StoreFlowQueryDTO storeFlowQueryDTO) {
        return storeFlowService.getStoreFlowRefundDownloadVO(storeFlowQueryDTO);
    }

    @PostMapping("/storeFlow/refundOrder")
    public void refundOrder(@RequestBody AfterSale afterSale) {
        storeFlowService.refundOrder(afterSale);
    }

    @PostMapping("/storeFlow/listStoreFlow")
    public List<StoreFlow> listStoreFlow(@RequestBody StoreFlowQueryDTO storeFlowQueryDTO) {
        return storeFlowService.listStoreFlow(storeFlowQueryDTO);
    }

    @PostMapping("/storeFlow/queryOne")
    public StoreFlow queryOne(@RequestBody StoreFlowQueryDTO storeFlowQueryDTO) {
        return storeFlowService.queryOne(storeFlowQueryDTO);
    }

    @PostMapping("/storeFlow/refundBill")
    public Bill getRefundBill(@RequestBody BillSearchParams searchParams) {
        return storeFlowService.getRefundBill(searchParams);
    }

    @PostMapping("/storeFlow/orderBill")
    public Bill getOrderBill(@RequestBody BillSearchParams searchParams) {
        return storeFlowService.getOrderBill(searchParams);
    }

    private LambdaQueryWrapper<StoreFlow> buildStoreFlowWrapper(FinanceStoreFlowSearchParams params) {
        LambdaQueryWrapper<StoreFlow> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(cn.hutool.core.text.CharSequenceUtil.isNotEmpty(params.getStoreId()),
                StoreFlow::getStoreId, params.getStoreId());
        wrapper.eq(cn.hutool.core.text.CharSequenceUtil.isNotEmpty(params.getFlowType()),
                StoreFlow::getFlowType, params.getFlowType());
        if (params.getStartTime() != null && params.getEndTime() != null) {
            wrapper.between(StoreFlow::getCreateTime, params.getStartTime(), params.getEndTime());
        } else if (cn.hutool.core.text.CharSequenceUtil.isNotEmpty(params.getStartDate())
                && cn.hutool.core.text.CharSequenceUtil.isNotEmpty(params.getEndDate())) {
            wrapper.between(StoreFlow::getCreateTime, params.getStartDate(), params.getEndDate());
        }
        wrapper.orderByDesc(StoreFlow::getCreateTime);
        return wrapper;
    }

    // ==================== OrderLog ====================

    @PostMapping("/orderLog/save")
    public boolean saveOrderLog(@RequestBody OrderLog orderLog) {
        return orderLogService.save(orderLog);
    }
}
