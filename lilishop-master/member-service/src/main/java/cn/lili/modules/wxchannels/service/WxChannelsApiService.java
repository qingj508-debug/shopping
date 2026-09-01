package cn.lili.modules.wxchannels.service;

import cn.lili.modules.wxchannels.entity.dto.WxChannelsCategoryDTO;
import java.util.List;

public interface WxChannelsApiService {

    /**
     * 更新视频号 SKU 库存
     *
     * @param skuId 平台 SKU ID（与视频号侧绑定）
     * @param stock 库存数量（>=0）
     */
    void updateInventory(String skuId, Integer stock);

    /**
     * 变更视频号商品上下架状态
     *
     * @param goodsId 平台商品 ID（与视频号侧绑定）
     * @param status  状态（如 ENABLE/DISABLE），与业务约定保持一致
     */
    void changeStatus(String goodsId, String status);

    /**
     * 获取微信视频号全量三级类目
     *
     * @return 三级类目列表（含资质信息）
     */
    List<WxChannelsCategoryDTO> getThirdCategories();
}
