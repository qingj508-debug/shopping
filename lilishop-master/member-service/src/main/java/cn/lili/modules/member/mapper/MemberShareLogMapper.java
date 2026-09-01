package cn.lili.modules.member.mapper;

import cn.lili.modules.member.entity.dos.MemberShareLog;
import cn.lili.modules.member.entity.vo.MemberShareLogVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 会员分享记录数据层
 */
public interface MemberShareLogMapper extends BaseMapper<MemberShareLog> {

    @Select("SELECT sl.id, sl.member_id, sl.share_scene, sl.share_page, sl.related_id, sl.create_time, " +
            "m.username AS memberName, m.nick_name AS memberNickName, m.mobile AS memberMobile " +
            "FROM li_member_share_log sl LEFT JOIN li_member m ON sl.member_id = m.id ${ew.customSqlSegment}")
    IPage<MemberShareLogVO> pageWithMember(IPage<MemberShareLogVO> page,
                                           @Param(Constants.WRAPPER) Wrapper<MemberShareLog> queryWrapper);
}
