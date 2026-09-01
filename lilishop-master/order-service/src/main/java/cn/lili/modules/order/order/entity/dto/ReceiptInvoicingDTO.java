package cn.lili.modules.order.order.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 商家开票参数
 *
 * @author Mike
 */
@Data
public class ReceiptInvoicingDTO {

    @NotBlank(message = "发票地址不能为空")
    @Schema(description = "发票地址(URL)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String invoiceAddress;
}
