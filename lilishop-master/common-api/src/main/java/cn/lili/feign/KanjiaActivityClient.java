package cn.lili.feign;

import cn.lili.modules.promotion.entity.dos.KanjiaActivity;
import cn.lili.modules.promotion.entity.dto.search.KanjiaActivitySearchParams;
import cn.lili.modules.promotion.entity.vos.kanjia.KanjiaActivityVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * promotion-service 砍价活动内部调用 Feign 客户端
 * <p>
 * 端点由 promotion-service 的 InternalPromotionController 提供。
 */
@FeignClient(name = "promotion-service", path = "/internal/promotion/kanjiaActivity", contextId = "liliKanjiaActivityClient")
public interface KanjiaActivityClient {

    @PostMapping("/getKanjiaActivity")
    KanjiaActivity getKanjiaActivity(@RequestBody KanjiaActivitySearchParams kanJiaActivitySearchParams);

    @PostMapping("/getKanjiaActivityVO")
    KanjiaActivityVO getKanjiaActivityVO(@RequestBody KanjiaActivitySearchParams kanJiaActivitySearchParams);

    @PostMapping("/end")
    boolean endKanjiaActivity(@RequestParam("kanjiaId") String kanjiaId);
}
