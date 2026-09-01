package cn.lili.feign;

import cn.lili.modules.member.entity.dto.CollectionDTO;
import cn.lili.modules.store.entity.dos.Store;
import cn.lili.modules.store.entity.vos.StoreVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * store-service 内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用店铺域能力，端点由 store-service 的 InternalStoreController 提供。
 */
@FeignClient(name = "store-service", path = "/internal/store/store", contextId = "liliStoreClient")
public interface StoreClient {

    @GetMapping("/{id}")
    Store getById(@PathVariable("id") String id);

    @PostMapping("/listByIds")
    List<Store> listByIds(@RequestBody List<String> ids);

    @PostMapping("/updateStoreCollectionNum")
    void updateStoreCollectionNum(@RequestBody CollectionDTO collectionDTO);

    /**
     * 根据店铺ID获取店铺信息VO（含店铺详情，join 查询）
     */
    @GetMapping("/detail/{storeId}")
    StoreVO getStoreDetail(@PathVariable("storeId") String storeId);
}
