package cn.lili.modules.wxchannels.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_wx_channel_order")
@Schema(description = "微信视频号订单")
public class WxChannelOrder extends BaseEntity {

    @Schema(description = "视频号订单编号")
    private String channelOrderSn;

    @Schema(description = "平台订单编号")
    private String orderSn;

    @Schema(description = "客户ID")
    private String memberId;

    @Schema(description = "客户昵称")
    private String memberNickName;

    @Schema(description = "订单金额")
    private Double amount;

    @Schema(description = "订单状态")
    private String status;

    @Schema(description = "带货视频号名称")
    private String channelName;

    @Schema(description = "下单场景 LIVE,WINDOW")
    private String scene;
}
