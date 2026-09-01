package cn.lili.modules.wxchannels.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WxChannelOverviewDailyVO {

    @Schema(description = "日期")
    private String date;

    @Schema(description = "视频号总销售额")
    private Double totalSales;

    @Schema(description = "直播间销售额")
    private Double liveSales;

    @Schema(description = "橱窗销售额")
    private Double windowSales;

    @Schema(description = "视频号退货金额")
    private Double totalRefund;

    @Schema(description = "直播间退货金额")
    private Double liveRefund;

    @Schema(description = "橱窗退货金额")
    private Double windowRefund;
}
