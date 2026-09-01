/**
 * 作者：mike
 * 日期：2026-03-30
 */
package cn.lili.modules.goods.serviceimpl;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.goods.entity.dos.GoodsGroup;
import cn.lili.modules.goods.mapper.GoodsGroupMapper;
import cn.lili.modules.goods.service.GoodsGroupService;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 商品分组业务服务实现类。
 * <p>
 * 基于 MyBatis-Plus 的 {@link ServiceImpl} 实现通用 CRUD。
 * </p>
 *
 * @author mike
 * @date 2026-03-30
 */
@Service
public class GoodsGroupServiceImpl extends ServiceImpl<GoodsGroupMapper, GoodsGroup> implements GoodsGroupService {

    @Override
    public IPage<GoodsGroup> getByPage(PageVO pageVO) {
        QueryWrapper<GoodsGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return this.page(PageUtil.initPage(pageVO), queryWrapper);
    }
}

