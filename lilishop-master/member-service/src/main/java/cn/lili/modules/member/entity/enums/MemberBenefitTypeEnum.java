package cn.lili.modules.member.entity.enums;

import cn.hutool.core.text.CharSequenceUtil;

import java.util.Arrays;

/**
 * 权益类型
 */
public enum MemberBenefitTypeEnum {
    EXCLUSIVE_CUSTOMER_SERVICE("专属客服"),
    COUPON_PACKAGE("券礼包"),
    GIFT_POINT("赠送积分"),
    CUSTOM("自定义");

    private final String description;

    MemberBenefitTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String value() {
        return this.name();
    }

    public static boolean isValid(String type) {
        if (CharSequenceUtil.isBlank(type)) {
            return false;
        }
        return Arrays.stream(MemberBenefitTypeEnum.values()).anyMatch(v -> v.name().equals(type));
    }
}
