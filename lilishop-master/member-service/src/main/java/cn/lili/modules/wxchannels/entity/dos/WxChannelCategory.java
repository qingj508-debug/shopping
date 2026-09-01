package cn.lili.modules.wxchannels.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_wx_channel_category")
@Schema(description = "微信视频号类目申请")
public class WxChannelCategory extends BaseEntity {

    @Schema(description = "微信类目ID")
    private String wxCategoryId;

    @Schema(description = "微信类目名称")
    private String wxCategoryName;

    @Schema(description = "平台类目ID")
    private String platformCategoryId;

    @Schema(description = "平台类目名称")
    private String platformCategoryName;

    @Schema(description = "状态 APPROVED,PENDING,REJECTED")
    private String status;

    @Schema(description = "资质材料JSON")
    private String materials;
}
