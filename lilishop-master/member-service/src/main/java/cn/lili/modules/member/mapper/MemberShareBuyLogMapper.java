package cn.lili.modules.member.mapper;

import cn.lili.modules.member.entity.dos.MemberShareBuyLog;
import cn.lili.modules.member.entity.vo.MemberShareBuyLogVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 分享购买奖励记录数据层
 */
public interface MemberShareBuyLogMapper extends BaseMapper<MemberShareBuyLog> {

    @Select("SELECT COUNT(1) FROM li_member_share_buy_log WHERE order_sn = #{orderSn} AND delete_flag = 0")
    Long countByOrderSn(@Param("orderSn") String orderSn);

    @Select("SELECT sb.id, sb.inviter_id, sb.invitee_id, sb.order_sn, sb.order_amount, sb.reward_experience, sb.reward_status, sb.create_time, " +
            "inviter.username AS inviterName, inviter.nick_name AS inviterNickName, inviter.mobile AS inviterMobile, " +
            "invitee.username AS inviteeName, invitee.nick_name AS inviteeNickName, invitee.mobile AS inviteeMobile " +
            "FROM li_member_share_buy_log sb " +
            "LEFT JOIN li_member inviter ON sb.inviter_id = inviter.id " +
            "LEFT JOIN li_member invitee ON sb.invitee_id = invitee.id ${ew.customSqlSegment}")
    IPage<MemberShareBuyLogVO> pageWithMember(IPage<MemberShareBuyLogVO> page,
                                              @Param(Constants.WRAPPER) Wrapper<MemberShareBuyLog> queryWrapper);
}
