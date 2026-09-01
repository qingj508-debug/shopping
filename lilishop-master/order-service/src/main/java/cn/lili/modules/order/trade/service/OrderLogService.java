package cn.lili.modules.order.trade.service;

import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.SearchVO;
import cn.lili.modules.order.trade.entity.dos.OrderLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 订单日志业务层
 *
 * @author Chopper
 * @since 2020-02-25 14:10:16
 */
public interface OrderLogService extends IService<OrderLog> {

    /**
     * 根据订单编号获取订单日志列表
     * @param orderSn 订单编号
     * @return 订单日志列表
     */
    List<OrderLog> getOrderLog(String orderSn);

    /**
     * 订单日志分页查询
     *
     * @param entity 查询参数
     * @param searchVO 查询条件
     * @param pageVO 分页参数
     * @return 分页结果
     */
    IPage<OrderLog> getByPage(OrderLog entity, SearchVO searchVO, PageVO pageVO);
}
