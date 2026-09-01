package cn.lili.modules.member.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnumValueVO {

    @Schema(description = "枚举值")
    private String value;

    @Schema(description = "描述")
    private String description;

    public EnumValueVO(String value, String description) {
        this.value = value;
        this.description = description;
    }
}

