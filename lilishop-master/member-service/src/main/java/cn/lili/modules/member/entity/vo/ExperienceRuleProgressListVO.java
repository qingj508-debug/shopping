package cn.lili.modules.member.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExperienceRuleProgressListVO implements Serializable {

    private static final long serialVersionUID = 4680553575916909550L;

    @Schema(description = "经验值说明")
    private String description;

    @Schema(description = "经验值规则列表")
    private List<ExperienceRuleProgressVO> items;
}
