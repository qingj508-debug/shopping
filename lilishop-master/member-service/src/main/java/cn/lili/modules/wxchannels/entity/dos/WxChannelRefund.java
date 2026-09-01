package cn.lili.modules.wxchannels.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_wx_channel_refund")
@Schema(description = "微信视频号退单")
public class WxChannelRefund extends BaseEntity {

    @Schema(description = "视频号退单编号")
    private String channelRefundSn;

    @Schema(description = "视频号订单编号")
    private String channelOrderSn;

    @Schema(description = "客户ID")
    private String memberId;

    @Schema(description = "客户昵称")
    private String memberNickName;

    @Schema(description = "退款金额")
    private Double amount;

    @Schema(description = "退单状态")
    private String status;

    @Schema(description = "下单场景 LIVE,WINDOW")
    private String scene;
}
