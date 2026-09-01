package cn.lili.rocketmq.tags;

/**
 * 客户操作枚举
 *
 * @author paulG
 * @since 2020/12/9
 **/
public enum MemberTagsEnum {
    /**
     * 客户注册
     */
    MEMBER_REGISTER("客户注册"),
    /**
     * 客户注册
     */
    MEMBER_LOGIN("客户登录"),
    /**
     * 客户签到
     */
    MEMBER_SING("客户签到"),
    /**
     * 客户提现
     */
    MEMBER_WITHDRAWAL("客户提现"),
    /**
     * 客户提现
     */
    DISTRIBUTION_WITHDRAWAL("分销提现"),
    /**
     * 客户信息更改
     */
    MEMBER_INFO_EDIT("客户信息更改"),
    /**
     * 客户积分变动
     */
    MEMBER_POINT_CHANGE("客户积分变动"),
    /**
     * 客户使用联合登录
     */
    MEMBER_CONNECT_LOGIN("客户使用联合登录");

    private final String description;

    MemberTagsEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }


}
