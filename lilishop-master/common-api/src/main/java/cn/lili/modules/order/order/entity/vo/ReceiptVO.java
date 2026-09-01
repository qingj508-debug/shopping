package cn.lili.modules.order.order.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 发票
 *
 * @author Bulbasaur
 * @since 2020/11/28 11:38
 */
@Data
@Schema(description = "发票")
public class ReceiptVO implements Serializable {

    private static final long serialVersionUID = -8402457457074092957L;

    @Schema(description = "发票抬头")
    private String receiptTitle;

    @Schema(description = "个人名称")
    private String personalName;

    @Schema(description = "单位名称")
    private String companyName;

    @Schema(description = "发票地址(URL)")
    private String invoiceAddress;

    @Schema(description = "发票类型", allowableValues = {"电子普通发票", "增值税专用发票"}, example = "电子普通发票")
    private String receiptType;

    @Schema(description = "纳税人识别号")
    private String taxpayerId;

    @Schema(description = "收票人手机号")
    private String receiptPhone;

    @Schema(description = "收票人邮箱")
    private String receiptEmail;

    @Schema(description = "单位地址")
    private String companyAddress;

    @Schema(description = "单位电话")
    private String companyPhone;

    @Schema(description = "开户银行")
    private String bankName;

    @Schema(description = "银行账户")
    private String bankAccount;

    @Schema(description = "发票内容")
    private String receiptContent;

}
