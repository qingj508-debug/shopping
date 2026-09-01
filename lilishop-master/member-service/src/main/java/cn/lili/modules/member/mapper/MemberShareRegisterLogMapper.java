package cn.lili.modules.member.mapper;

import cn.lili.modules.member.entity.dos.MemberShareRegisterLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 分享注册奖励记录数据层
 */
public interface MemberShareRegisterLogMapper extends BaseMapper<MemberShareRegisterLog> {

    @Select("SELECT COUNT(1) FROM li_member_share_register_log WHERE invitee_id = #{inviteeId} AND delete_flag = 0")
    Long countByInviteeId(@Param("inviteeId") String inviteeId);

    @Select("SELECT inviter_id FROM li_member_share_register_log WHERE invitee_id = #{inviteeId} AND delete_flag = 0 ORDER BY create_time DESC LIMIT 1")
    String findInviterIdByInviteeId(@Param("inviteeId") String inviteeId);
}
