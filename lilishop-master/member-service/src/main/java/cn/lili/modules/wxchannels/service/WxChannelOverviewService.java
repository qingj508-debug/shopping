package cn.lili.modules.wxchannels.service;

import cn.lili.modules.wxchannels.entity.vo.WxChannelOverviewDailyVO;
import cn.lili.modules.wxchannels.entity.vo.WxChannelOverviewSummaryVO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * 视频号小店-概况统计 服务接口
 *
 * 职责：
 * - 提供视频号销售/退款的统计概括与日报明细；
 * - 提供日报 Excel 导出能力。
 *
 * 约定：
 * - 未指定时间范围默认返回最近 7 天；
 * - 概况与日报按 create_time 统计，场景维度包含 LIVE/WINDOW。
 */
public interface WxChannelOverviewService {

    /**
     * 销售/退款概括统计
     *
     * @param startTime 起始时间（毫秒），可为空
     * @param endTime   截止时间（毫秒），可为空
     * @return 概括统计（总销售、直播间/橱窗销售、总退款、直播间/橱窗退款）
     */
    WxChannelOverviewSummaryVO summary(Long startTime, Long endTime);

    /**
     * 日报统计（按天）
     *
     * @param startTime 起始时间（毫秒），可为空
     * @param endTime   截止时间（毫秒），可为空
     * @return 日报列表（按日期倒序）
     */
    List<WxChannelOverviewDailyVO> daily(Long startTime, Long endTime);

    /**
     * 导出日报 Excel
     *
     * @param response  Http 响应
     * @param startTime 起始时间（毫秒），可为空
     * @param endTime   截止时间（毫秒），可为空
     */
    void export(HttpServletResponse response, Long startTime, Long endTime);
}
