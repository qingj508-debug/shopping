package cn.lili.modules.member.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员分享记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_member_share_log")
@Schema(description = "会员分享记录")
public class MemberShareLog extends BaseEntity {

    @Schema(description = "会员ID")
    private String memberId;

    @Schema(description = "分享场景")
    private String shareScene;

    @Schema(description = "分享页面")
    private String sharePage;

    @Schema(description = "关联业务ID")
    private String relatedId;
}
