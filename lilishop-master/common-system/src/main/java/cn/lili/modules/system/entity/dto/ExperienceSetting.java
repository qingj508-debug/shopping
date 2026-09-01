package cn.lili.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 经验值设置
 *
 * @author Bulbasaur
 * @since 2021/5/16 11:10 下午
 */
@Data
public class ExperienceSetting implements Serializable {

    private static final long serialVersionUID = -4261856614779031745L;
    @Schema(description = "经验值规则")
    private List<ExperienceRuleItem> items = new ArrayList<>();

    @Schema(description = "经验值说明")
    private String description;

    @Data
    public static class ExperienceRuleItem implements Serializable {
        private static final long serialVersionUID = 4430371061889911808L;

        @Schema(description = "规则编码")
        private String ruleKey;

        @Schema(description = "规则名称")
        private String ruleName;

        @Schema(description = "是否开启")
        private Boolean enabled;

        @Schema(description = "经验值，1-100")
        private Integer value;

        @Schema(description = "可获得成长值限额，可选")
        private Integer maxValue;
    }

}
