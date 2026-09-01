package cn.lili.modules.goods.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Date;

@Data
public class GoodsMarketScheduleDTO {

    @Schema(description = "商品ID列表")
    @NotEmpty
    private List<String> goodsIds;

    @Schema(description = "状态 UPPER/DOWN")
    @NotEmpty
    private String status;

    @Schema(description = "触发时间")
    @NotNull
    private Date triggerTime;

    @Schema(description = "原因")
    private String reason;
}
