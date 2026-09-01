package cn.lili.modules.member.entity.vo;

import cn.lili.modules.member.entity.dos.MemberBenefit;
import cn.lili.modules.member.entity.dos.MemberGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "当前客户权益信息")
public class MemberCurrentBenefitVO implements Serializable {

    @Schema(description = "当前等级ID")
    private String gradeId;

    @Schema(description = "当前等级信息")
    private MemberGrade grade;

    @Schema(description = "当前等级绑定的权益列表")
    private List<MemberBenefit> benefits;
}
