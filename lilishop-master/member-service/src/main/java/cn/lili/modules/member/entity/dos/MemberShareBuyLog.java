package cn.lili.modules.member.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分享购买奖励记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_member_share_buy_log")
@Schema(description = "分享购买奖励记录")
public class MemberShareBuyLog extends BaseEntity {

    @Schema(description = "分享人ID")
    private String inviterId;

    @Schema(description = "被分享购买用户ID")
    private String inviteeId;

    @Schema(description = "订单编号")
    private String orderSn;

    @Schema(description = "订单金额")
    private Double orderAmount;

    @Schema(description = "奖励经验值")
    private Long rewardExperience;

    @Schema(description = "奖励状态 SUCCESS/SKIPPED")
    private String rewardStatus;
}
