/**
 * 作者：mike
 * 日期：2026-03-30
 */
package cn.lili.modules.goods.serviceimpl;

import cn.lili.modules.goods.entity.dos.GoodsGroupGoods;
import cn.lili.modules.goods.mapper.GoodsGroupGoodsMapper;
import cn.lili.modules.goods.service.GoodsGroupGoodsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分组-商品关联业务服务实现类。
 * <p>
 * 提供对分组与商品关联关系的批量更新能力。
 * </p>
 *
 * @author mike
 * @date 2026-03-30
 */
@Service
public class GoodsGroupGoodsServiceImpl extends ServiceImpl<GoodsGroupGoodsMapper, GoodsGroupGoods> implements GoodsGroupGoodsService {

    @Override
    public long countByGroupId(String groupId) {
        QueryWrapper<GoodsGroupGoods> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        return this.count(wrapper);
    }

    /**
     * 批量设置某分组下的商品关联关系。
     * <p>
     * 当 {@code goodsIds} 为空或清理后为空时，不进行写入，直接返回 {@code true}。
     * </p>
     *
     * @param groupId 分组ID
     * @param goodsIds 商品ID列表
     * @return 操作是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGroupGoods(String groupId, List<String> goodsIds) {
        if (goodsIds == null || goodsIds.isEmpty()) {
            return true;
        }

        List<String> cleanGoodsIds = goodsIds.stream()
                .filter(i -> i != null && !i.trim().isEmpty())
                .map(String::trim)
                .distinct()
                .toList();
        if (cleanGoodsIds.isEmpty()) {
            return true;
        }

        // 批量设置分组：仅重置本次选中商品的分组，不影响其他商品
        QueryWrapper<GoodsGroupGoods> removeWrapper = new QueryWrapper<>();
        removeWrapper.in("goods_id", cleanGoodsIds);
        this.remove(removeWrapper);

        List<GoodsGroupGoods> list = new ArrayList<>();
        for (String goodsId : cleanGoodsIds) {
            GoodsGroupGoods rel = new GoodsGroupGoods();
            rel.setGroupId(groupId);
            rel.setGoodsId(goodsId);
            list.add(rel);
        }
        return this.saveBatch(list);
    }
}

