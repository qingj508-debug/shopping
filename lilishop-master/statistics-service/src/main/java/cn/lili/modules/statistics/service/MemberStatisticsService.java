package cn.lili.modules.statistics.service;

import cn.lili.modules.member.entity.vo.MemberDistributionVO;
import cn.lili.modules.statistics.entity.dos.MemberStatisticsData;
import cn.lili.modules.statistics.entity.dto.StatisticsQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * 客户统计业务层
 *
 * @author Bulbasaur
 * @since 2020/12/9 11:06
 */
public interface MemberStatisticsService extends IService<MemberStatisticsData> {

    /**
     * 获取客户数量
     *
     * @return 客户统计
     */
    long getMemberCount();

    /**
     * 获取今日新增客户数量
     *
     * @return 今日新增客户数量
     */
    long todayMemberNum();

    /**
     * 获取指定结束时间前的客户数量
     *
     * @param endTime
     * @return
     */
    long memberCount(Date endTime);

    /**
     * 当天活跃客户数量
     *
     * @param startTime
     * @return
     */
    long activeQuantity(Date startTime);

    /**
     * 时间段内新增客户数量
     *
     * @param endTime
     * @param startTime
     * @return
     */
    long newlyAdded(Date endTime, Date startTime);

    /**
     * 根据参数，查询这段时间的客户统计
     *
     * @param statisticsQueryParam
     * @return
     */
    List<MemberStatisticsData> statistics(StatisticsQueryParam statisticsQueryParam);


    /**
     * 查看客户数据分布
     *
     * @return 客户数据分布
     */
    List<MemberDistributionVO> distribution();

}