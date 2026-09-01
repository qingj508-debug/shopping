/**
 * 作者：mike
 * 日期：2026-03-30
 */
package cn.lili.modules.goods.service;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.goods.entity.dos.GoodsGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品分组业务服务接口（Service）。
 * <p>
 * 继承 {@link IService}，提供 {@link GoodsGroup} 的通用 CRUD 能力。
 * </p>
 *
 * @author mike
 * @date 2026-03-30
 */
public interface GoodsGroupService extends IService<GoodsGroup> {

    /**
     * 管理端分页查询商品分组。
     *
     * @param pageVO 分页参数
     * @return 商品分组分页数据
     */
    IPage<GoodsGroup> getByPage(PageVO pageVO);
}

