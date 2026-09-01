package cn.lili.modules.member.entity.vo;

import cn.lili.common.enums.SwitchEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 客户搜索VO
 *
 * @author Bulbasaur
 * @since 2020/12/15 10:48
 */
@Data
public class MemberSearchVO {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "客户分组ID")
    private String groupId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "用户手机号码")
    private String mobile;

    @Schema(description = "会员等级ID")
    private String gradeId;

    @Schema(description = "最小积分值")
    private Long minPoint;

    @Schema(description = "最大积分值")
    private Long maxPoint;

    /**
     * @see SwitchEnum
     */
    @Schema(description = "客户状态")
    private String disabled;
}
