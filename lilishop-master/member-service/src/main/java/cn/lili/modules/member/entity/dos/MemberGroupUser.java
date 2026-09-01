package cn.lili.modules.member.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_member_group_user")
@Schema(description = "客户分组用户关联")
public class MemberGroupUser extends BaseEntity {

    @NotNull
    @Schema(description = "分组ID")
    private String groupId;

    @NotNull
    @Schema(description = "客户ID")
    private String memberId;
}
