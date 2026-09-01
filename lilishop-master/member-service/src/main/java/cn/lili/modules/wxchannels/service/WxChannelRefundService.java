package cn.lili.modules.wxchannels.service;

import cn.lili.modules.wxchannels.entity.dos.WxChannelRefund;
import cn.lili.modules.wxchannels.entity.dto.WxChannelRefundSearchParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 视频号小店-退单 服务接口
 *
 * 职责：
 * - 提供视频号退单记录的分页查询能力（退单号、订单号、客户、商品名、状态、场景、时间范围）。
 *
 * 数据来源说明：
 * - 退单数据由对接层/回调/定时拉取落库；本接口仅提供只读查询能力。
 */
public interface WxChannelRefundService extends IService<WxChannelRefund> {

    /**
     * 分页查询视频号退单
     *
     * @param params 查询参数（退单号/订单号、客户、商品名模糊、状态、场景、时间范围、分页/排序）
     * @return 退单分页结果
     */
    IPage<WxChannelRefund> page(WxChannelRefundSearchParams params);
}
