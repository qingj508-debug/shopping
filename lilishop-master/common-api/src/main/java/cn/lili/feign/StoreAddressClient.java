package cn.lili.feign;

import cn.lili.modules.store.entity.dos.StoreAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * store-service 内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用店铺自提点域能力，端点由 store-service 的 InternalStoreController 提供。
 */
@FeignClient(name = "store-service", path = "/internal/store/storeAddress", contextId = "liliStoreAddressClient")
public interface StoreAddressClient {

    @GetMapping("/{id}")
    StoreAddress getById(@PathVariable("id") String id);
}
