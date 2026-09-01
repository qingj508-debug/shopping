package cn.lili.modules.goods.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 商品虚拟销量更新参数
 */
@Data
public class GoodsVirtualSalesDTO {

    @NotEmpty(message = "商品规格ID列表不能为空")
    @Schema(description = "商品规格ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<@NotBlank(message = "商品规格ID不能为空") String> skuIds;

    @NotNull(message = "虚拟销量不能为空")
    @Min(value = 0, message = "虚拟销量不能小于0")
    @Max(value = 99999999, message = "虚拟销量不能超过99999999")
    @Schema(description = "虚拟销量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer virtualSales;
}
