package cn.lili.feign;

import cn.lili.modules.store.entity.dos.StoreDetail;
import cn.lili.modules.store.entity.dto.StoreAfterSaleAddressDTO;
import cn.lili.modules.store.entity.dto.StoreDeliverGoodsAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * store-service 内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用店铺详情域能力，端点由 store-service 的 InternalStoreController 提供。
 */
@FeignClient(name = "store-service", path = "/internal/store/storeDetail", contextId = "liliStoreDetailClient")
public interface StoreDetailClient {

    @GetMapping("/{storeId}")
    StoreDetail getStoreDetail(@PathVariable("storeId") String storeId);

    @GetMapping("/afterSaleAddress/{id}")
    StoreAfterSaleAddressDTO getStoreAfterSaleAddressDTO(@PathVariable("id") String id);

    @GetMapping("/deliverGoodsAddress/{id}")
    StoreDeliverGoodsAddressDTO getStoreDeliverGoodsAddressDto(@PathVariable("id") String id);
}
