package cn.lili.modules.wxchannels.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WxChannelCategorySubmitItemDTO {

    @Schema(description = "微信类目ID")
    @NotBlank
    private String wxCategoryId;

    @Schema(description = "微信类目名称")
    @NotBlank
    private String wxCategoryName;

    @Schema(description = "平台类目ID")
    @NotBlank
    private String platformCategoryId;

    @Schema(description = "平台类目名称")
    @NotBlank
    private String platformCategoryName;

    @Schema(description = "资质材料JSON")
    private String materials;
}
