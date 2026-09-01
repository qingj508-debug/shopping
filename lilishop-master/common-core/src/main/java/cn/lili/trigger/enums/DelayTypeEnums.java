package cn.lili.trigger.enums;

/**
 * 延时任务类型
 *
 * @author paulG
 * @since 2021/5/7
 */
public enum DelayTypeEnums {

    /**
     * 促销活动
     */
    PROMOTION("促销活动"),
    /**
     * 拼团订单
     */
    PINTUAN_ORDER("拼团订单"),
    /**
     * 订单
     */
    COUPON_ACTIVITY("优惠券活动"),

    /**
     * 直播
     */
    BROADCAST("直播"),

    /**
     * 商品（上下架）
     */
    GOODS("商品");

    private String description;

    DelayTypeEnums(String description) {
        this.description = description;
    }

}
