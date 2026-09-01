package cn.lili.controller.internal;

import cn.lili.modules.order.aftersale.entity.dos.AfterSale;
import cn.lili.modules.order.order.entity.dos.Order;
import cn.lili.modules.payment.entity.RefundLog;
import cn.lili.modules.payment.entity.enums.PaymentMethodEnum;
import cn.lili.modules.payment.kit.RefundSupport;
import cn.lili.modules.payment.kit.plugin.bank.BankTransferPlugin;
import cn.lili.modules.payment.service.RefundLogService;
import cn.lili.modules.wallet.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * payment-service 内部调用端点
 * <p>
 * 供 RefundLogClient/RefundClient/BankTransferClient/RechargeClient（common-api Feign）跨服务调用，
 * 直接委托本地 service，返回裸类型。
 * 注意：/internal/** 不受买家网关安全链保护，也不会被网关路由。
 */
@RestController
@RequestMapping("/internal/payment")
public class InternalPaymentController {

    @Autowired
    private RefundLogService refundLogService;
    @Autowired
    private RefundSupport refundSupport;
    @Autowired
    private BankTransferPlugin bankTransferPlugin;
    @Autowired
    private RechargeService rechargeService;

    // ==================== RefundLog ====================

    @GetMapping("/refundLog/byAfterSaleSn")
    public RefundLog queryByAfterSaleSn(@RequestParam("sn") String sn) {
        return refundLogService.queryByAfterSaleSn(sn);
    }

    // ==================== Refund ====================

    @PostMapping("/refund/afterSale")
    public void refundAfterSale(@RequestBody AfterSale afterSale) {
        refundSupport.refund(afterSale);
    }

    // ==================== BankTransfer ====================

    @PostMapping("/bankTransfer/callBack")
    public void bankTransferCallBack(@RequestBody Order order) {
        bankTransferPlugin.callBack(order);
    }

    // ==================== Recharge ====================

    @PostMapping("/recharge/getRecharge")
    public Double getRecharge(@RequestBody Date[] dates,
                              @RequestParam(value = "paymentMethod", required = false) PaymentMethodEnum paymentMethodEnum) {
        return rechargeService.getRecharge(dates, paymentMethodEnum);
    }
}
