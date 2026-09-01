package cn.lili.modules.order.order.entity.dto;

import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.SearchVO;
import cn.lili.modules.order.order.entity.dos.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单支付记录查询参数（跨服务共享）
 * <p>
 * 包装 Order + SearchVO + PageVO，供 OpenFeign 单 @RequestBody 传输。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLogQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单查询条件
     */
    private Order order;

    /**
     * 查询条件
     */
    private SearchVO searchVO;

    /**
     * 分页参数
     */
    private PageVO pageVO;
}
