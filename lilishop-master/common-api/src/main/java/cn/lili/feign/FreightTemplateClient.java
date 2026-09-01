package cn.lili.feign;

import cn.lili.modules.store.entity.dos.FreightTemplate;
import cn.lili.modules.store.entity.vos.FreightTemplateVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * store-service 内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用运费模板域能力，端点由 store-service 的 InternalStoreController 提供。
 */
@FeignClient(name = "store-service", path = "/internal/store/freightTemplate", contextId = "liliFreightTemplateClient")
public interface FreightTemplateClient {

    @GetMapping("/{id}")
    FreightTemplate getById(@PathVariable("id") String id);

    @GetMapping("/vo/{id}")
    FreightTemplateVO getFreightTemplate(@PathVariable("id") String id);

    @GetMapping("/list/{storeId}")
    List<FreightTemplateVO> getFreightTemplateList(@PathVariable("storeId") String storeId);
}
