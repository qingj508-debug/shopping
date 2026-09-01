package cn.lili.modules.wxchannels.service;

import cn.lili.modules.wxchannels.entity.dos.WxChannelOrder;
import cn.lili.modules.wxchannels.entity.dto.WxChannelOrderSearchParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 视频号小店-订单 服务接口
 *
 * 职责：
 * - 提供视频号订单的分页查询能力（订单号、客户、商品名、状态、场景、时间范围）。
 *
 * 数据来源说明：
 * - 订单数据由对接层/回调/定时拉取落库；本接口仅提供只读查询能力。
 */
public interface WxChannelOrderService extends IService<WxChannelOrder> {

    /**
     * 分页查询视频号订单
     *
     * @param params 查询参数（编号、客户、商品名模糊、状态、场景、时间范围、分页/排序）
     * @return 视频号订单分页结果
     */
    IPage<WxChannelOrder> page(WxChannelOrderSearchParams params);
}
