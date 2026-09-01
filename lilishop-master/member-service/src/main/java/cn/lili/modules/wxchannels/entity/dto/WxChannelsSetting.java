package cn.lili.modules.wxchannels.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "微信视频号小店配置")
@Data
public class WxChannelsSetting {
    @Schema(description = "视频号小店AppId")
    private String appId;
    @Schema(description = "视频号小店AppSecret")
    private String appSecret;
    @Schema(description = "接口基础地址，例如：https://api.weixin.qq.com/minishop")
    private String apiBase;
    @Schema(description = "获取access_token地址，例如：https://api.weixin.qq.com/cgi-bin/token")
    private String tokenUrl;
}
