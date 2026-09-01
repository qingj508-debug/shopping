package cn.lili.modules.promotion.entity.dto;

import cn.lili.modules.order.cart.entity.dto.TradeDTO;
import cn.lili.modules.order.order.entity.dos.Order;
import cn.lili.modules.order.order.entity.dos.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 下单应用礼品卡分摊参数（Feign 单 body 包装）
 * <p>
 * 对应 GiftCardCashService.applyGiftCardUsageOnOrderCreate(tradeDTO, orders, orderItems)，
 * 跨进程调用时打包为单个请求体。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardOrderApplyDTO {

    /**
     * 交易对象
     */
    private TradeDTO tradeDTO;

    /**
     * 订单集合
     */
    private List<Order> orders;

    /**
     * 订单项集合
     */
    private List<OrderItem> orderItems;
}
