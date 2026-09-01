package cn.lili.modules.order.order.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;    
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 发票
 *
 * @author Bulbasaur
 * @since 2020/11/28 11:38
 */
@Data
@TableName("li_receipt")
@Schema(description = "发票")
@EqualsAndHashCode(callSuper = false)
public class Receipt extends BaseEntity {

    private static final long serialVersionUID = -8210927482915675995L;

    @Schema(description = "订单编号")
    private String orderSn;

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

    @Schema(description = "发票金额")
    private Double receiptPrice;

    @Schema(description = "客户ID")
    private String memberId;

    @Schema(description = "客户名称")
    private String memberName;

    @Schema(description = "商家ID")
    private String storeId;

    @Schema(description = "商家名称")
    private String storeName;

    @Schema(description = "发票状态 0未开 1已开")
    private Integer receiptStatus;

    @Schema(description = "发票详情")
    private String receiptDetail;

}
