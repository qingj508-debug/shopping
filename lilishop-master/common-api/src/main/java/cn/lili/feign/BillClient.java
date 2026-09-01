package cn.lili.feign;

import cn.lili.modules.store.entity.dos.Bill;
import cn.lili.modules.store.entity.dto.BillSearchParams;
import cn.lili.modules.store.entity.vos.BillListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * store-service 内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用结算单域能力，端点由 store-service 的 InternalStoreController 提供。
 */
@FeignClient(name = "store-service", path = "/internal/store/bill", contextId = "liliBillClient")
public interface BillClient {

    @GetMapping("/{id}")
    Bill getById(@PathVariable("id") String id);

    @PostMapping("/billPage")
    IPage<BillListVO> billPage(@RequestBody BillSearchParams billSearchParams);
}
