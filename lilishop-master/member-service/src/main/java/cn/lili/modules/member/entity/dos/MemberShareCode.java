package cn.lili.modules.member.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员分享码
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_member_share_code")
@Schema(description = "会员分享码")
public class MemberShareCode extends BaseEntity {

    @Schema(description = "会员ID")
    private String memberId;

    @Schema(description = "分享码")
    private String shareCode;

    @Schema(description = "状态 OPEN/CLOSE")
    private String state;
}
