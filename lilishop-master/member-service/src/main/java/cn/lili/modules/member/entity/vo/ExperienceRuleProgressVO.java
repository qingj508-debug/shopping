package cn.lili.modules.member.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExperienceRuleProgressVO implements Serializable {

    private static final long serialVersionUID = 8362538156744885472L;

    @Schema(description = "规则编码")
    private String ruleKey;

    @Schema(description = "规则名称")
    private String ruleName;

    @Schema(description = "规则值")
    private Integer value;

    @Schema(description = "限额")
    private Integer maxValue;

    @Schema(description = "是否已完成")
    private Boolean completed;

    @Schema(description = "已获得经验值")
    private Long gainedExperience;
}
