package cn.lili.modules.member.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员分享记录VO
 */
@Data
public class MemberShareLogVO implements Serializable {

    private static final long serialVersionUID = 5032332714304075774L;

    @Schema(description = "分享记录ID")
    private String id;

    @Schema(description = "会员ID")
    private String memberId;

    @Schema(description = "客户名称")
    private String memberName;

    @Schema(description = "客户昵称")
    private String memberNickName;

    @Schema(description = "客户手机号")
    private String memberMobile;

    @Schema(description = "分享场景")
    private String shareScene;

    @Schema(description = "分享页面")
    private String sharePage;

    @Schema(description = "关联业务ID")
    private String relatedId;

    @Schema(description = "创建时间")
    private Date createTime;
}
