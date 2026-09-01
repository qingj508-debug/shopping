package cn.lili.modules.member.service;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.member.entity.dos.MemberPointsHistory;
import cn.lili.modules.member.entity.vo.MemberPointsHistoryVO;
import cn.lili.modules.member.entity.vo.MemberPointsStatisticsVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 客户积分历史业务层
 *
 * @author Bulbasaur
 * @since 2020-02-25 14:10:16
 */
public interface MemberPointsHistoryService extends IService<MemberPointsHistory> {

    /**
     * 获取客户积分VO
     *
     * @param memberId 客户ID
     * @return 客户积分VO
     */
    MemberPointsHistoryVO getMemberPointsHistoryVO(String memberId);

    /**
     * 客户积分历史
     *
     * @param page       分页
     * @param memberId   客户ID
     * @param memberName 客户名称
     * @return 积分历史分页
     */
    IPage<MemberPointsHistory> MemberPointsHistoryList(PageVO page, String memberId, String memberName);

    /**
     * 分页查询指定会员的积分变更记录（按创建时间倒序）。
     *
     * @param memberId 会员ID
     * @param pageVO   分页参数
     * @return 积分历史分页
     */
    IPage<MemberPointsHistory> pageByMemberId(String memberId, PageVO pageVO);

    MemberPointsStatisticsVO queryMemberPointsStatistics();

}
