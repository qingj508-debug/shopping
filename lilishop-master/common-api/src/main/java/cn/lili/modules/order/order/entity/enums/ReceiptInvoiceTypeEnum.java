package cn.lili.modules.order.order.entity.enums;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * 订单发票类型
 *
 * @author Mike
 */
public enum ReceiptInvoiceTypeEnum {

    /**
     * 电子普通发票
     */
    ELECTRONIC_ORDINARY("电子普通发票"),

    /**
     * 增值税专用发票
     */
    VAT_SPECIAL("增值税专用发票");

    private final String label;

    ReceiptInvoiceTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 将买家端传入的发票类型
     */
    public static String toStoredLabel(String receiptType) {
        if (CharSequenceUtil.isBlank(receiptType)) {
            return ELECTRONIC_ORDINARY.getLabel();
        }
        String s = receiptType.trim();
        if ("2".equals(s)
                || VAT_SPECIAL.getLabel().equals(s)
                || VAT_SPECIAL.name().equalsIgnoreCase(s)) {
            return VAT_SPECIAL.getLabel();
        }
        if ("1".equals(s)
                || ELECTRONIC_ORDINARY.getLabel().equals(s)
                || ELECTRONIC_ORDINARY.name().equalsIgnoreCase(s)) {
            return ELECTRONIC_ORDINARY.getLabel();
        }
        return ELECTRONIC_ORDINARY.getLabel();
    }
}
