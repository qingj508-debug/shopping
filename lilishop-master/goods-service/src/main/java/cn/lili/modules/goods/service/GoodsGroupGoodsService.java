/**
 * 作者：mike
 * 日期：2026-03-30
 */
package cn.lili.modules.goods.service;

import cn.lili.modules.goods.entity.dos.GoodsGroupGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品分组-商品关联业务服务接口（Service）。
 * <p>
 * 用于维护“某分组下包含哪些商品”的关系。
 * </p>
 *
 * @author mike
 * @date 2026-03-30
 */
public interface GoodsGroupGoodsService extends IService<GoodsGroupGoods> {

    /**
     * 统计指定分组下的商品数量。
     *
     * @param groupId 分组ID
     * @return 分组下商品数量
     */
    long countByGroupId(String groupId);

    /**
     * 批量更新指定分组下的商品关联关系。
     * <p>
     * 具体策略由实现类决定（例如：清理旧关系并写入新关系）。
     * </p>
     *
     * @param groupId 分组ID
     * @param goodsIds 商品ID列表（可包含空值/空白，会在实现层做清理）
     * @return 成功返回 {@code true}，失败返回 {@code false}
     */
    boolean updateGroupGoods(String groupId, List<String> goodsIds);
}

