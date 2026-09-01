package cn.lili.modules.wxchannels.service;

import cn.lili.modules.wxchannels.entity.dos.WxChannelGoods;
import cn.lili.modules.wxchannels.entity.dto.WxChannelGoodsSearchParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 视频号小店-商品镜像 服务接口
 *
 * 职责：
 * - 提供视频号商品镜像分页查询能力（名称模糊、状态筛选）；
 * - 保持与商城商品的库存、上下架状态同步（由消费者负责写入，本接口只读）。
 */
public interface WxChannelGoodsService extends IService<WxChannelGoods> {

    /**
     * 分页查询视频号商品镜像
     *
     * @param params 查询参数（名称模糊、状态、分页/排序）
     * @return 视频号商品分页结果
     */
    IPage<WxChannelGoods> page(WxChannelGoodsSearchParams params);
}
