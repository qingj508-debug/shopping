package cn.lili.modules.member.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分享购买奖励记录VO
 */
@Data
public class MemberShareBuyLogVO implements Serializable {

    private static final long serialVersionUID = 7786164890993829956L;

    @Schema(description = "分享购买记录ID")
    private String id;

    @Schema(description = "分享人ID")
    private String inviterId;

    @Schema(description = "分享人客户名称")
    private String inviterName;

    @Schema(description = "分享人客户昵称")
    private String inviterNickName;

    @Schema(description = "分享人客户手机号")
    private String inviterMobile;

    @Schema(description = "被分享购买用户ID")
    private String inviteeId;

    @Schema(description = "被分享客户名称")
    private String inviteeName;

    @Schema(description = "被分享客户昵称")
    private String inviteeNickName;

    @Schema(description = "被分享客户手机号")
    private String inviteeMobile;

    @Schema(description = "订单编号")
    private String orderSn;

    @Schema(description = "订单金额")
    private Double orderAmount;

    @Schema(description = "奖励经验值")
    private Long rewardExperience;

    @Schema(description = "奖励状态 SUCCESS/SKIPPED")
    private String rewardStatus;

    @Schema(description = "创建时间")
    private Date createTime;
}
