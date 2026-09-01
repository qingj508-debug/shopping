package cn.lili.modules.member.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员达到某等级时已发放等级权益的记录（幂等：同一会员同一等级仅一条）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_member_grade_benefit_grant")
@Schema(description = "等级权益发放记录")
public class MemberGradeBenefitGrant extends BaseEntity {

    @Schema(description = "会员ID")
    private String memberId;

    @Schema(description = "达到的等级ID")
    private String gradeId;
}
