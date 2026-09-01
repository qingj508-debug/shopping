package cn.lili.modules.payment.service;

import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.SearchVO;
import cn.lili.modules.payment.entity.RefundLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 退款日志 业务层
 *
 * @author Chopper
 * @since 2020-12-19 09:25
 */
public interface RefundLogService extends IService<RefundLog> {
    /**
     * 根据售后sn查询退款日志
     * @param sn
     * @return
     */
    RefundLog queryByAfterSaleSn(String sn);

    /**
     * 退款日志分页查询
     *
     * @param entity 查询参数
     * @param searchVO 查询条件
     * @param pageVO 分页参数
     * @return 分页结果
     */
    IPage<RefundLog> getByPage(RefundLog entity, SearchVO searchVO, PageVO pageVO);
}
