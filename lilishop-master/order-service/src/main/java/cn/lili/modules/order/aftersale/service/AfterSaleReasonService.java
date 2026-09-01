package cn.lili.modules.order.aftersale.service;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.order.aftersale.entity.dos.AfterSaleReason;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 售后原因业务层
 *
 * @author Chopper
 * @since 2020/11/17 7:37 下午
 */
public interface AfterSaleReasonService extends IService<AfterSaleReason> {

    /**
     * 获取售后原因列表
     * @param serviceType
     * @return
     */
    List<AfterSaleReason> afterSaleReasonList(String serviceType);

    /**
     * 按售后类型分页查询售后原因。
     *
     * @param pageVO      分页参数
     * @param serviceType 售后类型
     * @return 售后原因分页数据
     */
    IPage<AfterSaleReason> pageByServiceType(PageVO pageVO, String serviceType);

    /**
     * 修改售后原因
     * @param afterSaleReason 售后原因
     * @return 售后原因
     */
    AfterSaleReason editAfterSaleReason(AfterSaleReason afterSaleReason);

}
