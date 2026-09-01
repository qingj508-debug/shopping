package cn.lili.modules.member.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

/**
 * 客户修改
 * 用于后台的用户信息修改
 *
 * @author Bulbasaur
 * @since 2020/12/15 9:57
 */
@Data
public class ManagerMemberEditDTO {

    @NotNull(message = "用户ID不能为空")
    private String id;

//    @Schema(description = "客户用户名,用户名不能进行修改", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "客户用户名不能为空")
//    private String username;

    @Schema(description = "客户密码")
    private String password;

    @Schema(description = "昵称")
    @Length(min = 2, max = 20, message = "客户昵称必须为2到20位之间")
    private String nickName;

    @Schema(description = "地区")
    private String region;

    @Schema(description = "地区ID")
    private String regionId;

    @Min(message = "必须为数字且1为男,0为女", value = 0)
    @Max(message = "必须为数字且1为男,0为女", value = 1)
    @NotNull(message = "客户性别不能为空")
    @Schema(description = "客户性别,1为男，0为女")
    private Integer sex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "客户生日")
    private Date birthday;

    @Schema(description = "客户头像")
    private String face;
}
