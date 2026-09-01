package cn.lili.modules.member.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分享注册奖励记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_member_share_register_log")
@Schema(description = "分享注册奖励记录")
public class MemberShareRegisterLog extends BaseEntity {

    @Schema(description = "分享人ID")
    private String inviterId;

    @Schema(description = "被分享注册用户ID")
    private String inviteeId;

    @Schema(description = "被分享注册用户手机号")
    private String inviteeMobile;

    @Schema(description = "分享码")
    private String shareCode;

    @Schema(description = "奖励经验值")
    private Long rewardExperience;

    @Schema(description = "奖励状态 SUCCESS/SKIPPED")
    private String rewardStatus;
}
