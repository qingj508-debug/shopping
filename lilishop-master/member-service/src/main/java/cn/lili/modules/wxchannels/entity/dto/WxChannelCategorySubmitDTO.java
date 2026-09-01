package cn.lili.modules.wxchannels.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class WxChannelCategorySubmitDTO {

    @Schema(description = "类目提审项")
    @NotEmpty
    @Valid
    private List<WxChannelCategorySubmitItemDTO> items;
}
