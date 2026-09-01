package cn.lili.modules.wxchannels.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WxChannelsCategoryDTO {

    @Schema(description = "三级类目ID")
    private Long thirdCatId;

    @Schema(description = "三级类目名称")
    private String thirdCatName;

    @Schema(description = "类目资质")
    private String qualification;

    @Schema(description = "类目资质类型 0不需要 1必填 2选填")
    private Integer qualificationType;

    @Schema(description = "商品资质")
    private String productQualification;

    @Schema(description = "商品资质类型 0不需要 1必填 2选填")
    private Integer productQualificationType;

    @Schema(description = "二级类目ID")
    private Long secondCatId;

    @Schema(description = "二级类目名称")
    private String secondCatName;

    @Schema(description = "一级类目ID")
    private Long firstCatId;

    @Schema(description = "一级类目名称")
    private String firstCatName;
}
