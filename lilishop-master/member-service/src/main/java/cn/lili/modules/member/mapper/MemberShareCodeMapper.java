package cn.lili.modules.member.mapper;

import cn.lili.modules.member.entity.dos.MemberShareCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 会员分享码数据层
 */
public interface MemberShareCodeMapper extends BaseMapper<MemberShareCode> {

    @Select("SELECT * FROM li_member_share_code WHERE member_id = #{memberId} AND delete_flag = 0 LIMIT 1")
    MemberShareCode findByMemberId(@Param("memberId") String memberId);

    @Select("SELECT * FROM li_member_share_code WHERE share_code = #{shareCode} AND state = 'OPEN' AND delete_flag = 0 LIMIT 1")
    MemberShareCode findByShareCode(@Param("shareCode") String shareCode);
}
