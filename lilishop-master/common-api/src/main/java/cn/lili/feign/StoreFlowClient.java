package cn.lili.feign;

import cn.lili.modules.finance.entity.dto.FinanceStoreFlowSearchParams;
import cn.lili.modules.order.aftersale.entity.dos.AfterSale;
import cn.lili.modules.order.order.entity.dos.StoreFlow;
import cn.lili.modules.order.order.entity.dto.StoreFlowQueryDTO;
import cn.lili.modules.store.entity.dos.Bill;
import cn.lili.modules.store.entity.dto.BillSearchParams;
import cn.lili.modules.store.entity.vos.StoreFlowPayDownloadVO;
import cn.lili.modules.store.entity.vos.StoreFlowRefundDownloadVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * order-service 内部调用 Feign 客户端（店铺流水域）
 * <p>
 * 端点由 order-service 的 InternalOrderController 提供。
 */
@FeignClient(name = "order-service", path = "/internal/order/storeFlow", contextId = "liliStoreFlowClient")
public interface StoreFlowClient {

    /**
     * 店铺流水总数统计（按财务导出查询参数）
     */
    @PostMapping("/countByParams")
    long countByParams(@RequestBody FinanceStoreFlowSearchParams params);

    /**
     * 店铺流水分页查询（按财务导出查询参数，分页取自 params 的 PageVO 属性）
     */
    @PostMapping("/pageByParams")
    IPage<StoreFlow> pageByParams(@RequestBody FinanceStoreFlowSearchParams params);

    /**
     * 获取结算单的入账流水
     */
    @PostMapping("/payDownload")
    List<StoreFlowPayDownloadVO> getStoreFlowPayDownloadVO(@RequestBody StoreFlowQueryDTO storeFlowQueryDTO);

    /**
     * 获取结算单的退款流水
     */
    @PostMapping("/refundDownload")
    List<StoreFlowRefundDownloadVO> getStoreFlowRefundDownloadVO(@RequestBody StoreFlowQueryDTO storeFlowQueryDTO);

    /**
     * 订单退款流水处理
     */
    @PostMapping("/refundOrder")
    void refundOrder(@RequestBody AfterSale afterSale);

    /**
     * 获取店铺流水列表
     */
    @PostMapping("/listStoreFlow")
    List<StoreFlow> listStoreFlow(@RequestBody StoreFlowQueryDTO storeFlowQueryDTO);

    /**
     * 根据参数查询一条流水记录
     */
    @PostMapping("/queryOne")
    StoreFlow queryOne(@RequestBody StoreFlowQueryDTO storeFlowQueryDTO);

    /**
     * 获取结算单的退款汇总信息
     */
    @PostMapping("/refundBill")
    Bill getRefundBill(@RequestBody BillSearchParams searchParams);

    /**
     * 获取结算单的入账汇总信息
     */
    @PostMapping("/orderBill")
    Bill getOrderBill(@RequestBody BillSearchParams searchParams);
}
