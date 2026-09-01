package cn.lili.modules.message.entity.dto;

import cn.lili.modules.message.entity.dos.NoticeMessage;
import cn.lili.modules.wechat.entity.dos.WechatMPMessage;
import cn.lili.modules.wechat.entity.dos.WechatMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 客户消息模板聚合：站内信与微信模板分表存储，通过 scene_code 关联展示（不合并表结构）。
 */
@Data
@Schema(description = "消息模板聚合行")
public class MessageTemplateAggregateDTO {

    @Schema(description = "站内信/邮箱等配置")
    private NoticeMessage notice;

    @Schema(description = "微信服务号模板，未关联则为空")
    private WechatMessage wechatOa;

    @Schema(description = "微信小程序订阅模板，未关联则为空")
    private WechatMPMessage wechatMp;
}
